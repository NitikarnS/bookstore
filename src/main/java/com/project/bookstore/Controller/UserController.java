package com.project.bookstore.Controller;

import com.project.bookstore.Model.LoginForm;
import com.project.bookstore.Model.User;
import com.project.bookstore.Service.UserServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserServiceImpl userServiceImpl;

    UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        userServiceImpl.login(loginForm.getUsername(), loginForm.getPassword());
        return ResponseEntity.ok().build();
    }

}
