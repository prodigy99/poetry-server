package com.xkj.poetryserver.service.impl;

import com.xkj.poetryserver.dao.WriterDao;
import com.xkj.poetryserver.domain.Writer;
import com.xkj.poetryserver.service.WriterService;
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
