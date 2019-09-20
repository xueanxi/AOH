package com.game.xianxue.aoh.game.engine;

import android.os.SystemClock;

import com.game.xianxue.aoh.Log.BattleLog;
import com.game.xianxue.aoh.game.model.person.BattlePerson;
import com.game.xianxue.aoh.comparator.ActiveValueComparator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 这是模拟人物行动值变化的模型
 * Created by anxi.xue on 8/30/17.
 */

public class ActiveValueManager implements Runnable {
    private static final String TAG = "ActiveValueManager";

    int mTimeIncreaseActiveInterval = 100;          // 活跃值增加的时间间隔

    Thread innerThread = null;
    ArrayList<BattlePerson> personLists = null;
    ArrayList<BattlePerson> personMaxActive = null;
    static final Object lock = new Object();
    BattleEngine mBattleEngine;


    boolean isStart = false;
    boolean isStop = false;
    boolean isPause = false;

    public ActiveValueManager(BattleEngine battleEngine, ArrayList<BattlePerson> personLists) {
        this.mBattleEngine = battleEngine;
        this.personLists = personLists;
    }


    @Override
    public void run() {

        isStart = true;
        // personMaxActive 是存放行动值满了的人物
        personMaxActive = new ArrayList<BattlePerson>();
        ActiveValueComparator activeValueComparator = new ActiveValueComparator();

        while (!isStop) {
            if (personLists == null) {
                stop();
                break;
            }

            // 每一个人增加行动值，并且把行动值到达最大的人物，添加到 personMaxActive 列表中
            StringBuilder sb = new StringBuilder();
            for (BattlePerson person : personLists) {
                if (person.getHP_Current() > 0) {
                    person.increaseActiveValues();
                }
                if (person.getActiveValuePencent() >= 1.0f) {
                    personMaxActive.add(person);
                }

                sb.append(person.getName() + " HP:" + person.getHP_Current() + " Active:" + person.getActiveValuePencent() + "\n");
            }

            BattleLog.log(BattleLog.TAG_ST,sb.toString());

            SystemClock.sleep(mTimeIncreaseActiveInterval);

            // 蓄气一轮之后判断是否暂停
            checkIfPause();

            // 如果没有行动值满的人，怎进入下一轮续气
            if(personMaxActive == null || personMaxActive.size() ==0){
                continue;
            }

            if(personMaxActive.size() > 1){
                Collections.sort(personMaxActive,activeValueComparator);
            }

            // 处理行动值满了的人物行动
            for(BattlePerson person:personMaxActive){
                if(person.getHP_Current()<=0){
                    BattleLog.log(BattleLog.TAG_PG,"轮到"+person.getName()+"行动，但是他已经挂掉了，跳过...");
                    continue;
                }

                // 调用战斗引擎执行行动
                mBattleEngine.doAction(person);

                // 一人行动之后判断是否暂停
                checkIfPause();
            }

            // 所有满行动值的人执行之后，清空
            personMaxActive.clear();
        }

        personMaxActive.clear();
        personMaxActive = null;
        BattleLog.log(BattleLog.TAG_PG,"战斗结束了，蓄气模型关闭。。。");
    }

    private void checkIfPause(){
        // 一个人行动之后，判断是否暂停
        synchronized (lock) {
            try {
                while (isPause){
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        if (innerThread == null) {
            innerThread = new Thread(this);
        }
        isStop = false;
        isStart = true;
        innerThread.start();
    }

    public boolean isStart(){
        return isStart;
    }

    /**
     * 停止运行，停止之后就结束了，不能恢复运行
     * 如果要恢复的话，执行 suspend暂停方法，suspend暂停之后可以调用 resume 方法恢复运行
     */
    public void stop() {
        isStop = true;
    }

    /**
     * 暂停
     */
    public void pause() {
        isPause = true;
    }

    /**
     * 继续
     */
    public void resume() {
        if (isStart) {
            synchronized (lock) {
                isPause = false;
                lock.notify();
            }
        }
    }

    public void setTimeActiveInterval(int mTimeActiveInterval) {
        this.mTimeIncreaseActiveInterval = mTimeActiveInterval;
    }
}
