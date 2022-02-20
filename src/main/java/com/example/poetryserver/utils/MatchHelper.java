package com.example.poetryserver.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
public class MatchHelper {
    /**
     * 匹配线程
     */
    private static final ScheduledExecutorService sec = Executors.newSingleThreadScheduledExecutor();

    /**
     * 每个人需要匹配到的玩家数量
     */
    private static final int NEED_MATCH_PLAYER_COUNT = 1;

    // 递增的base值,计算公式为:Math.pow(waitSecond, base);
    private static final int base = 2;
    // 匹配系统中的递增阈值
    private static final int stepThreshold = 20;
    // 保底递增值
    private static final int floor = 1;
    // 超时时间,单位为秒
    private static final int timeout = 5;

    private static Consumer<String> matchTimeoutConsumer;
    private static Consumer<List<String>> matchSuccessConsumer;


    /**
     * 匹配池
     */
    private static final ConcurrentHashMap<String, MatchPoolPlayerInfo> playerPool = new ConcurrentHashMap<>();

    static {
        sec.scheduleWithFixedDelay(() -> matchProcess(playerPool), 1, 1, TimeUnit.SECONDS);//每隔1秒匹配一次
    }


    /**
     * 把玩家放入匹配池
     *
     * @param playerId
     * @param rank
     * @return
     */
    public static void putPlayerIntoMatchPool(String playerId, int rank) {
        MatchPoolPlayerInfo playerInfo = new MatchPoolPlayerInfo(playerId, rank);
        playerPool.put(playerId, playerInfo);
    }

    private static void matchSuccessProcess(List<MatchPoolPlayerInfo> matchPoolPlayerInfos) {
        List<String> uids = new ArrayList<>();

        matchPoolPlayerInfos.forEach(matchPoolPlayerInfo -> {
            removePlayerFromMatchPool(matchPoolPlayerInfo.getPlayerId());
            uids.add(matchPoolPlayerInfo.getPlayerId());
        });

        if(matchSuccessConsumer != null){
            matchSuccessConsumer.accept(uids);
        }
    }

    private static void matchTimeoutProcess(MatchPoolPlayerInfo matchPoolPlayerInfo) {
//        log.info(matchPoolPlayerInfo.getPlayerId() + "超时,直接移除");
        removePlayerFromMatchPool(matchPoolPlayerInfo.getPlayerId());

        matchTimeoutConsumer.accept(matchPoolPlayerInfo.getPlayerId());
    }

    public static void matchSuccess(Consumer<List<String>> matchSuccessConsumer){
        MatchHelper.matchSuccessConsumer = matchSuccessConsumer;
    }

    public static void matchTimeout(Consumer<String> matchTimeoutConsumer){
        MatchHelper.matchTimeoutConsumer = matchTimeoutConsumer;
    }



    /**
     * 把玩家从匹配池移除
     *
     * @param playerId
     */
    public static void removePlayerFromMatchPool(String playerId) {
        playerPool.remove(playerId);
    }

