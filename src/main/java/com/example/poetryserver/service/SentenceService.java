package com.example.poetryserver.service;

import com.example.poetryserver.domain.Poetry;
import com.example.poetryserver.domain.Sentence;

import java.util.List;

public interface SentenceService {
    List<Sentence> getRandomSentences(int num);
    Sentence getOneRandomSentence();

}
