package com.sana.crudnkafkademo.services;

import com.sana.crudnkafkademo.exception.BookIsbnNotFoundException;
import com.sana.crudnkafkademo.model.Book;
import com.sana.crudnkafkademo.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import com.sana.crudnkafkademo.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
@ComponentScan("com.sana.crudnkafkademo.services")
public class BookServiceImpl implements BookService{

    private static final String TOPIC = "Book-Transaction-History";

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * Get all stored book list
     * @return the complete list of book
     */
    @Override
    public List<Book> getAllbook() {
        List<Book> books = new ArrayList<Book>();
        booksRepository.findAll().forEach(Book -> books.add(Book));
        return books;
    }

    /**
     * Find a book by ISBN
     * @param isbn as the input
     * @return a book record
     */

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

    /**
     * Find all books containing provided title
     * @param title as the input
     * @return the book list
     */
    @Override
    public List<Book> getBookByTitle(String title) {
        List<Book> books = booksRepository.getBookByTitle(title);
        if(books.isEmpty()) throw new BookNotFoundException("No book found containing this title");
        return books;
    }


    /**
     * Find all books containing provided author name
     * @param author as the input
     * @return the book list
     */
    @Override
    public List<Book> getBookByAuthor(String author) {
        List<Book> books = booksRepository.getBookByAuthor(author);
        if(books.isEmpty()) throw new BookNotFoundException("No book found by this author");
        return books;
    }

    /**
     * update a book to database
     * @param book as the input
     * @throws BookIsbnNotFoundException
     */
    @Override
    public void updateBook(Book book) throws BookIsbnNotFoundException{
        try{
            booksRepository.save(book);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw new BookIsbnNotFoundException("Invalid ISBN");
        }
    }

    /**
     * Save a book to the database
     * @param book as the input
     */
    public void save(Book book)
    {
        booksRepository.save(book);

        kafkaProducerService.sendMessage(TOPIC, "New Book Saved: " + book.toString());
    }

    /**
     * delete a book from database by provided ISBN
     * @param isbn as the input
     */
    public void delete(String isbn)
    {
        booksRepository.deleteById(isbn);
        kafkaProducerService.sendMessage(TOPIC, "Book deleted with ISBN: " + isbn);
    }

}
