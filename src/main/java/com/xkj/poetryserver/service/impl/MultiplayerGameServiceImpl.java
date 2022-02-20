package com.xkj.poetryserver.service.impl;

import com.xkj.poetryserver.domain.User;
import com.xkj.poetryserver.model.Game;
import com.xkj.poetryserver.service.MultiplayerGameService;
import com.xkj.poetryserver.service.PuzzleService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MultiplayerGameServiceImpl implements MultiplayerGameService {
    final PuzzleService puzzleService;

    public MultiplayerGameServiceImpl(PuzzleService puzzleService, RedisTemplate redisTemplate) {
        this.puzzleService = puzzleService;
    }

    Map<String, Game> gameMap = new HashMap();

    @Override
    public void setGame(String key, Game game) {
        gameMap.put(key, game);
    }
    @Override
    public Game getGame(String uid){
        return gameMap.get(uid);
    }

    @Override
    public void removeGame(String uid){
        gameMap.remove(uid);
    }

    @Override
    public  Game generateGame(User user) {
        Game game = new Game(user);
        setGame(user.getUid(), game);
        return game;
    }

    @Override
    public  Game generateGame(List<User> users) {
        Game game = new Game(users);
        users.forEach(user -> setGame(user.getUid(),game));
        return game;
    }
}
