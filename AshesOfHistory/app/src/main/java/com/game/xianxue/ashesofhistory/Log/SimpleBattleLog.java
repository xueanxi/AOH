package com.game.xianxue.ashesofhistory.Log;

import android.util.Log;

/**
 * Created by user on 5/24/17.
 */
public class SimpleBattleLog {
    static final String TAG = "SimpleBattleLog";

    public static void log(String tag, String content) {
        Log.d(TAG,  " " + content);
    }
}
