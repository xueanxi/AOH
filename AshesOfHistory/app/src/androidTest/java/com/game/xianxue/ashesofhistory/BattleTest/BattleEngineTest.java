package com.game.xianxue.ashesofhistory.BattleTest;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.database.BasePersonManager;
import com.game.xianxue.ashesofhistory.database.LineUpDataManager;
import com.game.xianxue.ashesofhistory.game.engine.BattleEngine;
import com.game.xianxue.ashesofhistory.game.model.TeamModel;
import com.game.xianxue.ashesofhistory.game.model.lineup.LineUpBase;
import com.game.xianxue.ashesofhistory.game.model.lineup.LineUpBattle;
import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.game.model.person.NormalPerson;
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
        engine.setmTimeActiveIncrese(1000);
        engine.setmTimePersonAction(500);
        engine.setBattleTeam(t1, t2);
        engine.startBattle();
    }

    @Test
    public void TestLineUp() {
        init();

        // 初始化人物
        // 第一队
        BasePerson play1 = BasePersonManager.getPersonFromDataBaseByPinyin("guanyu");
        BasePerson play2 = BasePersonManager.getPersonFromDataBaseByPinyin("zhangfei");
        BasePerson play3 = BasePersonManager.getPersonFromDataBaseByPinyin("liubei");
        BasePerson play4 = BasePersonManager.getPersonFromDataBaseByPinyin("zhaoyun");
        BasePerson play5 = BasePersonManager.getPersonFromDataBaseByPinyin("zhugeliang");
        SimpleLog.logd("TestPerson","play5 = "+play5);

        BattlePerson b1 = new BattlePerson(new NormalPerson(play1));
        BattlePerson b2 = new BattlePerson(new NormalPerson(play2));
        BattlePerson b3 = new BattlePerson(new NormalPerson(play3));
        BattlePerson b4 = new BattlePerson(new NormalPerson(play4));
        BattlePerson b5 = new BattlePerson(new NormalPerson(play5));
        SimpleLog.logd("TestPerson","b5 = "+b5);
        b3.setLeader(true);
        b5.setCounsellor(true);

        ArrayList<BattlePerson> playerList1 = new ArrayList<BattlePerson>();
        playerList1.add(b1);
        playerList1.add(b2);
        playerList1.add(b3);
        playerList1.add(b4);
        playerList1.add(b5);

        // 第二队
        BasePerson play6 = BasePersonManager.getPersonFromDataBaseByPinyin("caocao");
        BasePerson play7 = BasePersonManager.getPersonFromDataBaseByPinyin("caoren");
        BasePerson play8 = BasePersonManager.getPersonFromDataBaseByPinyin("dianwei");
        BasePerson play9 = BasePersonManager.getPersonFromDataBaseByPinyin("zhangliao");
        BasePerson play10 = BasePersonManager.getPersonFromDataBaseByPinyin("xvchu");
        BasePerson play11 = BasePersonManager.getPersonFromDataBaseByPinyin("gongjianbing");
        BasePerson play12 = BasePersonManager.getPersonFromDataBaseByPinyin("bubing");

        BattlePerson b6 = new BattlePerson(new NormalPerson(play6));
        BattlePerson b7 = new BattlePerson(new NormalPerson(play7));
        BattlePerson b8 = new BattlePerson(new NormalPerson(play8));
        BattlePerson b9 = new BattlePerson(new NormalPerson(play9));
        BattlePerson b10 = new BattlePerson(new NormalPerson(play10));
        BattlePerson b11 = new BattlePerson(new NormalPerson(play11));
        BattlePerson b12 = new BattlePerson(new NormalPerson(play12));
        b7.setLeader(true);
        b6.setCounsellor(true);

        ArrayList<BattlePerson> playerList2 = new ArrayList<BattlePerson>();
        playerList2.add(b6);
        playerList2.add(b7);
        playerList2.add(b8);
        playerList2.add(b9);
        playerList2.add(b10);
        playerList2.add(b11);
        playerList2.add(b12);

        ShowUtils.showArrays(TAG,playerList1);
        ShowUtils.showArrays(TAG,playerList2);

        // 初始化阵型1
        LineUpBase lineUp1 = LineUpDataManager.getDataFromDataBaseById(0);//普通阵容
        LineUpBattle lb1 = new LineUpBattle(lineUp1,playerList1);
        // 初始化阵型2
        LineUpBase lineUp2 = LineUpDataManager.getDataFromDataBaseById(1);//长蛇阵
        LineUpBattle lb2 = new LineUpBattle(lineUp2,playerList2);

        // 初始化阵营
        TeamModel t1 = new TeamModel(TeamModel.CAMP_LEFT, lb1);
        TeamModel t2 = new TeamModel(TeamModel.CAMP_RIGHT, lb2);

        BattleEngine engine = BattleEngine.getInstance();
        engine.setmTimeActiveIncrese(50);
        engine.setmTimePersonAction(50);
        engine.setBattleTeam(t1, t2);
        engine.startBattle();

        try {
            Thread.sleep(999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
