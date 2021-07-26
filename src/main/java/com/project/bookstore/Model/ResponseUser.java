package com.project.bookstore.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUser {

    private String name;

    private String surname;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    private List<Long> books;

    public ResponseUser(String name, String surname, String dateOfBirth, List<Long> books) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.books = books;
    }

    public ResponseUser(String name, String surname, String dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }



    public String getName() {   
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Long> getBooks() {
        return books;
    }

    public void setBooks(List<Long> books) {
        this.books = books;
    }

    

}
