package com.game.xianxue.ashesofhistory.utils;

import android.content.Context;
import android.util.Xml;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.model.constant.ConstantColumn.BasePersonColumn;
import com.game.xianxue.ashesofhistory.model.person.BasePerson;

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
    public static ArrayList<BasePerson> getAllCharacter(Context context) throws Exception {
        BasePerson person = null;
        ArrayList<BasePerson> persons = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(context.getAssets().open(DEFAULT_PAGE_TAG), "UTF-8");
        int event = pullParser.getEventType();// 觸發第一個事件
        long startTime = System.currentTimeMillis();

        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    persons = new ArrayList<BasePerson>();
                    break;
                case XmlPullParser.START_TAG:
                    String tag = pullParser.getName();
                    if ("item".equals(tag)) {
                        person = new BasePerson();
                    } else if (BasePersonColumn.name.equals(tag)) {
                        person.setName(pullParser.nextText());
                    } else if (BasePersonColumn.name2.equals(tag)) {
                        person.setName2(pullParser.nextText());
                    } else if (BasePersonColumn.personId.equals(tag)) {
                        person.setPsersonId(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.sexuality.equals(tag)) {
                        person.setSexuality(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.aptitude.equals(tag)) {
                        person.setAptitude(Float.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.strength_Raw.equals(tag)) {
                        person.setStrength_Raw(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.intellect_Raw.equals(tag)) {
                        person.setIntellect_Raw(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.physique_Raw.equals(tag)) {
                        person.setPhysique_Raw(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.dexterity_Raw.equals(tag)) {
                        person.setDexterity_Raw(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.spirit_Raw.equals(tag)) {
                        person.setSpirit_Raw(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.luck_Raw.equals(tag)) {
                        person.setLuck_Raw(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.fascination_Raw.equals(tag)) {
                        person.setFascination_Raw(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.skill_lists_Raw.equals(tag)) {
                        person.setSkillLists(pullParser.nextText());
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
        SimpleLog.logd(TAG, "anxi 解析所有人物使用的时间:" + (System.currentTimeMillis() - startTime));
        return persons;
    }
}


