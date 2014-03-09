package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
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
	//private final AuthorRepositoryInterface authorRepository;
	

	/**
	 * BookResource constructor
	 * 
	 * @param bookRepository
	 *            a BookRepository instance
	 */
	public BookResource(BookRepositoryInterface bookRepository ) {
		this.bookRepository = bookRepository;
		//this.authorRepository = authorRepository;
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
		if(book.getReviews().size()!=0){
		bookResponse.addLink(new LinkDto("view-all-reviews","/books/"+book.getIsbn()+"/reviews","GET"));
		}
		// add more links



		return bookResponse;
	}


	@POST
	@Timed(name = "create-book")
	public Response createBook(Book request) {
		// Store the new book in the BookRepository so that we can retrieve it.
		Book savedBook = bookRepository.saveBook(request);
		
		String location = "/books/" + savedBook.getIsbn();
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("view-book", location, "GET"));
		links.addLink(new LinkDto("update-book", location, "PUT"));
		links.addLink(new LinkDto("delete-book" , location,"DELETE"));
		links.addLink(new LinkDto("create-review",location ,"POST"));
		// Add other links if needed
		
		return Response.status(201).entity(links).build();
	}
	
	/*@POST
	@Timed(name = "create-book")
	public Response createBook(Book request , Author author) {
		// Store the new book in the BookRepository so that we can retrieve it.
		Book savedBook = bookRepository.saveBook(request);
         
		Author savedAuthor = request.addAuthor(author);
		
		String location = "/books/" + savedBook.getIsbn();
		BookDto bookResponse = new BookDto(savedBook);
		AuthorDto authorResponse = new AuthorDto(savedAuthor);
		
		bookResponse.addLink(new LinkDto("view-book", location, "GET"));
		bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
		bookResponse.addLink(new LinkDto("delete-book" , location,"DELETE"));
		bookResponse.addLink(new LinkDto("create-review",location ,"POST"));
		// Add other links if needed

		return Response.status(201).entity(bookResponse).build();
	}*/

	
	// updating metatdata of a particular book
	@PUT
	@Path("/{isbn}")
	@Timed(name = "update-book")
	public Response updateBook(@PathParam("isbn") Long isbn ,  @QueryParam("status") String status){
		
		if(bookRepository.updateStatusbyIsbn(isbn, status)){
			Book book = bookRepository.getBookByISBN(isbn);
		
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("view-book", "/books/" + isbn,"GET"));
		links.addLink(new LinkDto("update-book","/books/" +isbn, "PUT"));
		links.addLink(new LinkDto("delete-book","/books/"+isbn,"DELETE"));
		links.addLink(new LinkDto("create-review","/books/"+isbn+"/reviews","POST"));
		if(book.getReviews().size()!=0){
		links.addLink(new LinkDto("view-all-reviews","/books/"+isbn+"/reviews","GET"));
		}
		
		return Response.status(200).entity(links).build();
		}
		
		return Response.status(400).build(); 
	}
	
	@DELETE
	@Path("/{isbn}")
	@Timed(name = "delete-book")
	public Response deleteBook(@PathParam("isbn") Long isbn){
		if(bookRepository.deleteBookByIsbn(isbn)){
			LinksDto links = new LinksDto();
			links.addLink(new LinkDto("create-book", "/books", "POST"));
			return Response.status(200).entity(links).build();
	}
	
	return Response.status(200).build();
	}
	// adding reviews to book
	
	@POST
	@Path("/{isbn}/reviews")
	@Timed(name = "create-review")
	public Response createReview(@PathParam("isbn") LongParam isbn , Review newReview){
		
//		try{
//		Book book = bookRepository.getBookByISBN(isbn.get());
//		
//		book.setReview(newReview);
//		System.out.printf("comment %s, rating %s\n", newReview.getComment(), newReview.getRating());
//		
//		System.out.println(newReview);
//		LinksDto links = new LinksDto();
//		links.addLink(new LinkDto("view-review", "/books/"+book.getIsbn()+"/reviews/"+newReview.getId(), "GET"));
//		return Response.status(201).entity(links).build(); 
//		}
//		catch(Exception ex){
//			System.out.println("exception");
//			
//			return Response.status(404).build(); 
//		}
		
			Book book = bookRepository.getBookByISBN(isbn.get());
			
			book.setReview(newReview);
			System.out.printf("comment %s, rating %s\n", newReview.getComment(), newReview.getRating());
			
			System.out.println(newReview);
			LinksDto links = new LinksDto();
			links.addLink(new LinkDto("view-review", "/books/"+book.getIsbn()+"/reviews/"+newReview.getId(), "GET"));
			return Response.status(201).entity(links).build(); 			
//	
		
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
		List<Review> allReviews = book.getReviews();
		ReviewsDto reviewResponse = new ReviewsDto(allReviews);
		
		
		return reviewResponse ;
	
	}
	
	@GET
	@Path("/{isbn}/authors")
	@Timed(name = "view-all-authors")
	
	public AuthorsDto viewAllAuthors(@PathParam("isbn") LongParam isbn){
		
		Book book = bookRepository.getBookByISBN(isbn.get());
		List<Author> allAuthors = book.viewAllAuthorsList();
		AuthorsDto authorResponse = new AuthorsDto(allAuthors);
		
		return authorResponse ;
	
	}
	
	@GET
	@Path("/{isbn}/authors/{id}")
	@Timed(name = "view-author")
	
	public AuthorDto viewAuthorById(@PathParam("isbn") LongParam isbn , @PathParam("id") Long id){
		Book book = bookRepository.getBookByISBN(isbn.get());
		Author author = book.getAuthorByID(id);
		
		

		AuthorDto authorResponse = new AuthorDto(author);
		authorResponse.addLink(new LinkDto("view-author","/books/"+book.getIsbn()+"/authors/"+author.getId(),"GET"));
		// add more links
		return authorResponse;
	
	}
	


}