    private static void matchProcess(ConcurrentHashMap<String, MatchPoolPlayerInfo> playerPool) {
        long startTime = System.currentTimeMillis();
//        log.info("执行匹配开始|开始时间|" + startTime);
        try {
            //先把匹配池中的玩家按分数分布
            TreeMap<Integer, HashSet<MatchPoolPlayerInfo>> pointMap = new TreeMap<>();
            for (MatchPoolPlayerInfo matchPlayer : playerPool.values()) {
                //在匹配池中是时间太长，直接移除
                if ((System.currentTimeMillis() - matchPlayer.getStartMatchTime()) > timeout * 1000) {
                    matchTimeoutProcess(matchPlayer);
                    continue;
                }
                HashSet<MatchPoolPlayerInfo> set = pointMap.get(matchPlayer.getRank());
                if (set == null) {
                    set = new HashSet<>();
                    set.add(matchPlayer);
                    pointMap.put(matchPlayer.getRank(), set);
                } else {
                    set.add(matchPlayer);
                }
            }

            for (HashSet<MatchPoolPlayerInfo> sameRankPlayers : pointMap.values()) {
                boolean continueMatch = true;
                while (continueMatch) {
                    //找出同一分数段里，等待时间最长的玩家，用他来匹配，因为他的区间最大
                    //如果他都不能匹配到，等待时间比他短的玩家更匹配不到
                    MatchPoolPlayerInfo oldest = null;
                    for (MatchPoolPlayerInfo playerMatchPoolInfo : sameRankPlayers) {
                        if (oldest == null) {
                            oldest = playerMatchPoolInfo;
                        } else if (playerMatchPoolInfo.getStartMatchTime() < oldest.getStartMatchTime()) {
                            oldest = playerMatchPoolInfo;
                        }
                    }
                    if (oldest == null) {
                        break;
                    }
//                    log.info(oldest.getPlayerId() + "|为该分数上等待最久时间的玩家开始匹配|rank|" + oldest.getRank());

                    long now = System.currentTimeMillis();
                    int waitSecond = (int) ((now - oldest.getStartMatchTime()) / 1000);

//                    log.info(oldest.getPlayerId() + "|当前时间已经等待的时间|waitSecond|" + waitSecond + "|当前系统时间|" + now + "|开始匹配时间|" + oldest.getStartMatchTime());

                    //按等待时间扩大匹配范围
//                    float base = 1.5f;// 递增的base值
//                    int floor = 1; // 保底低增值
//                    int stepThreshold = 10; // 递增阈值

                    float u = (float) Math.pow(waitSecond, base);
                    u = u + floor;
                    u = (float) Math.round(u);
                    u = Math.min(u, stepThreshold);

                    int min = (oldest.getRank() - (int) u) < 0 ? 0 : (oldest.getRank() - (int) u);
                    int max = oldest.getRank() + (int) u;

//                    log.info(oldest.getPlayerId() + "|本次搜索rank范围下限|" + min + "|rank范围上限|" + max);

                    int middle = oldest.getRank();

                    List<MatchPoolPlayerInfo> matchPoolPlayer = new ArrayList<MatchPoolPlayerInfo>();
                    //从中位数向两边扩大范围搜索
                    for (int searchRankUp = middle, searchRankDown = middle; searchRankUp <= max || searchRankDown >= min; searchRankUp++, searchRankDown--) {
                        HashSet<MatchPoolPlayerInfo> thisRankPlayers = pointMap.getOrDefault(searchRankUp, new HashSet<MatchPoolPlayerInfo>());
                        if (searchRankDown != searchRankUp && searchRankDown > 0) {
                            thisRankPlayers.addAll(pointMap.getOrDefault(searchRankDown, new HashSet<>()));
                        }
                        if (!thisRankPlayers.isEmpty()) {
                            if (matchPoolPlayer.size() < NEED_MATCH_PLAYER_COUNT) {
                                Iterator<MatchPoolPlayerInfo> it = thisRankPlayers.iterator();
                                while (it.hasNext()) {
                                    MatchPoolPlayerInfo player = it.next();
                                    if (player.getPlayerId() != oldest.getPlayerId()) {//排除玩家本身
                                        if (matchPoolPlayer.size() < NEED_MATCH_PLAYER_COUNT) {
                                            matchPoolPlayer.add(player);
//                                            log.info(oldest.getPlayerId() + "|匹配到玩家|" + player.getPlayerId() + "|rank|" + player.getRank());
                                            //移除
                                            it.remove();
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    }

                    if (matchPoolPlayer.size() == NEED_MATCH_PLAYER_COUNT) {
//                        log.info(oldest.getPlayerId() + "|匹配到玩家数量够了|提交匹配成功处理");
                        //自己也匹配池移除
                        sameRankPlayers.remove(oldest);
                        //匹配成功处理
                        matchPoolPlayer.add(oldest);
                        //TODO 把配对的人提交匹配成功处理
                        matchSuccessProcess(matchPoolPlayer);
                    } else {
                        //本分数段等待时间最长的玩家都匹配不到，其他更不用尝试了
                        continueMatch = false;
//                        log.info(oldest.getPlayerId() + "|匹配到玩家数量不够，取消本次匹配");
                        //归还取出来的玩家
                        for (MatchPoolPlayerInfo player : matchPoolPlayer) {
                            HashSet<MatchPoolPlayerInfo> sameRankPlayer = pointMap.get(player.getRank());
                            sameRankPlayer.add(player);
                        }
                    }
                }
            }
        } catch (Throwable t) {
            log.error("match|error", t);
        }
        long endTime = System.currentTimeMillis();
//        log.info("执行匹配结束|结束时间|" + endTime + "|耗时|" + (endTime - startTime) + "ms");
    }

    private static class MatchPoolPlayerInfo {
        private String playerId;//玩家ID
        private int rank;//玩家分数
        private long startMatchTime;//开始匹配时间


        private MatchPoolPlayerInfo(String playerId, int rank) {
            super();
            this.playerId = playerId;
            this.rank = rank;
            this.startMatchTime = System.currentTimeMillis();
        }

        public String getPlayerId() {
            return playerId;
        }

        public int getRank() {
            return rank;
        }

        public long getStartMatchTime() {
            return startMatchTime;
        }
    }
}
