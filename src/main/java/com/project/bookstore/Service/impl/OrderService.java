package com.project.bookstore.Service.impl;

import java.util.List;

import com.project.bookstore.Model.Book;
import com.project.bookstore.Model.User;
import com.project.bookstore.Repository.BookRepository;
import com.project.bookstore.Repository.UserRepository;
import com.project.bookstore.Service.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public User order(Long userId, List<Long> bookIdList) {
        User user = userRepository.findById(userId).get();
        List<Book> books = bookRepository.findAllById(bookIdList);
        user.setOrders(books);
        return userRepository.save(user);
    }

}
