package com.example.poetryserver.controller;

import com.example.poetryserver.controller.utils.R;
import com.example.poetryserver.domain.Poetry;
import com.example.poetryserver.service.PoetryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poetry")
public class PoetryController {
    final PoetryService poetryService;

    public PoetryController(PoetryService poetryService) {
        this.poetryService = poetryService;
    }

    @GetMapping("/findPoetryByTitle/{title}")
    public R findPoetryByTitle(@PathVariable("title") String title){
        return new R(poetryService.findPoetryByTitle(title));
    }
}
