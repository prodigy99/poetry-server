package com.example.poetryserver.service.impl;

import com.example.poetryserver.domain.User;
import com.example.poetryserver.model.Game;
import com.example.poetryserver.service.PuzzleService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MultiplayerGameServiceImpl {
    final PuzzleService puzzleService;

    public MultiplayerGameServiceImpl(PuzzleService puzzleService, RedisTemplate redisTemplate) {
        this.puzzleService = puzzleService;
    }

    Map<String, Game> gameMap = new HashMap();

    public void setGame(String key, Game game) {
        gameMap.put(key, game);
    }

    public Game getGame(String uid){
        return gameMap.get(uid);
    }
    public void removeGame(String uid){
        gameMap.remove(uid);
    }
    public  Game generateGame(User user) {
        Game game = new Game(user);
        setGame(user.getUid(), game);
        return game;
    }

    public  Game generateGame(List<User> users) {
        Game game = new Game(users);
        users.forEach(user -> setGame(user.getUid(),game));
        return game;
    }
}
