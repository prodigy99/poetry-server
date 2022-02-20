package com.xkj.poetryserver.controller;

import com.xkj.poetryserver.controller.utils.R;
import com.xkj.poetryserver.dto.SingleTopicSelectionPuzzleDTO;
import com.xkj.poetryserver.service.SingleGameService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
