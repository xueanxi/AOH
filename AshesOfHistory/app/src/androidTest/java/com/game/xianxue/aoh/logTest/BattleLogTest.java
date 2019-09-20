package com.game.xianxue.aoh.logTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.game.xianxue.aoh.Log.BattleLog;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BattleLogTest {

    private static final String TAG = "BattleLogTest";
    Context mContext;

    public void init() {
        mContext = getContext();
    }

    public Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    /**
     * 测试战斗日志
     */
    @Test
    public void TestBattleLogEnable() {
        init();
        BattleLog.log(BattleLog.TAG_AV,"av");
        BattleLog.log(BattleLog.TAG_BF,"bf");
        BattleLog.log(BattleLog.TAG_NA,"na");
        BattleLog.log(BattleLog.TAG_SA,"sa");
        BattleLog.log(BattleLog.TAG_RH,"rh");

        waitFinish();
    }

    private void waitFinish(){
        try {
            Thread.sleep(999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
