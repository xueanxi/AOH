package com.game.xianxue.ashesofhistory.utils;

import android.content.Context;
import android.util.Xml;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.skill.SkillBase;
import com.game.xianxue.ashesofhistory.model.constant.ConstantColumn.BasePersonColumn;
import com.game.xianxue.ashesofhistory.model.constant.ConstantColumn.SkillColumn;
import com.game.xianxue.ashesofhistory.model.person.BasePerson;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by user on 5/26/17.
 */
public class XmlUtils {
    private static final String TAG = "XmlUtils";
    private static final String DEFAULT_PAGE_TAG = "all_character.xml";
    private static final String SKILL_PAGE_TAG = "all_skills.xml";

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
        SimpleLog.logd(TAG, "解析所有人物使用的时间:" + (System.currentTimeMillis() - startTime));
        return persons;
    }


    /**
     * 从 xml 文件 解析 技能出来
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ArrayList<SkillBase> getAllSkill(Context context) throws Exception {
        SkillBase skill = null;
        ArrayList<SkillBase> skillLists = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(context.getAssets().open(SKILL_PAGE_TAG), "UTF-8");
        int event = pullParser.getEventType();// 觸發第一個事件
        long startTime = System.currentTimeMillis();

        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    skillLists = new ArrayList<SkillBase>();
                    break;
                case XmlPullParser.START_TAG:
                    String tag = pullParser.getName();
                    if ("item".equals(tag)) {
                        skill = new SkillBase();
                    } else if (SkillColumn.skillId.equals(tag)) {
                        skill.setSkillId(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.name.equals(tag)) {
                        skill.setName(pullParser.nextText());
                    } else if (SkillColumn.introduce.equals(tag)) {
                        skill.setIntroduce(pullParser.nextText());
                    } else if (SkillColumn.naturetype.equals(tag)) {
                        skill.setNaturetype(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.skillType.equals(tag)) {
                        skill.setSkillType(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.accuracyRate.equals(tag)) {
                        skill.setAccuracyRate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.effectRate.equals(tag)) {
                        skill.setEffectRate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.cdTime.equals(tag)) {
                        skill.setCdTime(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.time.equals(tag)) {
                        skill.setTime(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.range.equals(tag)) {
                        skill.setRange(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.effectNumber.equals(tag)) {
                        skill.setEffectNumber(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.level.equals(tag)) {
                        skill.setLevel(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.effectUp.equals(tag)) {
                        skill.setEffectUp(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.effectCamp.equals(tag)) {
                        skill.setEffectCamp(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.effectTarget.equals(tag)) {
                        skill.setEffectTarget(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.damageType.equals(tag)) {
                        skill.setDamageType(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.damageConstant.equals(tag)) {
                        skill.setDamageConstant(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.damageFluctuate.equals(tag)) {
                        skill.setDamageFluctuate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.assisteffect.equals(tag)) {
                        skill.setAssisteffect(pullParser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(pullParser.getName())) {
                        skillLists.add(skill);
                        skill = null;
                    }
                    break;
            }
            event = pullParser.next();
        }
        SimpleLog.logd(TAG, "解析所有技能使用的时间:" + (System.currentTimeMillis() - startTime));
        return skillLists;
    }
}


