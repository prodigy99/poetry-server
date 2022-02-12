package com.example.poetryserver.dao.impl;

import com.example.poetryserver.dao.WriterDao;
import com.example.poetryserver.domain.Writer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class WriterDaoImpl implements WriterDao {
    final MongoTemplate mongoTemplate;

    public WriterDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Writer findByName(String writerName) {
        Query query = new Query(Criteria.where("name").is(writerName));
        return this.mongoTemplate.findOne(query, Writer.class);
    }
}
