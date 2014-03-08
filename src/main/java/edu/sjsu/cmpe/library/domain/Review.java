package edu.sjsu.cmpe.library.domain;

import java.util.Arrays;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.BadRequestException;



@JsonPropertyOrder({"id","rating", "comment" })
public class Review{
	
	
	
	
	private int rating;
	private String comment;
	private Long id;
	public Review(){
		//reviewKey =0;
	}
	

	public void setId(Long id){
		this.id = id;
	}
	public Long getId(){
		return id;
	}
	 
	public int getRating(){
		return rating;
	}
	
	public void setRating(int rating){
		
		if(rating < 6){
		this.rating = rating;
		}
		else{
			throw new BadRequestException("Rating must be 1-5, input was " + rating);
		}
		
	}
	
	public String getComment(){
		return comment;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	@Override
	public String  toString(){
		
		StringBuilder str = new StringBuilder();
		str.append(id).append("/n").append(rating).append("/n").append(comment);
		
		
		return str.toString();
		
	}
}