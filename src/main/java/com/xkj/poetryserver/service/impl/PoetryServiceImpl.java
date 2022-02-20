package com.xkj.poetryserver.service.impl;

import com.xkj.poetryserver.dao.PoetryDao;
import com.xkj.poetryserver.domain.Poetry;
import com.xkj.poetryserver.service.PoetryService;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoetryServiceImpl implements PoetryService {

    final PoetryDao poetryDao;
    final MongoOperations mongoOperations;

    public PoetryServiceImpl(PoetryDao poetryDao, MongoOperations mongoOperations) {
        this.poetryDao = poetryDao;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Poetry findPoetryByTitle(String title) {
        return poetryDao.findByTitle(title);
    }

    @Override
    public List<Poetry> pagination(int index, int size) {
        return poetryDao.pagination(index,size);
    }

    @Override
    public long count() {
        return poetryDao.count();
    }

    @Override
    public Poetry findById(String id){
        return poetryDao.findById(id);
    }
}
