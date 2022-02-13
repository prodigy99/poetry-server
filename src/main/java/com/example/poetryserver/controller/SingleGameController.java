package com.example.poetryserver.controller;

import com.example.poetryserver.controller.utils.R;
import com.example.poetryserver.dto.SingleTopicSelectionPuzzleDTO;
import com.example.poetryserver.pojo.SingleTopicSelectionPuzzle;
import com.example.poetryserver.service.SingleGameService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Supplier;

//单人游戏
@RestController
@RequestMapping("/singleGame")
public class SingleGameController {
    final SingleGameService singleGameService;

    public SingleGameController(SingleGameService singleGameService) {
        this.singleGameService = singleGameService;
    }

    @GetMapping("/getPuzzle")
    public R getPuzzle(){ // 得到题目
        return new R(new SingleTopicSelectionPuzzleDTO(singleGameService.getPuzzle()));
    }

    @PostMapping("/submitAnswer")
    public R answer(@RequestHeader("uid") String uid,@RequestBody Map<String,Object> answerInfo){
        String pid = (String) answerInfo.get("pid");
        String key = (String) answerInfo.get("key");
        Map<String,String> res = new HashMap<>();
        res.put("isRight",String.valueOf(singleGameService.answer(uid,pid,key)));
        return new R(res);
    }
}
