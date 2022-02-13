package com.example.poetryserver.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@Document(collection = "guwen")
public class Poetry implements Serializable {
    @Id
    private String id;
    private String title;
    private String dynasty;
    private String writer;
    private String content;
    private List<String> type;
    private String remark;
    @Field("shangxi")
    private String appreciation;
    private String translation;
    private String audioUrl;

    @Override
    public String toString() {
        return "Poetry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dynasty='" + dynasty + '\'' +
                ", writer='" + writer + '\'' +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                ", appreciation='" + appreciation + '\'' +
                ", translation='" + translation + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                '}';
    }
}
