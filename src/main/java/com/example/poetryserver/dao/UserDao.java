package com.example.poetryserver.dao;

import com.example.poetryserver.domain.User;


public interface UserDao {
    User findById(String uid);
    void save(User user);
    void updateUser(User user);
}
