package edu.sjsu.cmpe.library.repository;

import edu.sjsu.cmpe.library.domain.Book;

/**
 * Book repository interface.
 * 
 * What is repository pattern?
 * 
 * @see http://martinfowler.com/eaaCatalog/repository.html
 */
public interface BookRepositoryInterface {
    /**
     * Save a new book in the repository
     * 
     * @param newBook
     *            a book instance to be create in the repository
     * @return a newly created book instance with auto-generated ISBN
     */
    Book saveBook(Book newBook);

    /**
     * Retrieve an existing book by ISBN
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Book getBookByISBN(Long isbn);
    boolean updateStatusbyIsbn(Long isbn , String status);
    boolean deleteBookByIsbn(Long isbn);
    
    //Book.Review saveReview(Book.Review newReview);

    // TODO: add other operations here!
}
