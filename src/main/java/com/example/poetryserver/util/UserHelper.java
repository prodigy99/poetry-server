package com.example.poetryserver.util;


public class UserHelper {
    public static double computeNextRankExp(int rank){
        double total = 0;
        for (int i = 1; i <= rank; i++) {
            total += rank * 50;
        }
        return total;
    }
}
