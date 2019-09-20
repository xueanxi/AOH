package com.game.xianxue.aoh.utils;

import com.game.xianxue.aoh.Log.LogUtils;

import java.util.ArrayList;

/**
 * Created by user on 8/29/17.
 */

public class ShowUtils {
    private static final String TAG = "ShowUtils";

    //=====================显示ArrayList start========================
    public static void showArrayLists(String tag, ArrayList lists) {
        if (lists == null) {
            LogUtils.d(tag, "ArrayList is null !!! ");
            return;
        }

        for(int i = 0;i<lists.size();i++){
            LogUtils.d(tag,i+":" + lists.get(i).toString());
        }
    }

    public static void showArrayLists(ArrayList lists) {
        if (lists == null) LogUtils.d(TAG, "ArrayList is null !!! ");
        for (Object data : lists) {
            LogUtils.d(TAG, data.toString());
        }
    }

    public static void showArrayListsJava(String tag, ArrayList lists) {
        if (lists == null) LogUtils.d(tag, "ArrayList is null !!! ");
        for (Object data : lists) {
            System.out.println(tag + " " + data.toString());
        }
    }
    //=====================显示ArrayList end========================




    //=====================显示数组 start========================
    public static void showArrays(String tag, int[] datas) {
        if (datas == null) LogUtils.d(tag, "ArrayList is null !!! ");
        for (Object data : datas) {
            LogUtils.d(tag, data.toString());
        }
    }
    public static void showArrays(String tag, float[] datas) {
        if (datas == null) LogUtils.d(tag, "ArrayList is null !!! ");
        for (Object data : datas) {
            LogUtils.d(tag, data.toString());
        }
    }
    public static void showArraysJAVA( int[] datas) {
        if (datas == null)return;
        for (Object data : datas) {
            System.out.print(data);
            System.out.print(",");
        }
    }
    public static void showArraysJAVA( float[] datas) {
        if (datas == null) return;
        for (Object data : datas) {
            System.out.print(data);
            System.out.print(",");
        }
    }
    //=====================显示数组 end========================
}
