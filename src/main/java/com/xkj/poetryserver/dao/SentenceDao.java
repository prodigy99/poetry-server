package com.xkj.poetryserver.dao;

import com.xkj.poetryserver.domain.Sentence;

import java.util.ArrayList;

public interface SentenceDao {
    ArrayList<Sentence> getRandomResults(int num);
}
