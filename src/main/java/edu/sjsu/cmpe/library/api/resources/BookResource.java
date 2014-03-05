package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
	/** bookRepository instance */
	private final BookRepositoryInterface bookRepository;

	/**
	 * BookResource constructor
	 * 
	 * @param bookRepository
	 *            a BookRepository instance
	 */
	public BookResource(BookRepositoryInterface bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GET
	@Path("/{isbn}")
	@Timed(name = "view-book")
	public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
		Book book = bookRepository.getBookByISBN(isbn.get());
		BookDto bookResponse = new BookDto(book);



		bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
		bookResponse.addLink(new LinkDto("update-book","/books/" +book.getIsbn(), "PUT"));
		bookResponse.addLink(new LinkDto("delete-book","/books/"+book.getIsbn(),"DELETE"));
		bookResponse.addLink(new LinkDto("create-review","/books/"+book.getIsbn()+"/reviews","POST"));
		bookResponse.addLink(new LinkDto("view-all-reviews","/books/"+book.getIsbn()+"/reviews","GET"));
		// add more links



		return bookResponse;
	}

	@POST
	@Timed(name = "create-book")
	public Response createBook(Book request) {
		// Store the new book in the BookRepository so that we can retrieve it.
		Book savedBook = bookRepository.saveBook(request);

		String location = "/books/" + savedBook.getIsbn();
		BookDto bookResponse = new BookDto(savedBook);
		bookResponse.addLink(new LinkDto("view-book", location, "GET"));
		bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
		bookResponse.addLink(new LinkDto("delete-book" , location,"DELETE"));
		bookResponse.addLink(new LinkDto("create-review",location ,"POST"));
		// Add other links if needed

		return Response.status(201).entity(bookResponse).build();
	}

	
	// updating metatdata of a particular book
	@PUT
	@Path("/{isbn}")
	@Timed(name = "update-book")
	public Response updateBook(@PathParam("isbn") LongParam isbn ,  @QueryParam("param_name") String param_name){
		
		Book book = bookRepository.getBookByISBN(isbn.get());
		book.setLanguage(param_name);
		
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
		links.addLink(new LinkDto("update-book","/books/" +book.getIsbn(), "PUT"));
		links.addLink(new LinkDto("delete-book","/books/"+book.getIsbn(),"DELETE"));
		links.addLink(new LinkDto("create-review","/books/"+book.getIsbn()+"/reviews","POST"));
		links.addLink(new LinkDto("view-all-reviews","/books/"+book.getIsbn()+"/reviews","GET"));
		
		return Response.status(200).entity(links).build();
		
	}
	
	// adding reviews to book
	
	@POST
	@Path("/{isbn}/reviews")
	@Timed(name = "create-review")
	public Response createReview(@PathParam("isbn") LongParam isbn , Review newReview){
		
		//System.out.println("comment" +newReview.getComment() + "rating"+ newReview.getRating());
		Book book = bookRepository.getBookByISBN(isbn.get());
		book.addReview(newReview);
		System.out.printf("comment %s, rating %s\n", newReview.getComment(), newReview.getRating());
		
		System.out.println(newReview);
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("view-review", "/books/"+book.getIsbn()+"/reviews/"+newReview.getId(), "GET"));
		
		return Response.status(201).entity(links).build(); 
		
	}
	
	@GET
	@Path("/{isbn}/reviews/{id}")
	@Timed(name = "view-review")
	
	public ReviewDto viewReviewById(@PathParam("isbn") LongParam isbn , @PathParam("id") LongParam id){
		Book book = bookRepository.getBookByISBN(isbn.get());
		Review review = book.getReviewById(id.get());
		
		System.out.println("rating " + review.getRating() +"cooment "+ review.getComment() +"id:" +review.getId());

		ReviewDto reviewResponse = new ReviewDto(review);
		reviewResponse.addLink(new LinkDto("view-review","/books/"+book.getIsbn()+"/reviews/"+review.getId(),"GET"));
		// add more links
		return reviewResponse;
	
	}
	
	@GET
	@Path("/{isbn}/reviews")
	@Timed(name = "view-all-reviews")
	
	public ReviewsDto viewAllReviews(@PathParam("isbn") LongParam isbn){
		
		Book book = bookRepository.getBookByISBN(isbn.get());
		List<Review> allReviews = book.getAllReviews();
		ReviewsDto reviewResponse = new ReviewsDto(allReviews);
		
		return reviewResponse ;
	
	}



}

