package com.xkj.poetryserver.dao.impl;

import com.xkj.poetryserver.dao.UserDao;
import com.xkj.poetryserver.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    final MongoTemplate mongoTemplate;

    public UserDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User findById(String uid) {
        return mongoTemplate.findOne(new Query(Criteria.where("uid").is(uid)), User.class);
    }

    @Override
    public void save(User user) {
        mongoTemplate.save(user);
    }

    @Override
    public void updateUser(User user) {
        // 更新User
        mongoTemplate.save(user);
    }

    @Override
    public List<User> top(int size) {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.asc("level")));
        query.limit(size);

        return mongoTemplate.find(query, User.class);
    }

}
