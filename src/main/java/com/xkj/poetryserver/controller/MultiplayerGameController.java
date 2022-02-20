package com.xkj.poetryserver.controller;

import com.xkj.poetryserver.config.MessageDecoder;
import com.xkj.poetryserver.config.MessageEncoder;
import com.xkj.poetryserver.controller.utils.Message;
import com.xkj.poetryserver.domain.User;
import com.xkj.poetryserver.model.Game;
import com.xkj.poetryserver.service.UserService;
import com.xkj.poetryserver.service.impl.MultiplayerGameServiceImpl;
import com.xkj.poetryserver.utils.MatchHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/multiplayerGames", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
@Component
public class MultiplayerGameController {

    private static MultiplayerGameServiceImpl multiplayerGameService;
    private static UserService userService;

    @Autowired
    public void setMultiplayerGameService(MultiplayerGameServiceImpl multiplayerGameService) {
        this.multiplayerGameService = multiplayerGameService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    static Map<String, Session> sessionMap = new HashMap();

    /**
     * 记录当前在线连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    public static void setSession(String key, Session session) {
        sessionMap.put(key, session);
    }

    public static Session getSession(String key) {
        return sessionMap.get(key);
    }

    static {
        MatchHelper.matchSuccess((uids) -> {// 匹配成功
            List<User> users = new ArrayList<>();
            uids.forEach((uid) -> {
                users.add(userService.findUserById(uid));
            });
            multiplayerGameService.generateGame(users);
        });

        MatchHelper.matchTimeout((uid) -> {// 匹配失败
            multiplayerGameService.generateGame(userService.findUserById(uid));
        });
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineCount.incrementAndGet(); // 在线数加1
        log.info("有新连接加入:{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    @OnMessage
    public void onMessage(Message message, Session session) throws EncodeException, IOException {
        Map<String, String> data = (Map<String, String>) message.getData();
        String uid = data.get("uid");

        switch (message.getEventType()) {
            //  注册
            case register:
                setSession(uid, session);
                break;
            case startMatch: // 开始匹配
                setSession(uid, session);
                Game game = multiplayerGameService.getGame(uid);
                if (game != null) {
                    // 断线重连
                    game.notifyMatchSuccess();
                } else {
                    User user = userService.findUserById(uid);
                    MatchHelper.putPlayerIntoMatchPool(uid, user.getLevel());
                }
                break;
            case ready:
                multiplayerGameService.getGame(uid).ready(uid);
                break;
            case answer:
                String pid = data.get("pid");
                String key = data.get("key");
                multiplayerGameService.getGame(uid).answer(uid, pid, key);
                break;
            default:
                return;
        }
    }


    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet();
        log.info("有连接退出:{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }
}
