package com.example.poetryserver.dao;

import com.example.poetryserver.domain.Writer;

public interface WriterDao {
    Writer findByName(String writerName);
}
