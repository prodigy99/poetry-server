package com.example.poetryserver;

import com.example.poetryserver.controller.utils.R;
import com.example.poetryserver.domain.Poetry;
import com.example.poetryserver.domain.User;
import com.example.poetryserver.domain.Writer;
import com.example.poetryserver.pojo.SingleTopicSelectionPuzzle;
import com.example.poetryserver.service.PuzzleService;
import com.example.poetryserver.service.SentenceService;
import com.example.poetryserver.service.UserService;
import com.example.poetryserver.service.WriterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

@SpringBootTest
class PoetryServerApplicationTests {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    WriterService writerService;

    @Autowired
    SentenceService sentenceService;

    @Autowired
    PuzzleService puzzleService;

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void findWriterByName(){
        Writer writer = writerService.findWriterByName("李白");
    }

    @Test
    public void findPoetryByTitle(){
        Poetry poetry = mongoTemplate.findOne(new Query(Criteria.where("title").is("将进酒")), Poetry.class);
        System.out.println(poetry);
    }

    @Test
    public void getRandomSentence(){
        System.out.println(sentenceService.getOneRandomSentence());
    }

    @Test
    public void PuzzleService(){
        System.out.println( puzzleService.getTitleWriterPuzzle());
    }

    @Test
    public void getTitleSentenceQuestion(){
        System.out.println( puzzleService.getTitleSentenceQuestion());
    }

    @Test
    public void getCrosswordPuzzles(){
        System.out.println(puzzleService.getCrosswordPuzzles(12));
    }

    @Test
    public void findUser(){
        User user = userService.findUserById("123");
        System.out.println(user);
    }

    @Test
    public void redis(){
        Random rand = new Random();
        ArrayList<Supplier<SingleTopicSelectionPuzzle>> suppliers =  new ArrayList<>(Arrays.asList(puzzleService::getTitleWriterPuzzle, puzzleService::getTitleSentenceQuestion));
        int index = rand.nextInt(suppliers.size());
        SingleTopicSelectionPuzzle singleTopicSelectionPuzzle = suppliers.get(index).get();
        System.out.println(singleTopicSelectionPuzzle);
        System.out.println(puzzleService.checkKey(singleTopicSelectionPuzzle.getPid(), singleTopicSelectionPuzzle.getKey()));
    }

}
