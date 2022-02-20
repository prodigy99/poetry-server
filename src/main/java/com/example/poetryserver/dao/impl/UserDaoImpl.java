package com.example.poetryserver.dao.impl;

import com.example.poetryserver.dao.UserDao;
import com.example.poetryserver.domain.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    final MongoTemplate mongoTemplate;

    public UserDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User findById(String uid) {
        return mongoTemplate.findOne(new Query(Criteria.where("uid").is(uid)),User.class);
    }

    @Override
    public void save(User user){
        mongoTemplate.save(user);
    }

    @Override
    public void updateUser(User user) {
        // 更新User
        mongoTemplate.save(user);
    }



}
