package com.project.bookstore.Model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Book {

    private @Id Long id;

    private String name;

    private String author;

    private BigDecimal price;

    @JsonProperty("is_recommended")
    @Column(name = "is_recommended")
    private boolean isRecommended;

    public Book() {
    }

    public Book(Long id, String name, String author, BigDecimal price, boolean isRecommended) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.isRecommended = isRecommended;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean getIsRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    @Override
    public String toString() {
        return "Book [author=" + author + ", id=" + id + ", isRecommended=" + isRecommended + ", name=" + name
                + ", price=" + price + "]";
    }

}
