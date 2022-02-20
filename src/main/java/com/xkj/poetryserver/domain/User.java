package com.xkj.poetryserver.domain;

import com.xkj.poetryserver.utils.UserHelper;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String uid;
    private String nickname;//用户名
    private String avatar;
    private int level;
    private int gold;
    private int diamond;
    private int exp;
    private double nextRankPercent; // 经验距离升级的百分比
//    private String rank; // 段位

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getExp() {
        return exp;
    }

    public double getNextRankPercent() {
        return nextRankPercent;
    }

    public void setNextRankPercent(double nextRankPercent) {
        this.nextRankPercent = nextRankPercent;
    }

    public void setExp(int exp) {
        this.exp = exp;
        double nextRankThreshold = UserHelper.computeNextRankExp(this.level);
        double baseRankThreshold = UserHelper.computeNextRankExp(this.level -1);

        if(exp > nextRankThreshold){
            this.setLevel(this.level + 1);
            this.setExp(this.exp); // 再计算一次，看是否还能升级
        }else{
            this.setNextRankPercent((this.exp - baseRankThreshold) / (nextRankThreshold - baseRankThreshold));
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickName) {
        this.nickname = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
