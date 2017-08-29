package com.game.xianxue.ashesofhistory.utils;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;

import java.util.ArrayList;

/**
 * Created by user on 8/29/17.
 */

public class ShowUtils {
    private static final String TAG = "ShowUtils";

    public static void showArrays(ArrayList lists) {
        for (Object data : lists) {
            SimpleLog.logd(TAG, data.toString());
        }
    }
}
