package com.project.bookstore.Service;

import com.project.bookstore.Model.User;

public interface IUserService {

    public User login(String username, String password);

    public User getCurrentUser();

}
