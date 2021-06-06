package com.sana.crudnkafkademo.services;

import com.sana.crudnkafkademo.exception.BookIsbnNotFoundException;
import com.sana.crudnkafkademo.model.Book;
import com.sana.crudnkafkademo.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sana.crudnkafkademo.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookServiceImpl implements BookService{

    @Autowired
    private BooksRepository booksRepository;

    @Override
    public List<Book> getAllbook() {
        List<Book> books = new ArrayList<Book>();
        booksRepository.findAll().forEach(Book -> books.add(Book));
        return books;
    }

    @Override
    public Book getbookByIsbn(String isbn)
    {
        Book book = null;
        try {
            book = booksRepository.findById(isbn).get();
        }catch (Exception e)
        {
            throw new BookIsbnNotFoundException("Invalid ISBN");
        }
        return book;
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        List<Book> books = booksRepository.getBookByTitle(title);
        if(books.isEmpty()) throw new BookNotFoundException("No book found containing this title");
        return books;
    }

    @Override
    public List<Book> getBookByAuthor(String author) {
        List<Book> books = booksRepository.getBookByAuthor(author);
        if(books.isEmpty()) throw new BookNotFoundException("No book found by this author");
        return books;
    }

    @Override
    public void updateBook(Book book) throws BookIsbnNotFoundException{
        try{
            booksRepository.save(book);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw new BookIsbnNotFoundException("Invalid ISBN");
        }
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
