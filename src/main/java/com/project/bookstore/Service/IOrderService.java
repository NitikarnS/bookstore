package com.project.bookstore.Service;

import java.util.List;

import com.project.bookstore.Model.User;

public interface IOrderService {

    public User order(Long userId, List<Long> bookIdList);

}
