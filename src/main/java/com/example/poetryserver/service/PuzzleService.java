package com.example.poetryserver.service;

import com.example.poetryserver.dto.CrosswordPuzzleDTO;
import com.example.poetryserver.pojo.SingleTopicSelectionPuzzle;

public interface PuzzleService {
    boolean checkKey(String pid, String key, boolean remove);

    // 根据pid获得puzzle
    boolean checkKey(String pid, String key);
    SingleTopicSelectionPuzzle getTitleWriterPuzzle();
    SingleTopicSelectionPuzzle getTitleSentenceQuestion();
    CrosswordPuzzleDTO getCrosswordPuzzles(int num);
}
