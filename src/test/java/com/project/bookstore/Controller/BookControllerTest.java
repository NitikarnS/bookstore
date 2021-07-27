package com.project.bookstore.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.UpdateBookTask;
import com.project.bookstore.Model.Book;
import com.project.bookstore.Repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(value = BookController.class)
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    UpdateBookTask updateBookTask;

    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    @Test
    public void getAllBooks() throws Exception {
        List<Book> books = new ArrayList<>(Arrays.asList(new Book(1L, "book A", "author A", new BigDecimal(100), true),
                new Book(2L, "book B", "author B", new BigDecimal(150), true),
                new Book(3L, "book F", "author B", new BigDecimal(80), false)));

        Mockito.when(bookRepository.listAllBook()).thenReturn(books);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("books", hasSize(3)))
                .andExpect(jsonPath("books[1].name", is("book B")));
    }
}
