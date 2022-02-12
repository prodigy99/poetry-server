package com.example.poetryserver.service.impl;

import com.example.poetryserver.dao.WriterDao;
import com.example.poetryserver.domain.Writer;
import com.example.poetryserver.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterServiceImpl implements WriterService {
    final WriterDao writerDao;

    public WriterServiceImpl(WriterDao writerDao) {
        this.writerDao = writerDao;
    }

    @Override
    public Writer findWriterByName(String name) {
        return writerDao.findByName(name);
    }
}
