package com.xkj.poetryserver.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Document(collection="sentence")
public class Sentence implements Serializable {
    @Id
    private String id;
    private String name;
    private String from;

    @Override
    public String toString() {
        return "Sentence{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
