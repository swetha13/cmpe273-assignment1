package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;

public class ReviewRepository implements ReviewRepositoryInterface {

	private final ConcurrentHashMap<Long, Review> reviewInMemoryMap;
	private Long reviewKey;
	
	public ReviewRepository(ConcurrentHashMap<Long, Review> reviewMap) {
		checkNotNull(reviewMap, "reviewMap must not be null for ReviewRepository");
		reviewInMemoryMap = reviewMap;
		reviewKey = (long) 0;
	}
	private final Long generateReviewKey() {
		// increment existing isbnKey and return the new value
		return Long.valueOf(++reviewKey);
	    }
	public Review addReview(Review review){
		
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
	
	
	public List<Review> getAllReviews(){
		int totalReviews = reviewInMemoryMap.size();
		System.out.println("Number of reviews " + totalReviews);
		List<Review> reviewsList = new ArrayList<Review>();
		for(int i = 1; i < totalReviews+1 ; i++){
			reviewsList.add(getReviewById((long) i));
		}
		return reviewsList;
		
		
	}
	
	
	
	 
}
