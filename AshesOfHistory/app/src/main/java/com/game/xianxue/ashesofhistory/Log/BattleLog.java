package com.game.xianxue.ashesofhistory.Log;

import android.util.Log;

/**
 * Created by user on 5/24/17.
 */
public class BattleLog {
    static final String TAG = "BattleLog";

    public static void log(String tag, String content) {
        Log.d(TAG,  " " + content);
    }
}
