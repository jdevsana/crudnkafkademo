package com.sana.crudnkafkademo.controller;

import com.sana.crudnkafkademo.exception.BookIsbnNotFoundException;
import com.sana.crudnkafkademo.exception.BookNotFoundException;
import com.sana.crudnkafkademo.model.Book;
import com.sana.crudnkafkademo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
    private ResponseEntity<List<Book>> getAllBook() throws BookNotFoundException {
        List<Book> books = bookService.getAllbook();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    /**
     * Get a book by ISBN
     * @param isbn as criteria
     * @return single book
     */
    @GetMapping("/isbn/{isbn}")
    private ResponseEntity<Book> getBookByIsbn(@PathVariable("isbn") String isbn){
        Book book = bookService.getbookByIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    /**
     * This custom method get all books containing specified title
     * by ignoring case
     * @param title as criteria
     * @return list of books
     */
    @GetMapping("/title/{title}")
    private ResponseEntity<List<Book>> getBookByTitle(@PathVariable("title") String title) throws BookNotFoundException {
        List<Book> books = bookService.getBookByTitle(title.toUpperCase());
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    /**
     * This custom method get all books containing specified author
     * by ignoring case
     * @param author as criteria
     * @return list of books
     */
    @GetMapping("/author/{author}")
    private ResponseEntity<List<Book>> getBookByAuthor(@PathVariable("author") String author) throws BookNotFoundException {
        List<Book> books = bookService.getBookByAuthor(author.toUpperCase());
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    /**
     * This API delete a book with specified ISBN
     * @param isbn of deleted book
     */
    @DeleteMapping("/delete/{isbn}")
    private void deleteBook(@PathVariable("isbn") String isbn) throws BookNotFoundException {
        bookService.delete(isbn);
        kafkaTemplate.send(TOPIC, "Book deleted with ISBN: " + isbn);
    }

    /**
     * This API save or update a book
     * @param book to be saved
     * @return ISBN number of the saved book
     */
    @PostMapping("/save")
    private ResponseEntity<String> saveBook(@Valid @RequestBody Book book) {
        bookService.saveOrUpdate(book);
        kafkaTemplate.send(TOPIC, "New Book Saved: " + book.toString());
        return ResponseEntity.status(HttpStatus.OK).body("Saved book ISBN:" + book.getIsbn());

    }

    /**
     * This API update a book by ISBN
     * @param book
     * @return ResponseEntity
     * @throws BookIsbnNotFoundException
     */
    @PutMapping("/update")
    ResponseEntity<Void> updateBook(@RequestBody Book book)throws BookIsbnNotFoundException {
        bookService.updateBook(book);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}    