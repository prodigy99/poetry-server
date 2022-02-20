package com.xkj.poetryserver.service;

import com.xkj.poetryserver.domain.User;
import com.xkj.poetryserver.model.Game;

import java.util.List;

public interface MultiplayerGameService {
    void setGame(String key, Game game);

    Game getGame(String uid);

    void removeGame(String uid);

    Game generateGame(User user);

    Game generateGame(List<User> users);
}
