package com.game.xianxue.ashesofhistory.databaseTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.database.BasePersonManager;
import com.game.xianxue.ashesofhistory.model.TeamModel;
import com.game.xianxue.ashesofhistory.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.model.person.NormalPerson;
import com.game.xianxue.ashesofhistory.utils.XmlUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

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
        BasePersonManager.showAllPersonFromDataBase();
    }

    @Test
    public void TestgetPlayerByName() {
        init();
        BasePerson p1 = BasePersonManager.getPersonFromDataBaseByPinyin("guanyu");
        if(p1 == null){
            SimpleLog.loge(TAG,"TestgetPlayerByName() : p1 == null");
        }else{
            NormalPerson normalPerson = new NormalPerson(p1,5);
            SimpleLog.logd(TAG,normalPerson.toString());

            BattlePerson battlePerson = new BattlePerson(normalPerson, TeamModel.CAMP_LEFT);
            SimpleLog.logd(TAG,battlePerson.toString());
        }
    }

    @Test
    public void TestXmlUtilGetAll() {
        init();
        try {
            XmlUtils.getAllCharacter(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
