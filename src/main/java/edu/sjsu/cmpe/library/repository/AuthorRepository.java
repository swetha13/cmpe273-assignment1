package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;

public class AuthorRepository implements AuthorRepositoryInterface {

	private final ConcurrentHashMap<Long, Author> authorInMemoryMap= new ConcurrentHashMap<Long, Author>();
	private Long authorKey;
	private final BookRepositoryInterface bookRepository;
	
	public AuthorRepository(BookRepositoryInterface bookRepository) {
		// TODO Auto-generated constructor stub
		this.bookRepository = bookRepository;
	}
	
	 private final Long generateAuthorKey(){
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
			int totalAuthors = authorInMemoryMap.size();
			System.out.println("Number of Authors " + totalAuthors);
			List<Author> authorsList = new ArrayList<Author>();
			for(int i = 1; i < totalAuthors+1 ; i++){
				authorsList.add(getAuthorByID((long) i));
			}
			return authorsList;
			
			
		}
}
