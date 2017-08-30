package com.game.xianxue.ashesofhistory.utils;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;

import java.util.ArrayList;

/**
 * Created by user on 8/29/17.
 */

public class ShowUtils {
    private static final String TAG = "ShowUtils";

    public static void showArrays(String tag, ArrayList lists) {
        if (lists == null) SimpleLog.logd(tag, "ArrayList is null !!! ");
        for (Object data : lists) {
            SimpleLog.logd(tag, data.toString());
        }
    }

    public static void showArrays(ArrayList lists) {
        if (lists == null) SimpleLog.logd(TAG, "ArrayList is null !!! ");
        for (Object data : lists) {
            SimpleLog.logd(TAG, data.toString());
        }
    }

    public static void showArraysJava(String tag, ArrayList lists) {
        if (lists == null) SimpleLog.logd(tag, "ArrayList is null !!! ");
        for (Object data : lists) {
            System.out.println(tag + " " + data.toString());
        }
    }
}
