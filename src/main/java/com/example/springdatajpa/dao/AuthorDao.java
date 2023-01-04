package com.example.springdatajpa.dao;


import com.example.springdatajpa.domain.Author;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorDao {
    Author getById(Long id);
    
    Author findAuthorByName(String firstName, String lastName);
    
    Author saveNewAuthor(Author author);
    
    Author updateAuthor(Author author);
    
    void deleteAuthorById(Long id);
    
    List<Author> findAllAuthorByLastName(String lastname, Pageable pageable);
}