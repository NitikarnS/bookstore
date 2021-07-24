package com.project.bookstore.Repository;

import java.util.List;

import com.project.bookstore.Model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b ORDER BY b.isRecommended desc, b.name, b.author")
    public List<Book> listAllBook();

}
