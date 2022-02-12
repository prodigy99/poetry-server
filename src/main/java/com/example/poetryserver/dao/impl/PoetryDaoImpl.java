package com.example.poetryserver.dao.impl;

import com.example.poetryserver.dao.PoetryDao;
import com.example.poetryserver.domain.Poetry;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PoetryDaoImpl implements PoetryDao {
    final MongoTemplate mongoTemplate;

    public PoetryDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Poetry findByTitle(String title) {
        return mongoTemplate.findOne(new Query(Criteria.where("title").is(title)),Poetry.class);
    }
}
