package com.example.poetryserver.service;

import com.example.poetryserver.dto.CrosswordPuzzleDTO;
import com.example.poetryserver.pojo.SingleTopicSelectionPuzzle;

public interface PuzzleService {
    void removePuzzle(String pid);

    boolean checkKey(String pid, String key, boolean remove);

    // 根据pid获得puzzle
    boolean checkKey(String pid, String key);
    SingleTopicSelectionPuzzle getTitleWriterPuzzle();
    SingleTopicSelectionPuzzle getTitleSentenceQuestion();

    SingleTopicSelectionPuzzle getSingleTopicSelectionPuzzle();

    CrosswordPuzzleDTO getCrosswordPuzzles(int num);
}
