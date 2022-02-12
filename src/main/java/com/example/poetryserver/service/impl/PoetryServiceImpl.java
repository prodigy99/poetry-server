package com.example.poetryserver.service.impl;

import com.example.poetryserver.dao.PoetryDao;
import com.example.poetryserver.domain.Poetry;
import com.example.poetryserver.service.PoetryService;
import org.springframework.stereotype.Service;

@Service
public class PoetryServiceImpl implements PoetryService {

    final PoetryDao poetryDao;
    public PoetryServiceImpl(PoetryDao poetryDao) {
        this.poetryDao = poetryDao;
    }

    @Override
    public Poetry findPoetryByTitle(String title) {
        return poetryDao.findByTitle(title);
    }
}
