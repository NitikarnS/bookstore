package com.project.bookstore.Controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import com.project.bookstore.Model.LoginForm;
import com.project.bookstore.Model.OrderForm;
import com.project.bookstore.Model.RegisterForm;
import com.project.bookstore.Model.ResponseOrderSummary;
import com.project.bookstore.Model.ResponseUser;
import com.project.bookstore.Model.User;
import com.project.bookstore.Service.impl.OrderService;
import com.project.bookstore.Service.impl.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    private final OrderService orderService;

    UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        userService.login(loginForm.getUsername(), loginForm.getPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseUser> getUsers() {
        User currentUser = userService.getCurrentUser();
        ResponseUser userRs = new ResponseUser(currentUser.getName(), currentUser.getSurname(),
                currentUser.getDateOfBirth());
        userRs.setBooks(currentUser.getOrders().stream().map(i -> i.getId()).collect(Collectors.toList()));
        return ResponseEntity.ok().body(userRs);
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUsers(@Valid @RequestBody RegisterForm registerForm) {
        userService.createUser(
                new User(registerForm.getUsername(), registerForm.getPassword(), registerForm.getDateOfBirth()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUsers() {
        User currentUser = userService.getCurrentUser();
        userService.deleteUser(currentUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/orders")
    public ResponseEntity<ResponseOrderSummary> orders(@RequestBody OrderForm orderForm) {
        User currentUser = userService.getCurrentUser();
        User user = orderService.order(currentUser.getId(), orderForm.getOrders());
        ResponseOrderSummary orderSummary = new ResponseOrderSummary(user.getTotalPriceOrderBooks());
        return ResponseEntity.ok().body(orderSummary);
    }

}
