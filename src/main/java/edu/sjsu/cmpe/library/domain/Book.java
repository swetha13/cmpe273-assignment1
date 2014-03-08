package edu.sjsu.cmpe.library.domain;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Path;

import org.eclipse.jetty.util.ajax.JSON;
import org.hibernate.validator.constraints.NotEmpty;

import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.Maps;

@JsonPropertyOrder({"isbn","title", "publication-date", "language","num-pages","status" })
public class Book {
	private long isbn;
	private Long reviewKey;
	//private Long authorKey;
	//private List<Review> reviews;
	
	
	private String title;
	@NotEmpty
	@JsonProperty("publication-date")
	private String publication_date;
	
	private String language;
	
	
	@JsonProperty("num-pages")
	private int num_pages;
	
	private enum Status { 
		available , checkedout , inqueue , lost }
	@NotEmpty
	private Status status; 
	
	
	private HashMap<Integer, String> authors = new HashMap<Integer, String>();



	private final ConcurrentHashMap<Long, Review> reviewInMemoryMap;
	//private final ConcurrentHashMap<Long, Author> authorInMemoryMap;
	
	public Book(){
		reviewKey = Long.valueOf(0);
		reviewInMemoryMap = new ConcurrentHashMap<Long, Review>();
		//authorKey = Long.valueOf(0);
		//authorInMemoryMap = new ConcurrentHashMap<Long, Author>();
	}
	public Review setReview(Review review){
		
		Long id = generateReviewKey();
		review.setId(id);
		reviewInMemoryMap.put(id, review);
		return review;
	}
	
	public  Review getReviewById(Long id) {
		checkArgument(id > 0,
			"ID was %s but expected greater than zero value", id);
		return reviewInMemoryMap.get(id);
	    }
	
	
	public List<Review> getReviews(){
		int totalReviews = reviewInMemoryMap.size();
		System.out.println("Number of reviews " + totalReviews);
		List<Review> reviewsList = new ArrayList<Review>();
		for(int i = 1; i < totalReviews+1 ; i++){
			reviewsList.add(getReviewById((long) i));
		}
		return reviewsList;
		
		
	}
	
	
	
	 private final Long generateReviewKey() {
	// increment existing isbnKey and return the new value
	return Long.valueOf(++reviewKey);
    }
	 
/*	 private final Long generateAuthorKey(){
		 return Long.valueOf(++authorKey);
	 }
	 
	 public Author addAuthor(Author author){
			
			Long id = generateAuthorKey();
			System.out.println("author id :"+ id);
			author.setAuthorId(id);
			authorInMemoryMap.put(id, author);
			return author;
		}
	 
	 public  Author getAuthorByID(Long id) {
			checkArgument(id > 0,
				"ID was %s but expected greater than zero value", id);
			return authorInMemoryMap.get(id);
		    }
	
	 public List<Author> getAllAuthors(){
			int totalAuthors = reviewInMemoryMap.size();
			System.out.println("Number of Authors " + totalAuthors);
			List<Author> authorsList = new ArrayList<Author>();
			for(int i = 1; i < totalAuthors+1 ; i++){
				authorsList.add(getAuthorByID((long) i));
			}
			return authorsList;
			
			
		}
		

	/**
	 * @return the isbn
	 */
	public long getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the publication_date
	 */
	public String getpublication_date() {
		return publication_date;
	}

	/**
	 * @param publication_date
	 *            the publication_date to set
	 */
	public void setpublication_date(String publication_date) {
		this.publication_date = publication_date;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}


	/**
	 * @return the number_of_pages
	 */
	public int getnum_pages() {
		return num_pages;
	}

	/**
	 * @param number_of_pages
	 *            the number_of_pages to set
	 */
	public void setnum_pages(int num_pages) {
		this.num_pages = num_pages;
	}

	public void setStatus(String status) {
		
		this.status = Status.valueOf(status);
		System.out.println("status value"+Status.valueOf(status));
			
		
	}


	public String getStatus(){
		return status.name();
	}


	@Override
	public String  toString(){
		
		StringBuilder str = new StringBuilder();
		str.append(isbn).append("/n").append(title).append("/n").append(reviewKey).append(reviewInMemoryMap.toString());
		
		
		return str.toString();
		
	}
	
/*	public void setAuthors(HashMap<String,String>[] authors_names){
		int count = 1;

		for (HashMap<String,String> map : authors_names){

			System.out.println("map values" + map);

			for (Entry<String, String> entryPair:map.entrySet())
			{

				try{
					
					System.out.println("key value"+entryPair.getKey() + "; " +"value :"+ entryPair.getValue());
					System.out.println("value 1" +entryPair.getValue());
					authors.put(Integer.valueOf(count), entryPair.getValue());
					count++;
					//System.out.println("authors new map value" + authors);
				}
				catch(Exception ex){
					System.out.println("exception raised");
					ex.printStackTrace();
				}


			}
		}
		System.out.println("displaying the new hash map");

		System.out.println("new map- author values " + authors);
		
		 

	}
	
	public LinksDto getAuthors(){
		//LinkDto authLink = new LinkDto("view-author", "/books/"+getIsbn()+"/authors/", "GET");
		AuthorDto authorsLinks = new AuthorDto();
		for (Entry<Integer, String> entryPair:authors.entrySet()){
			authorsLinks.addLink(new LinkDto("view-author", "/books/"+getIsbn()+"/authors/"+entryPair.getKey(), "GET"));
		
		}
		return authorsLinks;
		
		
		
	}*/
	

	
	
}
