package com.example.poetryserver.service;

import com.example.poetryserver.pojo.SingleTopicSelectionPuzzle;

public interface SingleGameService {

    SingleTopicSelectionPuzzle getPuzzle();

    boolean answer(String uid, String pid, String key);
}
