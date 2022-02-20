package com.xkj.poetryserver.service;

import com.xkj.poetryserver.domain.Sentence;

import java.util.List;

public interface SentenceService {
    List<Sentence> getRandomSentences(int num);
    Sentence getOneRandomSentence();

}
