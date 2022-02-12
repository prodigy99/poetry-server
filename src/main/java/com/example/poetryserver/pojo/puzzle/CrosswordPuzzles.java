package com.example.poetryserver.pojo.puzzle;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrosswordPuzzles {
    private char[] vue;
    private String content;
    private int num;
    private int len;
    private String writer;
}
