package com.game.xianxue.ashesofhistory.Log;

import android.util.Log;

/**
 * Created by user on 5/24/17.
 */
public class BuffLog {
    static final String TAG = "=BuffLog";
    static boolean isSimpleMode = true;

    public static void log(String content) {
        if(!isSimpleMode){
            System.out.println(content);
        }
        Log.d(TAG,  " " + content);
    }
}
