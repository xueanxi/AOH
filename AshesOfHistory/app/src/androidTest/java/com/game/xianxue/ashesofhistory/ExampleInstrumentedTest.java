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
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example_test.user.myapplication_tests", appContext.getPackageName());
    }


    @Test
    public void testQQ(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        String app = appContext.getString(R.string.app_name);
        Log.d("anxii","app = "+app);
        System.out.println("app = "+app);
        //assertEquals("My Application_tests", app);
    }
}
