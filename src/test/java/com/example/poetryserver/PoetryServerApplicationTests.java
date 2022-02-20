package com.example.poetryserver;

import com.alibaba.fastjson.JSONObject;
import com.example.poetryserver.controller.utils.R;
import com.example.poetryserver.dao.PoetryDao;
import com.example.poetryserver.domain.Poetry;
import com.example.poetryserver.domain.User;
import com.example.poetryserver.domain.Writer;
import com.example.poetryserver.pojo.SingleTopicSelectionPuzzle;
import com.example.poetryserver.service.*;
import com.example.poetryserver.service.impl.MultiplayerGameServiceImpl;
import com.example.poetryserver.utils.MatchHelper;
import com.example.poetryserver.utils.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.function.Supplier;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    public void findWriterByName() {
        Writer writer = writerService.findWriterByName("李白");
    }

    @Test
    public void findPoetryByTitle() {
        Poetry poetry = mongoTemplate.findOne(new Query(Criteria.where("title").is("将进酒")), Poetry.class);
        System.out.println(poetry);
    }

    @Test
    public void getRandomSentence() {
        System.out.println(sentenceService.getOneRandomSentence());
    }

    @Test
    public void PuzzleService() {
        System.out.println(puzzleService.getTitleWriterPuzzle());
    }

    @Test
    public void getTitleSentenceQuestion() {
        System.out.println(puzzleService.getTitleSentenceQuestion());
    }

    @Test
    public void getCrosswordPuzzles() {
        System.out.println(puzzleService.getCrosswordPuzzles(12));
    }

    @Test
    public void findUser() {
        User user = userService.findUserById(null);
        System.out.println(user);
    }

    @Test
    public void websocketTest() {

    }

    @Test
    public void redis() {
        Random rand = new Random();
        ArrayList<Supplier<SingleTopicSelectionPuzzle>> suppliers = new ArrayList<>(Arrays.asList(puzzleService::getTitleWriterPuzzle, puzzleService::getTitleSentenceQuestion));
        int index = rand.nextInt(suppliers.size());
        SingleTopicSelectionPuzzle singleTopicSelectionPuzzle = suppliers.get(index).get();
        System.out.println(singleTopicSelectionPuzzle);
        System.out.println(puzzleService.checkKey(singleTopicSelectionPuzzle.getPid(), singleTopicSelectionPuzzle.getKey()));
    }

    @Autowired
    MultiplayerGameServiceImpl multiplayerGameService;

    @Test
    public void matchSucess() throws InterruptedException {
        MatchHelper.putPlayerIntoMatchPool("1", 2);
//        MatchHelper.putPlayerIntoMatchPool("2",3);
        MatchHelper.matchSuccess((uids) -> {
            System.out.println(uids);
        });

        MatchHelper.matchTimeout((uid) -> {
            System.out.println(uid);
        });
        Thread.sleep(100000000);
    }

    @Test
    public void json() {
        System.out.println(JSONObject.toJSONString(new R(123)));
    }

    @Test
    public void getFakeUser() {
        UserHelper.getFakeUser("-1");
    }

    @Autowired
    PoetryDao poetryDao;

    @Autowired
    PoetryService poetryService;
    @Test
    public void count() {
        log.info("{}", poetryDao.count());
    }

    @Test
    public void pagination() {
        Map<String,Object> data = new HashMap<>();
        data.put("count",poetryService.count());
        data.put("poetry",poetryService.pagination(0,5));
        log.info("{}", data);
    }

    @Test
    public void find() {
        log.info("{}",poetryDao.findById("5b9a0136367d5c96f4cd2952"));

    }

}
