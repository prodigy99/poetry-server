package com.xkj.poetryserver.model;

import com.xkj.poetryserver.controller.MultiplayerGameController;
import com.xkj.poetryserver.controller.utils.Message;
import com.xkj.poetryserver.domain.User;
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
    public void unReady(){
        this.status = STATUS.ready;
    }


    public boolean isReady(){
        return this.status == STATUS.ready;
    }

//    发送消息
    public void sendMessage(Message message) {
        Session session = MultiplayerGameController.getSession(this.getUser().getUid());
        if(session != null) {
            try {
                session.getBasicRemote().sendObject(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}