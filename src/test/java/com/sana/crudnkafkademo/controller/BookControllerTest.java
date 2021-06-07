package com.sana.crudnkafkademo.controller;

import com.sana.crudnkafkademo.model.Book;
import com.sana.crudnkafkademo.services.BookService;
import org.h2.util.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.ws.rs.core.MediaType;

@AutoConfigureMockMvc
@WebMvcTest( value = BookController.class)
public class BookControllerTest {

    private static Book book;

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Should list all books when making GET request to endpoint - /api/v1/books/all")
    void getAllbookTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 9, 12);
        book = new Book("9781259589331", "The Complete Ref.", "Schildt", calendar.getTime());
        bookService.save(book);
        final String uri = "/api/v1/books/all";
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    @Test
    @DisplayName("Save a books when making GET request to endpoint - /api/v1/books/save")
    void getBookByIsbnTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 9, 12);
        book = new Book("9781259589331", "The Complete Ref.", "Schildt", calendar.getTime());
        final String uri = "/api/v1/books/isbn/9781259589331";

        Mockito.when(bookService.getbookByIsbn(Mockito.anyString())).thenReturn(book);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(book.getAuthor()));
        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(book.getTitle()));
        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(book.getIsbn()));
    }


    @Test
    @DisplayName("Get a book when making GET request to endpoint - /api/v1/books/title/{title}")
    void getBookByTitleTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 9, 12);
        book = new Book("9781259589331", "The Complete Ref.", "Schildt", calendar.getTime());
        final String uri = "/api/v1/books/title/"+book.getTitle();
        List<Book> booklist1 = new ArrayList<Book>();
        booklist1.add(book);

        Mockito.when(bookService.getBookByTitle(Mockito.anyString())).thenReturn(booklist1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(book.getAuthor()));
        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(book.getTitle()));
        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(book.getIsbn()));
    }


    @Test
    @DisplayName("Get a book when making GET request to endpoint - /api/v1/books/title/{author}")
    void getBookByAuthorTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 9, 12);
        book = new Book("9781259589331", "The Complete Ref.", "Schildt", calendar.getTime());
        final String uri = "/api/v1/books/author/"+book.getAuthor();

        List<Book> booklist1 = new ArrayList<Book>();
        booklist1.add(book);

        Mockito.when(bookService.getBookByAuthor(Mockito.anyString())).thenReturn(booklist1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(book.getAuthor()));
        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(book.getTitle()));
        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(book.getIsbn()));
    }



    @AfterEach
    void tearDown() {
        book = null;
        bookService = null;
        mockMvc = null;
    }
}