package com.sana.crudnkafkademo.controller;

import com.sana.crudnkafkademo.model.Book;
import com.sana.crudnkafkademo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    private static final String TOPIC = "Book-Transaction-History";

    /**
     * This API will return all the books listed
     * @return all listed books
     */
    @GetMapping("/all")
    private List<Book> getAllbook() {
        return bookService.getAllbook();
    }

    /**
     * Get a book by ISBN
     * @param isbn as criteria
     * @return single book
     */
    @GetMapping("/isbn/{isbn}")
    private Book getbookByIsbn(@PathVariable("isbn") String isbn) {
        return bookService.getbookByIsbn(isbn);
    }

    /**
     * This custom method get all books containing specified title
     * by ignoring case
     * @param title as criteria
     * @return list of books
     */
    @GetMapping("/title/{title}")
    private List<Book> getbookByTitle(@PathVariable("title") String title) {
        return bookService.getBookByTitle(title.toUpperCase());
    }

    /**
     * This custom method get all books containing specified author
     * by ignoring case
     * @param author as criteria
     * @return list of books
     */
    @GetMapping("/author/{author}")
    private List<Book> getbookByAuthor(@PathVariable("author") String author) {
        return bookService.getBookByAuthor(author.toUpperCase());
    }

    /**
     * This API delete a book with specified ISBN
     * @param isbn of deleted book
     */
    @DeleteMapping("/delete/{isbn}")
    private void deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);
        kafkaTemplate.send(TOPIC, "Book deleted with ISBN: " + isbn);
    }

    /**
     * This API save or update a book
     * @param book to be saved
     * @return ISBN number of the saved book
     */
    @PostMapping("/save")
    private String savebook(@Valid @RequestBody Book book) {
        bookService.saveOrUpdate(book);
        kafkaTemplate.send(TOPIC, "New Book Saved: " + book.toString());
        return book.getIsbn();
    }
}    