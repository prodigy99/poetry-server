package com.xkj.poetryserver.model;

import com.xkj.poetryserver.controller.utils.Message;
import com.xkj.poetryserver.domain.User;
import com.xkj.poetryserver.dto.SingleTopicSelectionPuzzleDTO;
import com.xkj.poetryserver.dto.UserDTO;
import com.xkj.poetryserver.pojo.SingleTopicSelectionPuzzle;
import com.xkj.poetryserver.service.MultiplayerGameService;
import com.xkj.poetryserver.service.PuzzleService;
import com.xkj.poetryserver.utils.SpringHelper;
import com.xkj.poetryserver.utils.UserHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
public class Game {

    PuzzleService puzzleService = SpringHelper.getBean(PuzzleService.class);
    MultiplayerGameService multiplayerGameService = SpringHelper.getBean(MultiplayerGameService.class);

    private boolean hasRobot = false;
    private List<Player> players;
    private ScheduledExecutorService sec;
    private Random rand = new Random();

    // 从第几秒开始倒计时
    public final static int MAX_TIME = 10;
    // 总共需要答多少道题
    public final static int TOTAL_PUZZLE = 6;
    //机器人uid
    private final static String ROBOT_PLAYER_ID = "-1";
    // 当前答到第几题
    public int index;
    // 当前正在解答的题目
    private SingleTopicSelectionPuzzle nowPuzzle;
    // 答案
    private Map<String, AnswerData> answerResults = new HashMap<>(2);
    // 时间
    private int timer;
    // 已经答了多少题
    private int hasAnswer = 0;

    /**
     * 机器人相关
     */
    // 机器人回答时间
    private int robotReplayTime;

    private Map<String, Integer> totalScore = new HashMap<>(2);

    public Game(User user) {
        // 只有一个player
        this(Arrays.asList(user, UserHelper.getFakeUser(ROBOT_PLAYER_ID)));
        this.hasRobot = true;
    }

    public Game(List<User> users) {
        this.players = new ArrayList<>();
        users.forEach(user -> this.players.add(new Player(user)));
        notifyMatchSuccess();
    }

    public void notifyMatchSuccess() {
        players.forEach(player -> {
            sendOtherMessage(player.getUser().getUid(), new Message(Message.EVENT_TYPE.matchSuccess, new UserDTO(player.getUser())));
        });
    }

    // 声明自己准备好了
    public void ready(String uid) {
        if (this.hasAnswer > TOTAL_PUZZLE) {
            finishGame();
            return;
        }
        getPlayer(uid).ready();
        // 如果没有机器人的话,要确定两个人都准备好
        if (!hasRobot) for (Player player : players) if (!player.isReady()) return;
        startGame();
    }

    // 开始游戏
    public void startGame() {
        init();
        // 开始计时器
        sec = Executors.newSingleThreadScheduledExecutor();
        sec.scheduleWithFixedDelay(() -> {
            this.updateTime();
        }, 1, 1, TimeUnit.SECONDS);//计时器
    }

    private void sendMessage(String uid, Message message) {
        getPlayer(uid).sendMessage(message);
    }

    // 给另一个人发消息
    private void sendOtherMessage(String uid, Message message) {
        getOtherPlayer(uid).sendMessage(message);
    }

