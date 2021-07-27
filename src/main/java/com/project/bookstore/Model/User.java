package com.project.bookstore.Model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @ManyToMany()
    @JoinTable(name = "order_book", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    private List<Book> orders;

    public User() {
    }

    public User(String username, String password, String dateOfBirth) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return username.split("\\.")[0];
    }

    public String getSurname() {
        String[] usernameSplit = username.split("\\.");
        return usernameSplit.length > 1 ? usernameSplit[1] : "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Book> getOrders() {
        return orders;
    }

    public void setOrders(List<Book> orders) {
        this.orders = orders;
    }

    public BigDecimal getTotalPriceOrderBooks() {
        if (orders == null) return BigDecimal.ZERO;
        return orders.stream().map(b -> b.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
