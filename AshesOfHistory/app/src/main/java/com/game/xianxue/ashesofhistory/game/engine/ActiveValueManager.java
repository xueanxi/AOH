package com.game.xianxue.ashesofhistory.game.engine;

import android.os.SystemClock;

import com.game.xianxue.ashesofhistory.Log.BattleLog;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;

import java.util.ArrayList;

/**
 * 这是模拟人物行动值变化的模型
 * Created by anxi.xue on 8/30/17.
 */

public class ActiveValueManager implements Runnable {
    private static final String TAG = "ActiveValueManager";

    int mTimeActiveInterval = 100;          // 活跃值增加的时间间隔

    Thread innerThread = null;
    ArrayList<BattlePerson> personLists = null;
    ArrayList<BattlePerson> personMaxActive = null;
    Object lock;
    BattleEngine mBattleEngine;


    boolean isStart = false;
    boolean isOver = false;
    boolean suspended = false;

    public ActiveValueManager(BattleEngine battleEngine, ArrayList<BattlePerson> personLists) {
        this.mBattleEngine = battleEngine;
        this.personLists = personLists;
        this.lock = new Object();
    }


    @Override
    public void run() {

        // personMaxActive 是存放行动值满了的人物
        personMaxActive = new ArrayList<BattlePerson>();

        while (!isOver) {
            if (personLists == null) {
                stop();
                break;
            }

            // 每一个人增加行动值，并且把行动值到达最大的人物，添加到 personMaxActive 列表中
            StringBuilder sb = new StringBuilder();
            for (BattlePerson person : personLists) {
                if (person.getHP() > 0) {
                    person.increaseActiveValues();
                }
                if (person.getActiveValuePencent() >= 1.0f) {
                    personMaxActive.add(person);
                }

                sb.append(person.getName() + " HP:" + person.getHP() + " Active:" + person.getActiveValuePencent() + "\n");
            }

            SystemClock.sleep(mTimeActiveInterval);


            BattleLog.log(sb.toString());

            // 处理行动值满了的人物行动
            while (personMaxActive != null && personMaxActive.size() > 0) {
                BattleLog.log("有" + personMaxActive.size() + "人蓄气完成。");
                if (personMaxActive.size() >= 1) {
                    // 先查看是否有行动值已经满的家伙,如果有，从中挑选当前百分比最高的人
                    int index = getMaxActivePersonIndex(personMaxActive);
                    BattleLog.log(personMaxActive.get(index).getName() + "发动进行进攻");

                    // 调用战斗引擎执行行动
                    mBattleEngine.doAction(personMaxActive.get(index));

                    // 暂停，等待战斗引擎唤醒
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    // 唤醒之后 把刚刚执行过行动的人物 从personMaxActive 中移除
                    personMaxActive.get(index).reduceActiveValue();
                    personMaxActive.remove(index);

                    // 一个人物进攻完了之后，需要检查其他蓄气满的人生命值是否为0，需要移除。
                    for (int i = 0; i < personMaxActive.size(); i++) {
                        if (personMaxActive.get(i).getHP() <= 0) {
                            personMaxActive.remove(i);
                        }
                    }
                }
            }
        }

        personMaxActive.clear();
        personMaxActive = null;
        BattleLog.log("战斗结束了，蓄气模型关闭。。。");
    }

    /**
     * 获得 personlists 中，行动值最高的人的 索引
     *
     * @param personlists
     * @return 返回 -1 说明没有达到最大活跃值的人，返回其他数字则是返回 达到最大行动值的索引
     */
    private int getMaxActivePersonIndex(ArrayList<BattlePerson> personlists) {
        if (personMaxActive == null || personMaxActive.size() == 0) {
            return -1;
        }
        if (personMaxActive.size() == 1) {
            return 0;
        } else if (personlists.size() >= 2) {
            int maxIndex = 0;
            BattlePerson max = personlists.get(0);
            for (int i = 1; i < personlists.size(); i++) {
                BattlePerson curPerson = personlists.get(i);
                if (max.getActiveValuePencent() < curPerson.getActiveValuePencent()) {
                    max = curPerson;
                    maxIndex = i;
                }
            }
            return maxIndex;
        }
        return -1;
    }


    public void start() {
        if (innerThread == null) {
            innerThread = new Thread(this);
        }
        isOver = false;
        isStart = true;
        innerThread.start();
    }

    /**
     * 停止运行，停止之后就结束了，不能恢复运行
     * 如果要恢复的话，执行 suspend暂停方法，suspend暂停之后可以调用 resume 方法恢复运行
     */
    public void stop() {
        isOver = true;
        isStart = false;
    }

    /**
     * 暂停
     */
    public void suspend() {
        suspended = true;
    }

    /**
     * 继续
     */
    public void resume() {
        if (isStart()) {
            synchronized (lock) {
                suspended = false;
                lock.notify();
            }
        } else {
            start();
        }
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public void setTimeActiveInterval(int mTimeActiveInterval) {
        this.mTimeActiveInterval = mTimeActiveInterval;
    }
}
