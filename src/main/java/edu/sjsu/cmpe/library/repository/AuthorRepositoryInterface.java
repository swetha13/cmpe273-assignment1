package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;

public interface AuthorRepositoryInterface {
    
	  Author addAuthor(Author author);
	  Author getAuthorByID(Long id);
	  List<Author> getAllAuthors();
    
}
