package com.example.poetryserver.pojo.puzzle;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SingleTopicSelectionPuzzle {
    private String question;
    private String[] options;
    private int key;
}
