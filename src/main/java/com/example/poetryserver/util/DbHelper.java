package com.example.poetryserver.util;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DbHelper {
    final MongoTemplate mongoTemplate;

    public DbHelper(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public <E> List<E> getRandomResults(Class<E> domainClass, int num) {
        Aggregation agg = Aggregation.newAggregation(domainClass, Aggregation.sample(num));
        AggregationResults<E> sentenceAggregationResults = mongoTemplate.aggregate(agg, domainClass, domainClass);
        ArrayList<E> results = new ArrayList<>(num);
        sentenceAggregationResults.forEach(results::add);
        return results;
    }

    public <E> E getOneRandomResult(Class<E> domainClass) {
        return getRandomResults(domainClass, 1).get(0);
    }
}
