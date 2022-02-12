package com.example.poetryserver.service;

import com.example.poetryserver.domain.Poetry;

public interface PoetryService {
    Poetry findPoetryByTitle(String title);
}
