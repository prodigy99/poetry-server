package com.xkj.poetryserver.dao;

import com.xkj.poetryserver.domain.User;

import java.util.List;


public interface UserDao {
    User findById(String uid);
    void save(User user);
    void updateUser(User user);

    List<User> top(int size);
}
