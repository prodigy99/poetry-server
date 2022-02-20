package com.example.poetryserver.model;

import com.example.poetryserver.controller.utils.Message;
import com.example.poetryserver.domain.User;
import com.example.poetryserver.service.impl.WebSocketServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
@Slf4j
public class Player {
    private User user;
    private boolean isRobot;
    private enum STATUS{
        ready
    };
    private STATUS status;

    public Player(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void ready(){
        this.status = STATUS.ready;
    }

    public boolean isReady(){
        return this.status == STATUS.ready;
    }

//    发送消息
    public void sendMessage(Message message) {
        Session session = WebSocketServiceImpl.getSession(this.getUser().getUid());
        if(session != null) {
            try {
                session.getBasicRemote().sendObject(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}