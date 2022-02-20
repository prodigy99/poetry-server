package com.xkj.poetryserver.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Data
@Document(collection = "wrongTopic")
public class WrongTopic {
    @Id
    private BigInteger id;
    private BigInteger uid;
    private BigInteger pid;
    private int weight;
}