    private void sendMessage(Message message) {
        for (Player player : players) {
            try {
                player.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        this.clearAnswer();
        this.newPuzzle();
        this.timer = MAX_TIME;
        if (this.hasRobot) this.robotReplayTime = rand.nextInt(MAX_TIME); // 设置机器人回答的时间
    }

    public void updateTime() {
        this.sendMessage(new Message(Message.EVENT_TYPE.updateTime, timer));
        this.timer--;
        if (hasRobot && this.timer == this.robotReplayTime) {
            if (rand.nextBoolean()) {
                answer(ROBOT_PLAYER_ID, nowPuzzle.getPid(), nowPuzzle.getKey());
            } else {
                int fakeKey;
                while (true) {
                    fakeKey = rand.nextInt(4);
                    if (!String.valueOf(fakeKey).equals(nowPuzzle.getKey())) break;
                }
                answer(ROBOT_PLAYER_ID, nowPuzzle.getPid(), String.valueOf(fakeKey));
            }
        }
        if (timer <= 0) {
            // 超时
            if (!this.isBothAnswer()) {
                produceUnAnswerPlayer((player -> noticeResults(player.getUser().getUid(), new AnswerData(true))));
            }
        }
    }

    // 清除答案
    public void clearAnswer() {
        answerResults.clear();
    }


    // 处理没有回答问题的player
    public void produceUnAnswerPlayer(Consumer<Player> action) {
        List<Player> unAnswerPlayers = new ArrayList<>();
        F:
        for (Player player : players) {
            for (String uid : answerResults.keySet()) {
                if (player.getUser().getUid().equals(uid)) continue F;
            }
            unAnswerPlayers.add(player);
        }
        unAnswerPlayers.forEach(action);
    }


    // 两人是不是都作答完毕了
    public boolean isBothAnswer() {
        return answerResults.size() == 2;
    }

    public void newPuzzle() {
        this.nowPuzzle = puzzleService.getSingleTopicSelectionPuzzle();
        this.hasAnswer++;
        this.sendMessage(new Message(Message.EVENT_TYPE.newPuzzle, new SingleTopicSelectionPuzzleDTO(this.nowPuzzle)));
    }

    public void finishGame() {
        players.forEach((player) -> {
            Map<String, Integer> data = new HashMap();
            data.put("meScore", totalScore.get(player.getUser().getUid()));
            data.put("otherScore", totalScore.get(getOtherPlayer(player.getUser().getUid()).getUser().getUid()));
            sendMessage(player.getUser().getUid(), new Message(Message.EVENT_TYPE.finishGame, data));
            multiplayerGameService.removeGame(player.getUser().getUid());
        });
        sec.shutdownNow();
    }

    public void noticeResults(String uid, AnswerData answerData) {
        answerResults.put(uid, answerData);
        sendMessage(uid, new Message(Message.EVENT_TYPE.meScore, answerData));
        totalScore.put(uid, totalScore.get(uid) == null ? answerData.score : totalScore.get(uid) + answerData.score);
        if (isBothAnswer()) {
            answerResults.forEach((_uid, data) -> {
                sendOtherMessage(_uid, new Message(Message.EVENT_TYPE.otherScore, answerResults.get(_uid)));
            });
            puzzleService.removePuzzle(nowPuzzle.getPid());

            // 展示分数
            players.forEach((player) -> {
                Map<String, Integer> data = new HashMap();
                data.put("meScore", totalScore.get(player.getUser().getUid()));
                data.put("otherScore", totalScore.get(getOtherPlayer(player.getUser().getUid()).getUser().getUid()));
                data.put("totalScore", TOTAL_PUZZLE * MAX_TIME * 10);

                sendMessage(player.getUser().getUid(), new Message(Message.EVENT_TYPE.showScore, data));
            });

            sec.shutdown();
        }
    }


    public Player getPlayer(String uid) {
        for (Player player : this.players) {
            if (player.getUser().getUid().equals(uid))
                return player;
        }
        return null;
    }

    public Player getOtherPlayer(String uid) {
        for (Player player : players) {
            if (!player.getUser().getUid().equals(uid)) return player;
        }
        return null;
    }

    /**
     * 回答问题
     */
    public boolean answer(String uid, String pid, String key) {
//         考虑到网络状况，可能会导致传输的pid为上一个问题的pid，自动丢弃
        if (!pid.equals(nowPuzzle.getPid())) return false;
        for (String answerUid : answerResults.keySet()) {// 如果已经回答过,就不让回答了
            if (uid.equals(answerUid)) return false;
        }
        boolean isRight = puzzleService.checkKey(pid, key, false);
        int score = isRight ? this.timer * 10 : 0;
//        总分数
//        发送消息
        noticeResults(uid, new AnswerData(isRight, score, key));

        return true;
    }

    private class AnswerData {
        private boolean isRight;
        private boolean isTimeOut;
        private int score;
        private String key;

        public AnswerData(boolean isRight, boolean isTimeOut, int score, String key) {
            this.isRight = isRight;
            this.isTimeOut = isTimeOut;
            this.score = score;
            this.key = key;
        }

        public AnswerData(boolean isTimeOut) {
            this(false, true, 0, String.valueOf(-1));
        }

        public AnswerData(boolean isRight, int score, String key) {
            this(isRight, false, score, key);
        }

        public boolean isRight() {
            return isRight;
        }

        public void setRight(boolean right) {
            isRight = right;
        }

        public boolean isTimeOut() {
            return isTimeOut;
        }

        public void setTimeOut(boolean timeOut) {
            isTimeOut = timeOut;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
