package com.xkj.poetryserver.service;

import com.xkj.poetryserver.domain.Writer;

public interface WriterService {
    Writer findWriterByName(String Name);
}
