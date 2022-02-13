package com.example.poetryserver.dto;

import com.example.poetryserver.pojo.SingleTopicSelectionPuzzle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class SingleTopicSelectionPuzzleDTO implements Serializable {
    private String title;
    private String[] options;
    private String pid;

    public SingleTopicSelectionPuzzleDTO(SingleTopicSelectionPuzzle singleTopicSelectionPuzzle) {
        this.title = singleTopicSelectionPuzzle.getTitle();
        this.options = singleTopicSelectionPuzzle.getOptions();
        this.pid = singleTopicSelectionPuzzle.getPid();
    }
}
