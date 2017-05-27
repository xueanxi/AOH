package com.game.xianxue.ashesofhistory.utils;

import android.content.Context;

import com.game.xianxue.ashesofhistory.model.SkillModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by user on 17-1-12.
 */

public class JsonParser {

    public static final ArrayList<SkillModel> loadSkillLists(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Gson gson = new Gson();
            ArrayList<SkillModel> itemInfos =
                    gson.fromJson(br, new TypeToken<ArrayList<SkillModel>>() {
                    }.getType());
            return itemInfos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从Json 字符串 获取 对象
     * @param text
     * @return
     */
    public static final ArrayList<SkillModel> loadSkillListFromString(String text) {
        Gson gson = new Gson();
        ArrayList<SkillModel> itemInfos =
                gson.fromJson(text, new TypeToken<ArrayList<SkillModel>>() {
                }.getType());
        return itemInfos;
    }

    /**
     * 从对像 获得 Json 字符串
     * @param infos
     * @return
     */
    public static final String getJsonStringFromObject(ArrayList<SkillModel> infos) {
        Gson gson = new Gson();
        return gson.toJson(infos);
    }
}
