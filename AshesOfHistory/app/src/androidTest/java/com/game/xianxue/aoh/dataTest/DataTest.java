package com.game.xianxue.aoh.dataTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.game.xianxue.aoh.database.FormationManager;
import com.game.xianxue.aoh.database.PersonDataManager;
import com.game.xianxue.aoh.game.model.lineup.FormationBase;
import com.game.xianxue.aoh.game.model.lineup.FormationBattle;
import com.game.xianxue.aoh.game.model.person.BasePerson;
import com.game.xianxue.aoh.game.model.person.BattlePerson;
import com.game.xianxue.aoh.game.model.person.NormalPerson;
import com.game.xianxue.aoh.utils.ShowUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataTest {

    private static final String TAG = "BattleEngineTest";
    Context mContext;

    public void init() {
        mContext = getContext();
    }

    public Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void TestLineUp() {
        init();

        // 初始化人物
        // 第一队
        BasePerson play1 = PersonDataManager.getPersonFromDataBaseByPinyin("guanyu");
        BasePerson play2 = PersonDataManager.getPersonFromDataBaseByPinyin("zhangfei");
        BasePerson play3 = PersonDataManager.getPersonFromDataBaseByPinyin("liubei");
        BasePerson play4 = PersonDataManager.getPersonFromDataBaseByPinyin("zhaoyun");
        BasePerson play5 = PersonDataManager.getPersonFromDataBaseByPinyin("zhugeliang");

        BattlePerson b1 = new BattlePerson(new NormalPerson(play1));
        BattlePerson b2 = new BattlePerson(new NormalPerson(play2));
        BattlePerson b3 = new BattlePerson(new NormalPerson(play3));
        BattlePerson b4 = new BattlePerson(new NormalPerson(play4));
        BattlePerson b5 = new BattlePerson(new NormalPerson(play5));
        b3.setLeader(true);
        b5.setCounsellor(true);

        ArrayList<BattlePerson> playerList1 = new ArrayList<BattlePerson>();
        playerList1.add(b1);
        playerList1.add(b2);
        playerList1.add(b3);
        playerList1.add(b4);
        playerList1.add(b5);

        // 第二队
        BasePerson play6 = PersonDataManager.getPersonFromDataBaseByPinyin("caocao");
        BasePerson play7 = PersonDataManager.getPersonFromDataBaseByPinyin("caoren");
        BasePerson play8 = PersonDataManager.getPersonFromDataBaseByPinyin("dianwei");
        BasePerson play9 = PersonDataManager.getPersonFromDataBaseByPinyin("zhangliao");
        BasePerson play10 = PersonDataManager.getPersonFromDataBaseByPinyin("xvchu");
        BasePerson play11 = PersonDataManager.getPersonFromDataBaseByPinyin("gongjianbing");
        BasePerson play12 = PersonDataManager.getPersonFromDataBaseByPinyin("bubing");

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

        ShowUtils.showArrayLists(TAG,playerList1);
        ShowUtils.showArrayLists(TAG,playerList2);

        // 初始化阵型1
        FormationBase lineUp1 = FormationManager.getDataFromDataBaseById(0);//普通阵容
        FormationBattle lb1 = new FormationBattle(lineUp1,playerList1);
        lb1.displayMatrix();
        // 初始化阵型2
        FormationBase lineUp2 = FormationManager.getDataFromDataBaseById(1);//长蛇阵
        FormationBattle lb2 = new FormationBattle(lineUp2,playerList2);
        lb2.displayMatrix();
        // 初始化阵营
        //TeamModel t1 = new TeamModel(TeamModel.CAMP_LEFT, lb1);
        //TeamModel t2 = new TeamModel(TeamModel.CAMP_RIGHT, lb2);
    }
}
