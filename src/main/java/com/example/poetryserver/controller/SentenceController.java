package com.example.poetryserver.controller;

import com.example.poetryserver.domain.Sentence;
import com.example.poetryserver.service.SentenceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sentence")
public class SentenceController {
    final SentenceService sentenceService;
    public SentenceController(SentenceService sentenceService) {
        this.sentenceService = sentenceService;
    }

    @GetMapping("getRandomSentence")
    public Sentence getRandomSentence(){
        return sentenceService.getOneRandomSentence();
    }
}
