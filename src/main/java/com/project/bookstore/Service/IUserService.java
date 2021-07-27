package com.project.bookstore.Service;

import com.project.bookstore.Model.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    public User login(String username, String password);

    public User getCurrentUser();

    public User createUser(User user);

    public void deleteUser(User user);

}
