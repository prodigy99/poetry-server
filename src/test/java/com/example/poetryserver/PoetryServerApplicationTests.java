package com.example.poetryserver;

import com.example.poetryserver.dao.SentenceDao;
import com.example.poetryserver.domain.Poetry;
import com.example.poetryserver.domain.Writer;
import com.example.poetryserver.service.PuzzleService;
import com.example.poetryserver.service.SentenceService;
import com.example.poetryserver.service.UserService;
import com.example.poetryserver.service.WriterService;
import com.example.poetryserver.util.WxHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;

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


}
