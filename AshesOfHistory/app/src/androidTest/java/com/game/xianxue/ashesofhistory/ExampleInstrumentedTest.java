package com.game.xianxue.ashesofhistory;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context mContext;

    public void init(){
        mContext = getContext();
    }

    public Context getContext(){
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testQQ(){

    }
}
