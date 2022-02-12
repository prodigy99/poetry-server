package com.example.poetryserver.dao;

import com.example.poetryserver.domain.WrongTopic;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigInteger;
import java.util.List;

public interface WrongTopicDao {
    void save(WrongTopic wrongTopic);
    void delete(Query query);
    WrongTopic findById(BigInteger id);
    List<WrongTopic> findAll(Query query);
}
