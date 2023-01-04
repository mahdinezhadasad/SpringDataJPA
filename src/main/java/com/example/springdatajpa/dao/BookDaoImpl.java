package com.example.springdatajpa.dao;

import com.example.springdatajpa.domain.Book;
import com.example.springdatajpa.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
    
    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll ();
    }
    
    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
       Pageable pageable = PageRequest.ofSize (pageSize);
       if(offset>0){
           
           pageable = pageable.withPage (offset/pageSize);
       }
       else {
           pageable = pageable.withPage (0);
       }
       return this.findAllBooks (pageable);
    }
    
    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll (pageable).getContent ();
    }
    
    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
    
        Page<Book>  bookPage = bookRepository.findAll (pageable);
        
        return bookPage.getContent ();
    }
}