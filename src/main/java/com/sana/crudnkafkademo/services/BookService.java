package com.sana.crudnkafkademo.services;

import com.sana.crudnkafkademo.exception.BookIsbnNotFoundException;
import com.sana.crudnkafkademo.exception.BookNotFoundException;
import com.sana.crudnkafkademo.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    List<Book> getAllbook();

    Book getbookByIsbn(String isbn) throws BookIsbnNotFoundException;

    void delete(String isbn) throws BookNotFoundException;

    void saveOrUpdate(Book book);

    List<Book> getBookByTitle(String title) throws BookNotFoundException;

    List<Book> getBookByAuthor(String author) throws BookNotFoundException;

    void updateBook(Book book);
}
