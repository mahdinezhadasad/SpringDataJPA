package com.example.springdatajpa.dao;

import com.example.springdatajpa.domain.Book;

public interface BookDao {
    
    Book getById(Long id);
    Book findBookByTitle(String title);
    Book saveNewBook(Book book);
    Book updateBook(Book book);
    void  deleteBookById(Long id);
}