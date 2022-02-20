package com.example.poetryserver.controller;

import com.example.poetryserver.controller.utils.R;
import com.example.poetryserver.service.PoetryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/pagination/{index}/{size}")
    public R pagination(@PathVariable("index") int index,@PathVariable("size") int size){
        Map<String,Object> data = new HashMap<>();
        data.put("count",poetryService.count());
        data.put("poetry",poetryService.pagination(index,size));
        return new R(data);
    }

    @GetMapping("/findById/{id}")
    public R findById(@PathVariable("id") String id){
        return new R(poetryService.findById(id));
    }
}
