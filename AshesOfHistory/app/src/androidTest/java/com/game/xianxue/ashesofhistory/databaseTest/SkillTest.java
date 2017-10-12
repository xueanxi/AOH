package com.game.xianxue.ashesofhistory.databaseTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.game.xianxue.ashesofhistory.database.SkillDataManager;
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
    private static final String TAG = "DataBaseTest";
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


}
