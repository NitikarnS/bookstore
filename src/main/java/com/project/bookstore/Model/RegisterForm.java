package com.project.bookstore.Model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterForm {
    
    @NotBlank()
    private String username;

    @NotBlank()
    private String password;

    @NotBlank()
    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    
}
