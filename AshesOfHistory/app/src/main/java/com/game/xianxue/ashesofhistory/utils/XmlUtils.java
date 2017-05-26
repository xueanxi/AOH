package com.game.xianxue.ashesofhistory.utils;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.game.xianxue.ashesofhistory.model.PlayerModel;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by user on 5/26/17.
 */
public class XmlUtils {
    private static final String TAG = "XmlUtils";
    private static final String DEFAULT_PAGE_TAG = "all_character.xml";

    /**
     * 从 xml 文件 解析 所有人物出来
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ArrayList<PlayerModel> getAllCharacter(Context context) throws Exception {
        PlayerModel person = null;
        ArrayList<PlayerModel> persons = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(context.getAssets().open(DEFAULT_PAGE_TAG), "UTF-8");
        int event = pullParser.getEventType();// 觸發第一個事件
        long startTime = System.currentTimeMillis();

        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    persons = new ArrayList<PlayerModel>();
                    break;
                case XmlPullParser.START_TAG:
                    String tag = pullParser.getName();
                    if ("item".equals(tag)) {
                        person = new PlayerModel();
                    } else if (PlayerModel.Column.name.equals(tag)) {
                        person.setName(pullParser.nextText());
                    } else if (PlayerModel.Column.character_id.equals(tag)) {
                        person.setCharacter_id(Integer.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.sexuality.equals(tag)) {
                        person.setSexuality(Integer.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.aptitude.equals(tag)) {
                        person.setAptitude(Float.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.strength_Initial.equals(tag)) {
                        person.setStrength_Initial(Integer.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.intellect_Initial.equals(tag)) {
                        person.setIntellect_Initial(Integer.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.physique_Initial.equals(tag)) {
                        person.setPhysique_Initial(Integer.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.dexterity_Initial.equals(tag)) {
                        person.setDexterity_Initial(Integer.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.spirit_Initial.equals(tag)) {
                        person.setSpirit_Initial(Integer.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.luck_Initial.equals(tag)) {
                        person.setLuck_Initial(Integer.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.fascination_Initial.equals(tag)) {
                        person.setFascination_Initial(Integer.valueOf(pullParser.nextText()));
                    } else if (PlayerModel.Column.skill_lists.equals(tag)) {
                        //person.setSkillLists(pullParser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(pullParser.getName())) {
                        persons.add(person);
                        person = null;
                    }
                    break;
            }
            event = pullParser.next();
        }
        Log.d(TAG, "anxi 解析所有人物使用的时间:" + (System.currentTimeMillis() - startTime));
        return persons;
    }
}


