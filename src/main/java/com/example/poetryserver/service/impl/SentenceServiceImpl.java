package com.example.poetryserver.service.impl;

import com.example.poetryserver.utils.DbHelper;
import com.example.poetryserver.dao.SentenceDao;
import com.example.poetryserver.domain.Sentence;
import com.example.poetryserver.service.SentenceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentenceServiceImpl implements SentenceService {
    final SentenceDao sentenceDao;
    final DbHelper dbHelper;
    public SentenceServiceImpl(SentenceDao sentenceDao, DbHelper dbHelper) {
        this.sentenceDao = sentenceDao;
        this.dbHelper = dbHelper;
    }

    @Override
    public List<Sentence> getRandomSentences(int num) {
        return sentenceDao.getRandomResults(num);
    }

    @Override
    public Sentence getOneRandomSentence() {
        return dbHelper.getRandomResults(Sentence.class,1).get(0);
    }
}
