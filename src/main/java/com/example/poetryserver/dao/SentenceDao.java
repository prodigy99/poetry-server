package com.example.poetryserver.dao;

import com.example.poetryserver.domain.Sentence;
import java.util.ArrayList;

public interface SentenceDao {
    ArrayList<Sentence> getRandomResults(int num);
}
