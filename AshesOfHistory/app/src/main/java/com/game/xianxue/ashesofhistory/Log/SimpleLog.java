package com.game.xianxue.ashesofhistory.Log;

import android.util.Log;

/**
 * Created by user on 5/24/17.
 */
public class SimpleLog {
    static final String TAG_LOG = "AOHLog";
    static final boolean isNeedLog = true;

    public static void logd(String TAG, String content) {
        if (isNeedLog) {
            Log.d(TAG_LOG, TAG + " " + content);
        }
    }

    public static void loge(String TAG, String content) {
        if (isNeedLog) {
            Log.e(TAG_LOG, TAG + " " + content);
        }
    }
}
