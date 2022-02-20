package com.xkj.poetryserver.controller;

import com.xkj.poetryserver.controller.utils.R;
import com.xkj.poetryserver.service.WriterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("writer")
public class WriterController {
    final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @GetMapping("/{name}")
    public R findWriterByName(@PathVariable("name") String name){
        return new R(writerService.findWriterByName(name));
    }
}
