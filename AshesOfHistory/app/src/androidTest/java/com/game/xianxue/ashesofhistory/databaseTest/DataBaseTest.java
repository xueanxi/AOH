package com.game.xianxue.ashesofhistory.databaseTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.database.PersonDataManager;
import com.game.xianxue.ashesofhistory.database.BuffDataManager;
import com.game.xianxue.ashesofhistory.database.SkillDataManager;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBase;
import com.game.xianxue.ashesofhistory.game.skill.SkillBase;
import com.game.xianxue.ashesofhistory.game.model.TeamModel;
import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.game.model.person.NormalPerson;
import com.game.xianxue.ashesofhistory.game.skill.SkillBattle;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;
import com.game.xianxue.ashesofhistory.utils.XmlUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataBaseTest {
    private static final String TAG = "DataBaseTest";
    Context mContext;

    public void init() {
        mContext = getContext();
    }

    public Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void TestgetAllPlayer() {
        init();
        PersonDataManager.showAllPersonFromDataBase();
    }

    /**
     * 测试数据库获取武将
     */
    @Test
    public void TestgetPlayerByName() {
        init();
        BasePerson p1 = PersonDataManager.getPersonFromDataBaseByPinyin("guanyu");
        if (p1 == null) {
            SimpleLog.loge(TAG, "TestgetPlayerByName() : p1 == null");
        } else {
            NormalPerson normalPerson = new NormalPerson(p1, 5);
            SimpleLog.logd(TAG, normalPerson.toString());

            BattlePerson battlePerson = new BattlePerson(normalPerson, TeamModel.CAMP_LEFT);
            SimpleLog.logd(TAG, battlePerson.toString());
        }
    }

    /**
     * 测试XML解析所有人物
     */
    @Test
    public void TestXmlUtilGetAll() {
        init();
        try {
            XmlUtils.getAllCharacter(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 测试XML解析所有人物
     */
    @Test
    public void TestXmlUtilGetAllBuff() {
        init();
        try {
            XmlUtils.getAllBuff(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void TestXmlUtilGetAllSkillFromDataBase() {
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
        SkillBase base = SkillDataManager.getSkillFromDataBaseById(197);
        Log.d(TAG,"base = "+base);

        SkillBattle battle = new SkillBattle(base,3);
        Log.d(TAG,"battle = "+battle);
    }


    /**
     * 测试通过id 获取一个技能
     */
    @Test
    public void TestgetbuffByIdFormDataBase2() {
        init();
        BuffDataManager.getBuffFromDataBaseById(0);
        BuffDataManager.getBuffFromDataBaseById(3);
        //BuffDataManager.getBuffFromDataBaseById(1);
        //BuffDataManager.getAllBuffFromDataBase();
    }
    /**
     * 测试通过id 获取一个技能
     */
    @Test
    public void TestgetbuffByIdFormDataBase() {
        init();
        BuffDataManager.getBuffFromDataBaseById(0);
        BuffDataManager.getBuffFromDataBaseById(3);
        //BuffDataManager.getBuffFromDataBaseById(1);
        //BuffDataManager.getAllBuffFromDataBase();
    }


    /**
     * 测试通过id 获取一个技能
     */
    @Test
    public void TestParseLineUpdata() throws Exception {
        init();
        XmlUtils.getAllLineUp(mContext);
        //BuffDataManager.getBuffFromDataBaseById(1);
        //BuffDataManager.getAllBuffFromDataBase();
    }

    /**
     * 测试通过id 获取一个阵型
     */
    @Test
    public void TestgetLineupdata() throws Exception {
        init();
        //LineUpDataManager.getAllDataFromDataBase();
        XmlUtils.getAllBuff(mContext);
        //BuffDataManager.getBuffFromDataBaseById(1);
        //BuffDataManager.getAllBuffFromDataBase();
    }

    /**
     * 测试通过id 获取一个buff
     */
    @Test
    public void TestgetBuffdata() throws Exception {
        init();
        ArrayList<BuffBase> datas = BuffDataManager.getAllBuffFromDataBase();
    }
}
