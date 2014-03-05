package edu.sjsu.cmpe.library.domain;

public class Review{
	//private int reviewKey;
	private int rating;
	private String comment;
	private Long id;
	public Review(){
		//reviewKey =0;
	}
	
	/* private final Long generateReviewKey() {
			// increment existing isbnKey and return the new value
			//return Long.valueOf(++reviewKey);
		    }*/
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
		this.rating = rating;
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