package com.xkj.poetryserver.dao;

import com.xkj.poetryserver.domain.Poetry;

import java.util.List;


public interface PoetryDao {
    Poetry findByTitle(String title);
    Poetry findByTitleAndWriter(String title,String writer);

    List<Poetry> pagination(int index, int size);

    Poetry findById(String id);

    long count();
}
