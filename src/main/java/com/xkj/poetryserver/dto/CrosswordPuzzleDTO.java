package com.xkj.poetryserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrosswordPuzzleDTO {
    private char[] vue;
    private String content;
    private int num;
    private int len;
    private String writer;
    private final int mode = 1;
}
