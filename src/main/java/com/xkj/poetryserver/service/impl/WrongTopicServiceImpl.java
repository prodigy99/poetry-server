package com.xkj.poetryserver.service.impl;

import com.xkj.poetryserver.dao.WrongTopicDao;
import com.xkj.poetryserver.domain.WrongTopic;
import com.xkj.poetryserver.service.WrongTopicService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class WrongTopicServiceImpl implements WrongTopicService {
    final WrongTopicDao wrongTopicDao;

    public WrongTopicServiceImpl(WrongTopicDao wrongTopicDao) {
        this.wrongTopicDao = wrongTopicDao;
    }

    @Override
    public void addWrongTopic(WrongTopic wrongTopic) {
        wrongTopicDao.save(wrongTopic);
    }

    @Override
    public void removeWrongTopic(BigInteger id) {
        wrongTopicDao.delete(new Query(Criteria.where("id").is(id)));
    }

    @Override
    public void setWeight(BigInteger id, int weight) {
        WrongTopic wrongTopic = wrongTopicDao.findById(id);
        wrongTopic.setWeight(weight);
    }

    @Override
    public List<WrongTopic> getUserAllWrongTopic(BigInteger uid) {
        return wrongTopicDao.findAll(new Query(Criteria.where("uid").is(uid)));
    }
}
