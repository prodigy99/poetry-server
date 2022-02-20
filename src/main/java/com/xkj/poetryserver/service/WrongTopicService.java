package com.xkj.poetryserver.service;

import com.xkj.poetryserver.domain.WrongTopic;

import java.math.BigInteger;
import java.util.List;

public interface WrongTopicService {
    void addWrongTopic(WrongTopic wrongTopic);
    void removeWrongTopic(BigInteger id);
    void setWeight(BigInteger id,int weight);
    List<WrongTopic> getUserAllWrongTopic(BigInteger uid);
}
