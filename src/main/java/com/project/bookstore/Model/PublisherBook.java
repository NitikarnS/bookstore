package com.project.bookstore.Model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublisherBook {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("book_name")
    private String name;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("author_name")
    private String authorName;

    public PublisherBook() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
