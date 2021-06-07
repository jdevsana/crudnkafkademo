package com.sana.crudnkafkademo.repository;

import com.sana.crudnkafkademo.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BooksRepository extends CrudRepository<Book, String> {

    /**
     * This custom method retrieve all books containing specified title
     * by ignoring case
     * @param title
     * @return list of books
     */
    @Query("SELECT b FROM Book b WHERE upper(b.title) like %?1%")
    List<Book> getBookByTitle(String title);

    /**
     * This custom method retrieve all books containing specified author
     * by ignoring case
     * @param author
     * @return list of books
     */
    @Query("SELECT b FROM Book b WHERE upper(b.author) like %?1%")
    List<Book> getBookByAuthor(String author);

}
