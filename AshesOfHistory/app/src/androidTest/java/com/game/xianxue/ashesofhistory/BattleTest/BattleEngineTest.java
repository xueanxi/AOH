package com.game.xianxue.ashesofhistory.BattleTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.database.BasePersonManager;
import com.game.xianxue.ashesofhistory.game.engine.BattleEngine;
import com.game.xianxue.ashesofhistory.model.TeamModel;
import com.game.xianxue.ashesofhistory.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.model.person.NormalPerson;
import com.game.xianxue.ashesofhistory.utils.SerializableUtils;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BattleEngineTest {

    private static final String TAG = "BattleEngineTest";
    Context mContext;

    public void init() {
        mContext = getContext();
    }

    public Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void TestStartBattle() {
        init();
        BasePerson play1 = BasePersonManager.getPersonFromDataBaseByPinyin("guanyu");
        BasePerson play2 = BasePersonManager.getPersonFromDataBaseByPinyin("lvbu");
        BasePerson play3 = BasePersonManager.getPersonFromDataBaseByPinyin("zhugeliang");
        BasePerson play4 = BasePersonManager.getPersonFromDataBaseByPinyin("zhangfei");

        NormalPerson n1 = new NormalPerson(play1);
        NormalPerson n2 = new NormalPerson(play2);
        NormalPerson n3 = new NormalPerson(play3);
        NormalPerson n4 = new NormalPerson(play4);

        BattlePerson b1 = new BattlePerson(n1, TeamModel.CAMP_LEFT);
        BattlePerson b2 = new BattlePerson(n2, TeamModel.CAMP_LEFT);
        BattlePerson b3 = new BattlePerson(n3, TeamModel.CAMP_RIGHT);
        BattlePerson b4 = new BattlePerson(n4, TeamModel.CAMP_RIGHT);

        ArrayList<BattlePerson> playerList1 = new ArrayList<BattlePerson>();
        playerList1.add(b1);
        playerList1.add(b2);

        ArrayList<BattlePerson> playerList2 = new ArrayList<BattlePerson>();
        playerList2.add(b3);
        playerList2.add(b4);

        TeamModel t1 = new TeamModel(TeamModel.CAMP_LEFT, playerList1);
        TeamModel t2 = new TeamModel(TeamModel.CAMP_RIGHT, playerList2);

        BattleEngine engine = BattleEngine.getInstance();
        engine.setmTimeIncreseActive(1000);
        engine.setmTimePerAction(500);
        engine.setBattleTeam(t1, t2);
        engine.startBattle();


    }

    @Test
    public void TestSerializableUtils() {
        init();

        BasePerson play1 = BasePersonManager.getPersonFromDataBaseByPinyin("guanyu");
        BasePerson play2 = BasePersonManager.getPersonFromDataBaseByPinyin("lvbu");
        BasePerson play3 = BasePersonManager.getPersonFromDataBaseByPinyin("zhugeliang");
        BasePerson play4 = BasePersonManager.getPersonFromDataBaseByPinyin("zhangfei");

        ArrayList<BasePerson> list = new ArrayList<BasePerson>();
        list.add(play1);
        list.add(play2);
        list.add(play3);
        list.add(play4);
        ShowUtils.showArrays(TAG, list);
        SerializableUtils.writeObjectToFile(list, "baseperson");
        SimpleLog.logd(TAG, "=======");

        list = null;
        list = SerializableUtils.readObjectFromFile("baseperson");
        ShowUtils.showArrays(TAG, list);
    }

    @Test
    public void TestSaveBasePersonToFile() {
        init();
        ArrayList<BasePerson> list = BasePersonManager.getAllPersonFromDataBase();
        SerializableUtils.writeObjectToFile(list, "baseperson");
        SimpleLog.logd(TAG, "=======");
        list = null;
        list = SerializableUtils.readObjectFromFile("baseperson");
        ShowUtils.showArrays(TAG, list);
    }
}
