package com.xkj.poetryserver.service;

import com.xkj.poetryserver.pojo.SingleTopicSelectionPuzzle;

public interface SingleGameService {

    SingleTopicSelectionPuzzle getPuzzle();

    boolean answer(String uid, String pid, String key);
}
