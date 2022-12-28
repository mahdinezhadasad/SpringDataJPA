package com.example.springdatajpa.dao;

import com.example.springdatajpa.domain.Book;
import com.example.springdatajpa.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class BookDaoImpl implements BookDao {
    
    private final BookRepository bookRepository;
    
    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public Book getById(Long id) {
        return bookRepository.getById (id);
    }
    
    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findBookByTitle (title).orElseThrow (EntityNotFoundException::new);
    }
    
    @Transactional
    @Override
    public Book saveNewBook(Book book) {
        return bookRepository.save (book);
    }
    
    @Override
    public Book updateBook(Book book) {
        
        Book foundBook = bookRepository.getReferenceById (book.getId ());
        foundBook.setIsbn (book.getIsbn ());
        foundBook.setPublisher (book.getPublisher ());
        foundBook.setAuthorId (book.getAuthorId ());
        foundBook.setTitle (book.getTitle ());
        return bookRepository.save (foundBook);
    }
    
    @Override
    public void deleteBookById(Long id) {
     bookRepository.deleteById (id);
    }
}