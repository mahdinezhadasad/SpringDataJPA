package com.example.springdatajpa;


import com.example.springdatajpa.dao.BookDao;
import com.example.springdatajpa.domain.Book;
import com.example.springdatajpa.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.example.springdatajpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    BookDao bookDao;
    

    
    @Test
    void testEmptyResultException() {
        
        assertThrows (EmptyResultDataAccessException.class, () -> {
            
            Book book = bookRepository.readByTitle ("seeeree");
        });
    }
    
    @Test
    void testBookStream() {
        
        AtomicInteger count = new AtomicInteger ();
        
        bookRepository.findAllByTitleNotNull ().forEach (book -> {
            count.incrementAndGet ();
        });
        
        assertThat (count.get ()).isGreaterThan (4);
    }
    
    @Test
    void testNullParam() {
        
        assertNull (bookRepository.getByTitle (null));
    }
    
    @Test
    void testNoException() {
        
        assertNull (bookRepository.getByTitle ("foo"));
    }
    
    @Test
    void testBookFuture() throws ExecutionException, InterruptedException {
        Future<Book> bookFuture = bookRepository.queryByTitle ("Clean Code");
        
        Book book = bookFuture.get ();
        
        assertNotNull (book);
        
    }
    
    @Test
    void testBookQuery() {
        
        Book book = bookRepository.findBookByTitleWithQuery ("Clean Code");
        assertThat (book).isNotNull ();
    }
    
    @Test
    void testBookQueryNamed() {
        
        Book book = bookRepository.findBookByTitleWithQueryNamed ("Clean Code");
        assertThat (book).isNotNull ();
    }
    
    @Test
    void testBookQueryNativeQuery() {
        
        Book book = bookRepository.findBookByTitleNativeQuery ("Clean Code");
        assertThat (book).isNotNull ();
    }
    
    
    @Test
    void testBookJPANamedQuery() {
        
        Book book = bookRepository.jpaNamed ("Clean Code");
        assertThat (book).isNotNull ();
    }
    
    @Test
    void findAllBooksPage1_SortByTitle(){
        List<Book> books = bookDao.findAllBooksSortByTitle (PageRequest.of(0,10,Sort.by(Sort.Order.desc ("title"))));
        
        assertThat(books).isNotNull();
        assertThat (books.size ()).isEqualTo (5);
        
    }
}