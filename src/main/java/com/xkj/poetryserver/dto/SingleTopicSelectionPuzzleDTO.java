package com.xkj.poetryserver.dto;

import com.xkj.poetryserver.pojo.SingleTopicSelectionPuzzle;
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
