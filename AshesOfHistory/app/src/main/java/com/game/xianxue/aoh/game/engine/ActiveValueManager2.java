package com.game.xianxue.aoh.game.engine;

import com.game.xianxue.aoh.Log.BattleLog;
import com.game.xianxue.aoh.game.model.person.BattlePerson;
import com.game.xianxue.aoh.comparator.ActiveValueComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 这是模拟人物行动值变化的模型
 * Created by anxi.xue on 8/30/17.
 */

public class ActiveValueManager2{
    private static final String TAG = "ActiveValueManager";

    int mTimeIncreaseActiveInterval = 100;          // 活跃值增加的时间间隔

    Thread innerThread = null;
    ArrayList<BattlePerson> personLists = null;
    ArrayList<BattlePerson> personMaxActive = null;
    static final Object lock = new Object();
    BattleEngine mBattleEngine;
    IActiveValueManagerCallBack mCallBack;
    ActiveValueComparator mComparator;


    public void setCallBAck(IActiveValueManagerCallBack callBack) {
        this.mCallBack = callBack;
    }

    public void increaseActiveValue() {
        if(personMaxActive == null){
            personMaxActive = new ArrayList<>();
        }
        personMaxActive.clear();
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
        BattleLog.log(BattleLog.TAG_AV, sb.toString());
    }

    public List<BattlePerson> getActivePersons() {
        // 如果没有行动值满的人，怎进入下一轮续气
        if (personMaxActive == null || personMaxActive.size() == 0) {
            return null;
        }

        if (personMaxActive.size() > 1) {
            Collections.sort(personMaxActive, mComparator);
        }

        return personMaxActive;
        //return person;
    }

    public ActiveValueManager2(BattleEngine battleEngine, ArrayList<BattlePerson> personLists) {
        this.mBattleEngine = battleEngine;
        this.personLists = personLists;
        this.mComparator = new ActiveValueComparator();
    }

    public void setTimeActiveInterval(int mTimeActiveInterval) {
        this.mTimeIncreaseActiveInterval = mTimeActiveInterval;
    }

    interface IActiveValueManagerCallBack {
        void personDoAction(BattlePerson person);
    }
}
