package com.example.poetryserver.utils;


import com.example.poetryserver.domain.User;
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

    // todo
    public static User getFakeUser(String uid){
        return new User(uid,"mk","http://q1.qlogo.cn/g?b=qq&nk=422459763&s=100",2,12,0,13,0.2);
    }


}
