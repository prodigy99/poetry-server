package com.xkj.poetryserver.controller;

import com.xkj.poetryserver.controller.utils.R;
import com.xkj.poetryserver.pojo.SingleTopicSelectionPuzzle;
import com.xkj.poetryserver.service.PuzzleService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Supplier;

@RestController
@RequestMapping("/puzzle")
public class PuzzleController {
    final PuzzleService puzzleService;

    public PuzzleController(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @GetMapping("/getSingleTopicSelectionPuzzle")
    public R getSingleTopicSelectionPuzzle(){ // 单选题
        Random rand = new Random();
        ArrayList<Supplier<SingleTopicSelectionPuzzle>> suppliers =  new ArrayList<>(Arrays.asList(puzzleService::getTitleWriterPuzzle, puzzleService::getTitleSentenceQuestion));
        int index = rand.nextInt(suppliers.size());
        return new R(suppliers.get(index).get());
    }

    @PostMapping("/submitAnswer")
    public R submitAnswer(@RequestBody Map<String,Object> answerInfo){
        String uid = (String) answerInfo.get("uid");
        String pid = (String) answerInfo.get("pid");
        String key = (String) answerInfo.get("key");
        Map<String,String> res = new HashMap<>();
        res.put("isRight",String.valueOf(puzzleService.checkKey(pid,key)));

        return new R(res);
    }

    @GetMapping("/getCrosswordPuzzles/{num}")
    @Deprecated
    public R getCrosswordPuzzles(@PathVariable("num") int num){ // 填词题,暂时不用
        return new R(puzzleService.getCrosswordPuzzles(num));
    }
}
