package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Book;

public class BookRepository implements BookRepositoryInterface {
	/** In-memory map to store books. (Key, Value) -> (ISBN, Book) */
	private final ConcurrentHashMap<Long, Book> bookInMemoryMap;

	/** Never access this key directly; instead use generateISBNKey() */
	private long isbnKey;

	public BookRepository(ConcurrentHashMap<Long, Book> bookMap) {
		checkNotNull(bookMap, "bookMap must not be null for BookRepository");
		bookInMemoryMap = bookMap;
		isbnKey = 0;
	}

	/**
	 * This should be called if and only if you are adding new books to the
	 * repository.
	 * 
	 * @return a new incremental ISBN number
	 */
	private final Long generateISBNKey() {
		// increment existing isbnKey and return the new value
		return Long.valueOf(++isbnKey);
	}

	/**
	 * This will auto-generate unique ISBN for new books.
	 */
	@Override
	public Book saveBook(Book newBook) {
		checkNotNull(newBook, "newBook instance must not be null");
		// Generate new ISBN
		Long isbn = generateISBNKey();
		newBook.setIsbn(isbn);
		//newBook.setLanguage(newBook.getLanguage());
		//newBook.setnum_pages(newBook.getnum_pages());

		// TODO: create and associate other fields such as author

		// Finally, save the new book into the map
		bookInMemoryMap.putIfAbsent(isbn, newBook);

		return newBook;
	}

	/**
	 * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#getBookByISBN(java.lang.Long)
	 */
	@Override
	public Book getBookByISBN(Long isbn) {
		checkArgument(isbn > 0,
				"ISBN was %s but expected greater than zero value", isbn);
		return bookInMemoryMap.get(isbn);
	}

	public boolean updateStatusbyIsbn(Long isbn , String status){
		try{
		Book book = new Book();
		if(bookInMemoryMap.containsKey(isbn)){
			book = bookInMemoryMap.get(isbn);
			book.setStatus(status);
			bookInMemoryMap.replace(isbn, book);
			return true;
		}
		}
		catch(Exception ex){
			System.out.println("Exception raised");
		}

		return false;

	}
	
	public boolean deleteBookByIsbn(Long isbn){
		
		if(bookInMemoryMap.containsKey(isbn)){
			bookInMemoryMap.remove(isbn);
			return true;
		}
		return false;
		
	}

	/*public Book.Review saveReview(Book.Review newReview){
    	newReview.setRating(newReview.getRating());
    	newReview.setComment(newReview.getComment());
    	return newReview;
    }*/

}
