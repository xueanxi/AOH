package com.game.xianxue.ashesofhistory.BattleTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.database.PersonDataManager;
import com.game.xianxue.ashesofhistory.database.BuffDataManager;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBase;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBattle;
import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.game.model.person.NormalPerson;
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
public class BuffTest {

    private static final String TAG = "=BuffTest";
    Context mContext;

    public void init() {
        mContext = getContext();
    }

    public Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void TestBasisBuff() {
        init();

        BasePerson play1 = PersonDataManager.getPersonFromDataBaseByPinyin("guanyu");
        NormalPerson b1 = new NormalPerson(play1,1);
        SimpleLog.logd(TAG,"TestBasisBuff():before "+b1.display());

        //BuffBase buffbase0 = BuffDataManager.getBuffFromDataBaseById(0);
        //BuffBase buffbase1 = BuffDataManager.getBuffFromDataBaseById(1);
        BuffBase buffbase2 = BuffDataManager.getBuffFromDataBaseById(100);
        //BuffBattle buffbattle0 = new BuffBattle(buffbase0);
        //BuffBattle buffbattle1 = new BuffBattle(buffbase1);
        BuffBattle buffbattle2 = new BuffBattle(buffbase2);
        buffbattle2.setLevel(4);
        ArrayList<BuffBattle> bufflist = new ArrayList<BuffBattle>();
        //bufflist.add(buffbattle0);
        //bufflist.add(buffbattle1);
        bufflist.add(buffbattle2);
        ShowUtils.showArrayLists(TAG,bufflist);


        b1.setBuffPassive(bufflist);
        b1.setLevelAndUpdate(1);

        SimpleLog.logd(TAG,"TestBasisBuff():after "+b1.display());
    }

    @Test
    public void TestPanelBuff() {
        init();

        BasePerson play1 = PersonDataManager.getPersonFromDataBaseByPinyin("zhangfei");
        NormalPerson b1 = new NormalPerson(play1,1);
        SimpleLog.logd(TAG,"TestBasisBuff():before "+b1.display());

        //BuffBase buffbase0 = BuffDataManager.getBuffFromDataBaseById(0);
        //BuffBase buffbase1 = BuffDataManager.getBuffFromDataBaseById(1);
        BuffBase buffbase2 = BuffDataManager.getBuffFromDataBaseById(16);
        //BuffBattle buffbattle0 = new BuffBattle(buffbase0);
        //BuffBattle buffbattle1 = new BuffBattle(buffbase1);
        BuffBattle buffbattle2 = new BuffBattle(buffbase2);
        buffbattle2.setLevel(5);
        ArrayList<BuffBattle> bufflist = new ArrayList<BuffBattle>();
        //bufflist.add(buffbattle0);
        //bufflist.add(buffbattle1);
        bufflist.add(buffbattle2);
        ShowUtils.showArrayLists(TAG,bufflist);


        b1.setBuffPassive(bufflist);
        b1.setLevelAndUpdate(1);

        SimpleLog.logd(TAG,"TestBasisBuff():after "+b1.display());
    }

    /**
     * 测试复合B
     */
    @Test
    public void TestDoubleBuff() {
        init();

        BasePerson play1 = PersonDataManager.getPersonFromDataBaseByPinyin("guanyu");
        NormalPerson b1 = new NormalPerson(play1,1);
        SimpleLog.logd(TAG,"TestBasisBuff():before "+b1.display());

        //BuffBase buffbase0 = BuffDataManager.getBuffFromDataBaseById(0);
        //BuffBase buffbase1 = BuffDataManager.getBuffFromDataBaseById(1);
        BuffBase buffbase2 = BuffDataManager.getBuffFromDataBaseById(100);
        //BuffBattle buffbattle0 = new BuffBattle(buffbase0);
        //BuffBattle buffbattle1 = new BuffBattle(buffbase1);
        BuffBattle buffbattle2 = new BuffBattle(buffbase2);
        buffbattle2.setLevel(1);
        ArrayList<BuffBattle> bufflist = new ArrayList<BuffBattle>();
        //bufflist.add(buffbattle0);
        //bufflist.add(buffbattle1);
        bufflist.add(buffbattle2);
        ShowUtils.showArrayLists(TAG,bufflist);


        b1.setBuffPassive(bufflist);
        b1.setLevelAndUpdate(1);

        SimpleLog.logd(TAG,"TestBasisBuff():after "+b1.display());
    }


}
