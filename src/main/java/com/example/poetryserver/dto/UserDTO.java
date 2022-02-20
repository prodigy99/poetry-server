package com.example.poetryserver.dto;

import com.example.poetryserver.domain.User;
import lombok.Data;

@Data
public class UserDTO {
    private String nickname;//用户名
    private String avatar;
    private int level;
    private int gold;
    private int diamond;
    private int exp;
    private double nextRankPercent; // 经验距离升级的百分比

    public UserDTO(User user) {
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.level = user.getLevel();
        this.gold = user.getGold();
        this.diamond = user.getDiamond();
        this.exp = user.getExp();
        this.nextRankPercent = user.getNextRankPercent();
    }
}
