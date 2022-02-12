package com.example.poetryserver.dao.impl;

import com.example.poetryserver.dao.WrongTopicDao;
import com.example.poetryserver.domain.WrongTopic;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class WrongTopicDaoImpl implements WrongTopicDao {
    final MongoTemplate mongoTemplate;

    public WrongTopicDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void save(WrongTopic wrongTopic) {
        mongoTemplate.save(wrongTopic);
    }

    @Override
    public void delete(Query query) {
        mongoTemplate.remove(query,WrongTopic.class);
    }

    @Override
    public WrongTopic findById(BigInteger id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),WrongTopic.class);
    }

    @Override
    public List<WrongTopic> findAll(Query query) {
        return mongoTemplate.find(query,WrongTopic.class);
    }
}
