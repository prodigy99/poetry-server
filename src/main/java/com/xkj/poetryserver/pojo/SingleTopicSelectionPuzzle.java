package com.xkj.poetryserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SingleTopicSelectionPuzzle implements Serializable {
    private String title;
    private String[] options;
    private String key;
    private String pid;
}
