package com.example.poetryserver.service.impl;

import com.example.poetryserver.dao.PoetryDao;
import com.example.poetryserver.domain.Poetry;
import com.example.poetryserver.dto.SentenceDTO;
import com.example.poetryserver.pojo.SingleTopicSelectionPuzzle;
import com.example.poetryserver.utils.DbHelper;
import com.example.poetryserver.domain.Sentence;
import com.example.poetryserver.domain.Writer;
import com.example.poetryserver.dto.CrosswordPuzzleDTO;
import com.example.poetryserver.service.PuzzleService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class PuzzleServiceImpl implements PuzzleService {
    final DbHelper dbHelper;
    final PoetryDao poetryDao;
    final RedisTemplate redisTemplate;

    public PuzzleServiceImpl(DbHelper dbHelper, PoetryDao poetryDao, RedisTemplate redisTemplate) {
        this.dbHelper = dbHelper;
        this.poetryDao = poetryDao;
        this.redisTemplate = redisTemplate;
    }

    // 得到随机的pid
    private String getPid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    // 将puzzle与id存入redis
    private void savePuzzle(String pid, SingleTopicSelectionPuzzle singleTopicSelectionPuzzle) {
        // 24小时后过期
        redisTemplate.opsForValue().set(pid, singleTopicSelectionPuzzle,24, TimeUnit.HOURS);
    }

    @Override
    public void removePuzzle(String pid){
        redisTemplate.delete(pid);
    }

    // 根据pid获得puzzle
    private SingleTopicSelectionPuzzle getPuzzle(String pid, boolean remove) {
        if(redisTemplate.hasKey(pid)){
            SingleTopicSelectionPuzzle singleTopicSelectionPuzzle = (SingleTopicSelectionPuzzle) redisTemplate.opsForValue().get(pid);
            if (remove == true)  removePuzzle(pid);
            return singleTopicSelectionPuzzle;
        }else return null;
    }

    @Override
    public boolean checkKey(String pid, String key, boolean remove){
        SingleTopicSelectionPuzzle singleTopicSelectionPuzzle = getPuzzle(pid,remove);
        if(singleTopicSelectionPuzzle == null) return false; // 没找到题目,直接判负
        return singleTopicSelectionPuzzle.getKey().equals(key);
    }

    @Override
    public boolean checkKey(String pid, String key){
        return this.checkKey(pid,key,true);
    }

    // 得到作者-诗词名类型的题目
    @Override
    public SingleTopicSelectionPuzzle getTitleWriterPuzzle() {
        Random rand = new Random();
        w:
        while (true) {
            SentenceDTO sentenceDTO = new SentenceDTO(dbHelper.getOneRandomResult(Sentence.class));
            Poetry poetry = poetryDao.findByTitleAndWriter(sentenceDTO.getTitle(), sentenceDTO.getWriter());
            if (poetry == null) continue;

            List<Writer> writers = dbHelper.getRandomResults(Writer.class, 4); // 备选作者
            String[] writerArr = new String[4];

            if (sentenceDTO.getWriter().equals("佚名")) continue;
            for (int i = 0; i < 4; i++) {
                if (writers.get(i).getName().equals(sentenceDTO.getWriter())) continue w; // 作者名重复，重新生成
                if (writers.get(i).getName().equals("佚名")) continue w; // 作者名模糊，重新生成
                writerArr[i] = writers.get(i).getName();
            }
            int key = rand.nextInt(4);
            writerArr[key] = sentenceDTO.getWriter();

            String pid = getPid();
            SingleTopicSelectionPuzzle singleTopicSelectionPuzzle = new SingleTopicSelectionPuzzle("《" + sentenceDTO.getTitle() + "》的作者是谁?", writerArr, key+"", pid);
            savePuzzle(pid, singleTopicSelectionPuzzle);
            return singleTopicSelectionPuzzle;
        }

    }

    //    得到名句-诗词名类型的题目
    @Override
    public SingleTopicSelectionPuzzle getTitleSentenceQuestion() {
        Random rand = new Random();
        List<SentenceDTO> sentences = new ArrayList<>();
        dbHelper.getRandomResults(Sentence.class, 4).forEach(sentence -> sentences.add(new SentenceDTO(sentence)));

        String[] sentencesArr = new String[4];
        String[] titlesArr = new String[4];

        for (int i = 0; i < 4; i++) {
            sentencesArr[i] = sentences.get(i).getContent();
            titlesArr[i] = sentences.get(i).getTitle();
        }

        w:
        while (true) {
            SentenceDTO sentenceDTO = new SentenceDTO(dbHelper.getOneRandomResult(Sentence.class));
            Poetry poetry = poetryDao.findByTitleAndWriter(sentenceDTO.getTitle(), sentenceDTO.getWriter());
            if (poetry == null) continue;
            for (String str : sentencesArr) {
                if (str.equals(sentenceDTO.getContent())) continue w; // 重复了，重新生成
            }

            int key = rand.nextInt(4);
            titlesArr[key] = sentenceDTO.getTitle();

            String pid = getPid();
            SingleTopicSelectionPuzzle singleTopicSelectionPuzzle = new SingleTopicSelectionPuzzle("名句 \"" + sentenceDTO.getContent() + "\" 出自以下哪一首诗/词?", titlesArr, key+"", pid);
            savePuzzle(pid, singleTopicSelectionPuzzle);
            return singleTopicSelectionPuzzle;
        }
    }

    @Override
    public SingleTopicSelectionPuzzle getSingleTopicSelectionPuzzle() { // 获取题目
        Random rand = new Random();
        ArrayList<Supplier<SingleTopicSelectionPuzzle>> suppliers = new ArrayList<>(Arrays.asList(this::getTitleWriterPuzzle, this::getTitleSentenceQuestion));
        int index = rand.nextInt(suppliers.size());
        return suppliers.get(index).get();
    }

    //获得填词类型的游戏
    @Deprecated
    public CrosswordPuzzleDTO getCrosswordPuzzles(int num) {
        Random rand = new Random();

        while (true) {
            SentenceDTO sentenceDTO = new SentenceDTO(dbHelper.getOneRandomResult(Sentence.class));
            int index;
            String sentenceStr = sentenceDTO.getContent();

            if (sentenceDTO.getWriter().equals("佚名")) continue;
            if (sentenceStr.contains("，")) {
                index = sentenceStr.indexOf("，");
            } else {
                index = sentenceStr.indexOf("？");
            }

            // 获取正确字符
            String content = sentenceStr.substring(0, index);
            char[] values = new char[num];
            for (int i = 0; i < index; i++) {
                values[i] = content.charAt(i);
            }

            // 获取其他字符
            List<Sentence> otherSentences = dbHelper.getRandomResults(Sentence.class, num - content.length());
            for (int i = index; i < num; i++) {
                values[i] = otherSentences.get(i - index).getName().charAt(0);
            }

            // 打乱数组顺序
            for (int i = 0; i < num; i++) {
                int t = rand.nextInt(num - i);
                char temp = values[t];
                values[t] = values[num - t - 1];
                values[num - t - 1] = temp;
            }

            return new CrosswordPuzzleDTO(values, content, num, content.length(), sentenceDTO.getWriter());
        }
    }
}
