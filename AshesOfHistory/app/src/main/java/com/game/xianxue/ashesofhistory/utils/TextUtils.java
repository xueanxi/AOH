package com.game.xianxue.ashesofhistory.utils;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;

/**
 * Created by user on 9/28/17.
 */

public class TextUtils {
    private static final String TAG = "=TextUtils";

    /**
     * 把字符串转化为整形数组
     * 比如 "1,2,3,4,5,8" -> int {1,2,3,4,5,8}
     * @param data
     * @return
     */
    public static int[] getIntArrayFromString(String data) {
        String[] datas = data.split(",");
        int[] result = new int[datas.length];
        for (int i = 0; i < datas.length; i++) {
            result[i] = Integer.valueOf(datas[i]);
        }

        if (result != null && result.length > 0) {
            return result;
        } else {
            return null;
        }
    }


    public static String getStringFromIntArray(int[] data) {
        if(data == null || data.length ==0) {
            SimpleLog.loge(TAG,"Error !!! getStringFromIntArray() data is null");
            return null;
        }
        StringBuilder result = new StringBuilder();
        for(int i = 0;i<data.length;i++) {
            result.append("" + data[i] + ",");
        }
        // 去掉最后一个多余的逗号
        result.deleteCharAt(result.length()-1);

        if(result == null ){
            return null;
        }else{
            return result.toString();
        }
    }

    /**
     * 把字符串转化为浮点型数组
     * 比如 "1,2,3,4,5,8" -> int {1,2,3,4,5,8}
     * @param data
     * @return
     */
    public static float[] getFloatArrayFromString(String data) {
        String[] datas = data.split(",");
        float[] result = new float[datas.length];
        for (int i = 0; i < datas.length; i++) {
            result[i] = Float.valueOf(datas[i]);
        }

        if (result != null && result.length > 0) {
            return result;
        } else {
            return null;
        }
    }

    public static String getStringFromFloatArray(float[] data) {
        if(data == null || data.length ==0) {
            SimpleLog.loge(TAG,"Error !!! getStringFromIntArray() data is null");
            return null;
        }
        StringBuilder result = new StringBuilder();
        for(int i = 0;i<data.length;i++) {
            result.append("" + data[i] + ",");
        }
        // 去掉最后一个多余的逗号
        result.deleteCharAt(result.length()-1);

        if(result == null ){
            return null;
        }else{
            return result.toString();
        }
    }
}
