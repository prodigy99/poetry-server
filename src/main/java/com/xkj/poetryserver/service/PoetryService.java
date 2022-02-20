package com.xkj.poetryserver.service;

import com.xkj.poetryserver.domain.Poetry;

import java.util.List;

public interface PoetryService {
    Poetry findPoetryByTitle(String title);

    List<Poetry> pagination(int index, int size);

    long count();

    Poetry findById(String id);
}
