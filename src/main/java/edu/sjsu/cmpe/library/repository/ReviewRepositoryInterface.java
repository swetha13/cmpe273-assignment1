package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;

public interface ReviewRepositoryInterface {
    
	Review addReview(Review review);
	Review getReviewById(Long id) ;
	List<Review> getAllReviews();
    
}
