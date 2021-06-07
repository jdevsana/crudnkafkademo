package com.sana.crudnkafkademo.services;

import com.sana.crudnkafkademo.model.Book;
import com.sana.crudnkafkademo.repository.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
//@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @MockBean
    private BooksRepository booksRepository;

    List<Book> books = new ArrayList<Book>();
    Book book1=null, book2=null;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 9, 12);
        book1 = new Book("9781259589331", "The Java Complete Ref.", "Schildt", calendar.getTime());
        calendar.set(2018, 3, 12);
        book2 = new Book("9781260117387", "Java 8 Exam", "Bates", calendar.getTime());
        books.add(book1);
        books.add(book2);
    }

    @Test
    @DisplayName("Get all listed books")
    public void getAllbookTest(){
        when(booksRepository.findAll()).thenReturn(books);
        assertEquals(2, bookServiceImpl.getAllbook().size());
    }

    @Test
    @DisplayName("Get book by title")
    public void getBookByTitleTest()
    {
        List<Book> listbooks1 = new ArrayList<Book>();
        listbooks1.add(book1);
        List<Book> listbooks2 = new ArrayList<Book>();
        listbooks2.add(book2);
        Mockito.when(booksRepository.getBookByTitle("The Java Complete Ref.")).thenReturn(listbooks1);
        assertEquals("9781259589331", bookServiceImpl.getBookByTitle("The Java Complete Ref.").get(0).getIsbn());
        Mockito.when(booksRepository.getBookByTitle("Java 8 Exam")).thenReturn(listbooks2);
        assertEquals("9781260117387", bookServiceImpl.getBookByTitle("Java 8 Exam").get(0).getIsbn());
    }

    @Test
    @DisplayName("Get book by title")
    public void getBookByAuthorTest()
    {
        List<Book> listbooks1 = new ArrayList<Book>();
        listbooks1.add(book1);
        List<Book> listbooks2 = new ArrayList<Book>();
        listbooks2.add(book2);
        Mockito.when(booksRepository.getBookByAuthor("Schildt")).thenReturn(listbooks1);
        assertEquals("9781259589331", bookServiceImpl.getBookByAuthor("Schildt").get(0).getIsbn()); //
        Mockito.when(booksRepository.getBookByAuthor("Bates")).thenReturn(listbooks2);
        assertEquals("9781260117387", bookServiceImpl.getBookByAuthor("Bates").get(0).getIsbn()); //
    }

}