package com.xkj.poetryserver.service;

import com.xkj.poetryserver.domain.User;

import java.util.List;

public interface UserService {

    User findUserById(String uid);

    User register(String uid, String nickName, String avatar);

    void updateUser(User user);

    void addUserExp(String uid,int exp);

    List<User> rankList();
}
