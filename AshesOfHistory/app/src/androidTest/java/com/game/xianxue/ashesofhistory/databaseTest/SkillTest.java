package com.game.xianxue.ashesofhistory.databaseTest;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.game.xianxue.ashesofhistory.database.LineUpDataManager;
import com.game.xianxue.ashesofhistory.database.PersonDataManager;
import com.game.xianxue.ashesofhistory.database.SkillDataManager;
import com.game.xianxue.ashesofhistory.game.engine.BattleEngine;
import com.game.xianxue.ashesofhistory.game.model.TeamModel;
import com.game.xianxue.ashesofhistory.game.model.lineup.LineUpBase;
import com.game.xianxue.ashesofhistory.game.model.lineup.LineUpBattle;
import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.game.model.person.NormalPerson;
import com.game.xianxue.ashesofhistory.game.skill.SkillBase;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;
import com.game.xianxue.ashesofhistory.utils.XmlUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SkillTest {
    private static final String TAG = "=SkillTest";
    Context mContext;

    public void init() {
        mContext = getContext();
    }

    public Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    /**
     * 测试XML解析所有节能
     */
    @Test
    public void TestXmlUtilGetAllSkill() {
        init();
        try {
            ArrayList<SkillBase> skillList = null;
            try {
                skillList = XmlUtils.getAllSkill(mContext);
                ShowUtils.showArrayLists(TAG,skillList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试获取所有技能
     */
    @Test
    public void TestGetAllSkillFromDataBase() {
        init();
        try {
            SkillDataManager.getAllSkillFromDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试通过id 获取一个技能
     */
    @Test
    public void TestgetSkillByIdFormDataBase() {
        init();
        SkillDataManager.getSkillFromDataBaseById(0);
    }
    /**
     * 测试 火攻 技能
     */
    @Test
    public void TestHuoGong() {
        init();
        BasePerson play1 = PersonDataManager.getPersonFromDataBaseByPinyin("zhugeliang");
        BattlePerson b1 = new BattlePerson(new NormalPerson(play1,15));
        BasePerson play2 = PersonDataManager.getPersonFromDataBaseByPinyin("liubei");
        BattlePerson b2 = new BattlePerson(new NormalPerson(play2,15));
        b2.setLeader(true);
        b1.setCounsellor(true);
        ArrayList<BattlePerson> playerList1 = new ArrayList<BattlePerson>();
        playerList1.add(b1);
        playerList1.add(b2);

        BasePerson play3= PersonDataManager.getPersonFromDataBaseByPinyin("ceshimuren");
        BattlePerson b3 = new BattlePerson(new NormalPerson(play3,15));
        BasePerson play4 = PersonDataManager.getPersonFromDataBaseByPinyin("caocao");
        BattlePerson b4 = new BattlePerson(new NormalPerson(play4,15));
        b4.setLeader(true);
        b3.setCounsellor(true);
        ArrayList<BattlePerson> playerList2 = new ArrayList<BattlePerson>();
        playerList2.add(b3);
        playerList2.add(b4);

        // 初始化阵型1
        LineUpBase lineUp1 = LineUpDataManager.getDataFromDataBaseById(0);//普通阵容
        LineUpBattle lb1 = new LineUpBattle(lineUp1,playerList1);
        lb1.displayMatrix();

        // 初始化阵型2
        LineUpBase lineUp2 = LineUpDataManager.getDataFromDataBaseById(0);//普通阵容
        LineUpBattle lb2 = new LineUpBattle(lineUp2,playerList2);
        lb2.displayMatrix();

        // 初始化阵营
        TeamModel t1 = new TeamModel(TeamModel.CAMP_LEFT, lb1);
        TeamModel t2 = new TeamModel(TeamModel.CAMP_RIGHT, lb2);

        BattleEngine engine = BattleEngine.getInstance();
        engine.setBattleTeam(t1, t2);
        engine.setTimeActiveIncrese(50);
        engine.setTimePersonAction(50);
        engine.startBattle();
        SystemClock.sleep(999999);
    }
}
