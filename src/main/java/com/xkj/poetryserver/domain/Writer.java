package com.xkj.poetryserver.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Document(collection="writer")
public class Writer implements Serializable {
    @Id
    private BigInteger id;
    private String name;
    private String headImageUrl;
    private String simpleIntro;
    private String detailIntro;

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", headImageUrl='" + headImageUrl + '\'' +
                ", simpleIntro='" + simpleIntro + '\'' +
                ", detailIntro='" + detailIntro + '\'' +
                '}';
    }
}
