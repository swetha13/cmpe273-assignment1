package edu.sjsu.cmpe.library.dto;



import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Author;


@JsonPropertyOrder(alphabetic = true)
public class AuthorDto extends LinksDto {
    private Author author;
	
     

    /**
     * @param book
     */
    public AuthorDto(Author author) {
	super();
	this.author = author;
    }

    public AuthorDto() {
    	super();
    	//this.review = review;
        }

   
	/**
     * @return the book
     */
    public Author getAuthor() {
	return author;
    }

    /**
     * @param book
     *            the book to set
     */
    public void setAuthor(Author author) {
	this.author = author;
    }
}
