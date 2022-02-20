package com.xkj.poetryserver.dao;

import com.xkj.poetryserver.domain.Writer;

public interface WriterDao {
    Writer findByName(String writerName);
}
