package com.game.xianxue.ashesofhistory.utils;

import android.content.Context;
import android.util.Log;

import com.game.xianxue.ashesofhistory.R;

/**
 * Created by user on 5/24/17.
 */
public class LogUtils {
    public static void Log(Context context){
        String str = context.getString(R.string.app_name);
        Log.d("anxiii","str = "+str);
    }
}
