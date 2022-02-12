package com.example.poetryserver.service;

import com.example.poetryserver.domain.Writer;

public interface WriterService {
    Writer findWriterByName(String Name);
}
