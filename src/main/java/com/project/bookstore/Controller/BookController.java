package com.project.bookstore.Controller;

import com.project.bookstore.Model.Book;
import com.project.bookstore.Model.ResponseBooks;
import com.project.bookstore.Repository.BookRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    @ResponseBody
    public ResponseBooks books() {
        return new ResponseBooks(bookRepository.listAllBook());
    }

}
