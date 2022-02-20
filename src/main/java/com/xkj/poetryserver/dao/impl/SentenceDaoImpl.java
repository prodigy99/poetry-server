package com.xkj.poetryserver.dao.impl;

import com.xkj.poetryserver.dao.SentenceDao;
import com.xkj.poetryserver.domain.Sentence;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class SentenceDaoImpl implements SentenceDao {
    final MongoTemplate mongoTemplate;

    public SentenceDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ArrayList<Sentence> getRandomResults(int num) {
        Aggregation agg = Aggregation.newAggregation(Sentence.class, Aggregation.sample(num));
        AggregationResults<Sentence> sentenceAggregationResults = mongoTemplate.aggregate(agg, Sentence.class, Sentence.class);

        ArrayList<Sentence> sentences = new ArrayList<>(num);
        sentenceAggregationResults.forEach(sentences::add);
        return sentences;
    }
}
