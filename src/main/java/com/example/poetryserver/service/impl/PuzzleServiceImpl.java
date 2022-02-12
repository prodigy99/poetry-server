package com.example.poetryserver.service.impl;

import com.example.poetryserver.util.DbHelper;
import com.example.poetryserver.domain.Sentence;
import com.example.poetryserver.domain.Writer;
import com.example.poetryserver.pojo.puzzle.CrosswordPuzzles;
import com.example.poetryserver.pojo.puzzle.SingleTopicSelectionPuzzle;
import com.example.poetryserver.service.PuzzleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PuzzleServiceImpl implements PuzzleService {
    final DbHelper dbHelper;

    public PuzzleServiceImpl(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

//    得到作者-诗词名类型的题目
    @Override
    public SingleTopicSelectionPuzzle getTitleWriterPuzzle() {
        Random rand = new Random();
        w:while(true){
            List<Writer> writers = dbHelper.getRandomResults(Writer.class,4); // 备选作者
            Sentence sentence = dbHelper.getOneRandomResult(Sentence.class);
            String[] writersStr = new String[4];
            String realWriter = sentence.getFrom().substring(0,sentence.getFrom().indexOf("《"));
            String title = sentence.getFrom().substring(sentence.getFrom().indexOf("《"),sentence.getFrom().indexOf("》")+1);
            if(realWriter.equals("佚名")) continue;
            for(int i=0; i<4;i++){
                if(writers.get(i).getName().equals(realWriter)) continue w; // 作者名重复，重新生成
                if(writers.get(i).getName().equals("佚名")) continue w; // 作者名模糊，重新生成
                writersStr[i] = writers.get(i).getName();
            }
            int key = rand.nextInt(4);
            writersStr[key] = realWriter;

            return new SingleTopicSelectionPuzzle(title+"的作者是谁?",writersStr,key);
        }

    }

//    得到名句-诗词名类型的题目
    @Override
    public SingleTopicSelectionPuzzle getTitleSentenceQuestion() {
        Random rand = new Random();
        List<Sentence> sentences = dbHelper.getRandomResults(Sentence.class,4);
        String[] sentencesArr = new String[4];
        String[] titlesArr = new String[4];

        for(int i=0; i<4; i++){
            sentencesArr[i] = sentences.get(i).getName();
            titlesArr[i]  = sentences.get(i).getFrom().substring(sentences.get(i).getFrom().indexOf("《"),sentences.get(i).getFrom().indexOf("》")+1);
        }
        w:while(true){
            Sentence sentence =  dbHelper.getOneRandomResult(Sentence.class);
            for (String str : sentencesArr) {
                if(str.equals(sentence.getName())) continue w; // 重复了，重新生成
            }
            String title = sentence.getFrom().substring(sentence.getFrom().indexOf("《"),sentence.getFrom().indexOf("》")+1);
            int key = rand.nextInt(4);
            titlesArr[key] = title;
            return new SingleTopicSelectionPuzzle("名句 \""+sentence.getName()+"\" 出自以下哪一首诗/词?",titlesArr,key);
        }
    }


    public CrosswordPuzzles getCrosswordPuzzles(int num){
        Random rand = new Random();

        while (true){
            Sentence sentence = dbHelper.getOneRandomResult(Sentence.class);
            int index;
            String sentenceStr =  sentence.getName();

            if(!sentence.getFrom().contains("佚名")) continue;
            if(sentenceStr.contains("，")){
                index = sentenceStr.indexOf("，");
            }else{
                index = sentenceStr.indexOf("？");
            }

            // 获取正确字符
            String content = sentenceStr.substring(0,index);
            char[] values = new char[num];
            for (int i = 0; i < index; i++) {
                values[i] = content.charAt(i);
            }

            // 获取其他字符
            List<Sentence> otherSentences = dbHelper.getRandomResults(Sentence.class,num - content.length());
            for (int i = index; i < num; i++) {
                values[i] = otherSentences.get(i-index).getName().charAt(0);
            }

            // 打乱数组顺序
            for (int i = 0; i < num; i++) {
                int t = rand.nextInt(num-i);
                char temp = values[t];
                values[t] = values[num-t-1];
                values[num-t-1]=temp;
            }
            String writer = sentence.getFrom().substring(0,sentence.getFrom().indexOf("《"));

            return new CrosswordPuzzles(values,content,num,content.length(),writer);


        }
    }
}
