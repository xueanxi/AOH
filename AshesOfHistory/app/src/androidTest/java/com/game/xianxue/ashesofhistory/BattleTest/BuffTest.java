package com.game.xianxue.ashesofhistory.BattleTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.database.PersonDataManager;
import com.game.xianxue.ashesofhistory.database.BuffDataManager;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBase;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBattle;
import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.game.model.person.NormalPerson;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static com.game.xianxue.ashesofhistory.interfaces.Interface_Buff.BUFF_TIME_UNLIMITED;
import static com.game.xianxue.ashesofhistory.interfaces.Interface_Buff.BUFF_TYPE_LAST;

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


        b1.setPassiveBuffList(bufflist);
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


        b1.setPassiveBuffList(bufflist);
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


        b1.setPassiveBuffList(bufflist);
        b1.setLevelAndUpdate(1);

        SimpleLog.logd(TAG,"TestBasisBuff():after "+b1.display());
    }


    /**
     * 测试持续叠加的buff
     */
    @Test
    public void TestChiXuBuff() {
        init();

        BasePerson play1 = PersonDataManager.getPersonFromDataBaseByPinyin("guanyu");
        BattlePerson person1 = new BattlePerson(new NormalPerson(play1,15));

        BuffBase b1 = BuffDataManager.getBuffFromDataBaseById(505);
        BuffBattle buff1 = new BuffBattle(b1,3);

        person1.addBuff(buff1);

        Log.d(TAG,"加持之前:"+person1.display());

        for(int i = 0;i<10;i++){
            handleBuff(person1);
            person1.updateBattleAttribute();
            Log.d(TAG,"加持之后"+i+"回合："+person1.display());
        }
    }

    /**
     * 处理Buff效果
     * @param actionPerson
     */
    private void handleBuff(BattlePerson actionPerson) {
        // TODO: 10/17/17 处理持续buff效果
        ArrayList<BuffBattle> buffPassives = actionPerson.getPassiveBuffList();
        ArrayList<BuffBattle> buffActives = actionPerson.getActiveBuffList();
        BuffBattle buff = null;

        // 处理被动技能的Buff
        if(buffPassives != null){
            for(int i =0;i<buffPassives.size();i++){
                buff = buffPassives.get(i);
                if(buff == null) continue;

                // buff一般都有回合限制，把时间到了的清除掉
                if(BUFF_TIME_UNLIMITED != buff.getTime()){
                    int remainTime  = buff.getRemainTime();
                    if(remainTime > 0)  {
                        buff.setRemainTime(remainTime--);
                    }else{
                        buffPassives.remove(i);
                    }
                }

                // 处理可以持续叠加的buff
                if(BUFF_TYPE_LAST != buff.getBuff_type())continue;
                buff.setDuration(buff.getDuration()+1);
            }
        }

        // 处理主动加持的buff
        if(buffActives != null){
            for(int i =0;i<buffActives.size();i++){
                buff = buffActives.get(i);
                if(buff == null) continue;

                // buff一般都有回合限制，把时间到了的清除掉
                if(BUFF_TIME_UNLIMITED != buff.getTime()){
                    int remainTime  = buff.getRemainTime();
                    if(remainTime > 0)  {
                        buff.setRemainTime(remainTime--);
                    }else{
                        buffActives.remove(i);
                    }
                }

                // 处理可以持续叠加的buff
                if(BUFF_TYPE_LAST != buff.getBuff_type())continue;
                buff.setDuration(buff.getDuration()+1);
            }
        }
    }
}
