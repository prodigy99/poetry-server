package com.example.poetryserver.service;

import com.example.poetryserver.pojo.puzzle.CrosswordPuzzles;
import com.example.poetryserver.pojo.puzzle.SingleTopicSelectionPuzzle;

public interface PuzzleService {
    SingleTopicSelectionPuzzle getTitleWriterPuzzle();
    SingleTopicSelectionPuzzle getTitleSentenceQuestion();
    CrosswordPuzzles getCrosswordPuzzles(int num);
}
