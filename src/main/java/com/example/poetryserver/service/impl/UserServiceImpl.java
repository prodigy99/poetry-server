package com.example.poetryserver.service.impl;

import com.example.poetryserver.dao.UserDao;
import com.example.poetryserver.domain.User;
import com.example.poetryserver.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    final UserDao userDao;



    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserById(String uid) {
        return userDao.findById(uid);
    }

    @Override
    public User register(String uid, String nickName, String avatar){
        User user = new User(uid,nickName,avatar,1,0,0,0,0);
        userDao.save(user);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void addUserExp(String uid, int exp) {
        User user = this.findUserById(uid);
        user.setExp(user.getExp() + exp);
        this.updateUser(user);
    }

}
