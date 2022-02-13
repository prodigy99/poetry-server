package com.example.poetryserver.dao;

import com.example.poetryserver.domain.Poetry;
import org.springframework.data.mongodb.core.query.Query;


public interface PoetryDao {
    Poetry findByTitle(String title);
    Poetry findByTitleAndWriter(String title,String writer);

}
