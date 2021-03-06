package com.xkj.poetryserver.controller;

import com.xkj.poetryserver.controller.utils.R;
import com.xkj.poetryserver.service.SentenceService;
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
    public R getRandomSentence() {
        return new R(sentenceService.getOneRandomSentence());
    }
}
