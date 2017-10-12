package com.game.xianxue.ashesofhistory.databaseTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.game.xianxue.ashesofhistory.database.PersonDataManager;
import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.game.model.person.NormalPerson;
import com.game.xianxue.ashesofhistory.utils.XmlUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PersonTest {
    private static final String TAG = "PersonTest";
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
    public void TestGetNormalPerson() {
        init();
        BasePerson p1 = PersonDataManager.getPersonFromDataBaseByPinyin("zhangfei");
        NormalPerson n1 = new NormalPerson(p1,10);
        Log.d(TAG,"TestGetNormalPerson() : "+n1.display());
        n1.showSkill();

        BasePerson p2 = PersonDataManager.getPersonFromDataBaseByPinyin("lvbu");
        NormalPerson n2 = new NormalPerson(p2,10);
        Log.d(TAG,"TestGetNormalPerson() : "+n2.display());
        n2.showSkill();
    }
    /**
     * 测试XML解析所有节能
     */
    @Test
    public void Test1() {
        init();
        try {
            XmlUtils.getAllCharacter(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
