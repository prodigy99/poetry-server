package com.xkj.poetryserver.service;

import com.xkj.poetryserver.dto.CrosswordPuzzleDTO;
import com.xkj.poetryserver.pojo.SingleTopicSelectionPuzzle;

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
