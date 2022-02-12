package com.example.poetryserver.service;

import com.example.poetryserver.domain.User;

public interface UserService {

    User findUserById(String uid);

    User register(String uid, String nickName, String avatar);

    void updateUser(User user);
}
