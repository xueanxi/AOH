package com.game.xianxue.aoh.Log;

import android.util.Log;

/**
 * Created by user on 5/24/17.
 */
public class LogUtils {
    static final String TAG_LOG = "AOHLog";
    static final boolean isShowSystemLog = true;

    public static void d(String TAG, String content) {
        if (isShowSystemLog) {
            System.out.println(TAG + " " + content);
        }else{
            Log.d(TAG_LOG, TAG + " " + content);
        }
    }

    public static void e(String TAG, String content) {
        if (isShowSystemLog) {
            System.out.println(TAG + " " + content);
        }else{
            Log.e(TAG_LOG, TAG + " " + content);
        }
    }
}
