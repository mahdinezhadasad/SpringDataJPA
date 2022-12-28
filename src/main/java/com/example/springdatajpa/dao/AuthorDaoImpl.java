package com.example.springdatajpa.dao;

import com.example.springdatajpa.domain.Author;
import com.example.springdatajpa.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthorDaoImpl implements AuthorDao {
    
    private final AuthorRepository authorRepository;
    
    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    
    @Override
    public Author getById(Long id) {
        return authorRepository.getById (id);
    }
    
    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepository.findAuthorByFirstNameAndLastName (firstName,lastName)
                .orElseThrow (EntityNotFoundException::new);
    }
    
    
    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save (author);
        
    }
    
    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        Author foundAuthor = authorRepository.getById (author.getId ());
        foundAuthor.setFirstName (author.getFirstName ());
        foundAuthor.setLastName (author.getLastName ());
        return authorRepository.save (foundAuthor);
    }
    
    @Override
    public void deleteAuthorById(Long id) {
        
        authorRepository.deleteById (id);
        
    }
}