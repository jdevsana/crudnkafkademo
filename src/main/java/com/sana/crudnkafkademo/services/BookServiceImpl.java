package com.sana.crudnkafkademo.services;

import com.sana.crudnkafkademo.model.Book;
import com.sana.crudnkafkademo.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BookServiceImpl implements BookService{

    @Autowired
    private BooksRepository booksRepository;

    @Override
    public List<Book> getAllbook() {
        List<Book> Books = new ArrayList<Book>();
        booksRepository.findAll().forEach(Book -> Books.add(Book));
        return Books;
    }

    //getting a specific record
    @Override
    public Book getbookByIsbn(String isbn)
    {
        return booksRepository.findById(isbn).get();
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        return booksRepository.getBookByTitle(title);
    }

    @Override
    public List<Book> getBookByAuthor(String author) {
        return booksRepository.getBookByAuthor(author);
    }

    public void saveOrUpdate(Book Book)
    {
        booksRepository.save(Book);
    }
    //deleting a specific record
    public void delete(String isbn)
    {
        booksRepository.deleteById(isbn);
    }

}
