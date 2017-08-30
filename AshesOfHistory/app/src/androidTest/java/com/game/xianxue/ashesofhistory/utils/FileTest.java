package com.game.xianxue.ashesofhistory.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FileTest {
    Context mContext;

    public void init(){
        mContext = getContext();
    }

    public Context getContext(){
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testQQ(){
        FileUtils.createMyDir();

    }
}
