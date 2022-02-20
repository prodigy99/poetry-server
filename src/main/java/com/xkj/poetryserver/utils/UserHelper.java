package com.xkj.poetryserver.utils;


import com.xkj.poetryserver.domain.FakeUserInfo;
import com.xkj.poetryserver.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class UserHelper {
    Random rand = new Random();
    public static double computeNextRankExp(int rank){
        double total = 0;
        for (int i = 1; i <= rank; i++) {
            total += rank * 50;
        }
        return total;
    }
    static DbHelper dbHelper = SpringHelper.getBean(DbHelper.class);

    public static User getFakeUser(String uid){
        FakeUserInfo fakeUserInfo = dbHelper.getOneRandomResult(FakeUserInfo.class);
        fakeUserInfo.setUsername(fakeUserInfo.getUsername().replaceAll("\n",""));
        return new User(uid,fakeUserInfo.getUsername(),fakeUserInfo.getAvatar(),2,12,0,13,0.2);
    }


}
