package com.sana.crudnkafkademo.services;

import com.sana.crudnkafkademo.model.Book;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface BookService {

    List<Book> getAllbook();

    Book getbookByIsbn(String isbn);

    void delete(String isbn);

    void saveOrUpdate(Book book);

    List<Book> getBookByTitle(String title);

    List<Book> getBookByAuthor(String author);
}
