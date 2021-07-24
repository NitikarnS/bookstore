package com.project.bookstore.Model;

import java.util.List;

public class ResponseBooks {
    private List<Book> books;

    public ResponseBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
