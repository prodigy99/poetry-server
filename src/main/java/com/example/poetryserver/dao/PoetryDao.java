package com.example.poetryserver.dao;

import com.example.poetryserver.domain.Poetry;

public interface PoetryDao {
    Poetry findByTitle(String title);
}
