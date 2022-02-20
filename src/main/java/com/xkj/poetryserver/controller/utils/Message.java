package com.xkj.poetryserver.controller.utils;

import lombok.Data;

@Data
public class Message {
    public enum EVENT_TYPE{
        register, //
        startMatch, // 开始匹配
        cancelMatch,// 取消匹配
        matchSuccess, // 匹配成功Ï
        updateTime, // 时钟同步
        ready, // 是否准备好了
        newPuzzle, // 新的题目
        answer, // 回答题目
        meScore, // 自己的回答结果
        otherScore, // 对手的回答结果
        showScore, // 展现分数
        exitGame, // 中途退出
        finishGame
    }

    private EVENT_TYPE eventType;
    private Object data;

    public Message(EVENT_TYPE eventType, Object data) {
        this.eventType = eventType;
        this.data = data;
    }

    public Message(EVENT_TYPE event_type) {
        this.eventType = event_type;
    }
}
