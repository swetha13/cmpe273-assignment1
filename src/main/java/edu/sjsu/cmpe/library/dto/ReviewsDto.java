package edu.sjsu.cmpe.library.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;

@JsonPropertyOrder(alphabetic = true)
public class ReviewsDto extends LinksDto {
    
	private List<Review> reviews;
     

    /**
     * @param book
     */
    public ReviewsDto(List<Review> reviews) {
	super();
	this.reviews = reviews;
    }

   
	/**
     * @return the book
     */
    public List<Review> getReviews() {
	return reviews;
    }

    /**
     * @param book
     *            the book to set
     */
    public void setReviews(List<Review> reviews) {
	this.reviews = reviews;
    }
}
