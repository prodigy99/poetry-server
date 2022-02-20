package com.example.poetryserver.service.impl;

import com.example.poetryserver.pojo.SingleTopicSelectionPuzzle;
import com.example.poetryserver.service.PuzzleService;
import com.example.poetryserver.service.SingleGameService;
import com.example.poetryserver.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// 单人游戏
@Service
public class SingleGameServiceImpl implements SingleGameService {
    final PuzzleService puzzleService;
    final UserService userService;
    @Value("${app.config.baseExp}")
    int baseExp;

    public SingleGameServiceImpl(PuzzleService puzzleService, UserService userService) {
        this.puzzleService = puzzleService;
        this.userService = userService;
    }

    @Override
    public SingleTopicSelectionPuzzle getPuzzle() { // 获取题目
        return puzzleService.getSingleTopicSelectionPuzzle();
    }

    @Override
    public boolean answer(String uid, String pid, String key) {
        Boolean isRight = puzzleService.checkKey(pid, key);
        if (puzzleService.checkKey(pid, key)) {
            // 回答正确,增加经验
            userService.addUserExp(uid,baseExp);
        }

        return isRight;
    }
}
