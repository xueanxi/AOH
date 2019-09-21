package com.game.xianxue.aoh.utils;

import android.content.Context;
import android.util.Xml;

import com.game.xianxue.aoh.Log.LogUtils;
import com.game.xianxue.aoh.game.model.lineup.FormationBase;
import com.game.xianxue.aoh.game.model.buff.BuffBase;
import com.game.xianxue.aoh.game.model.lineup.FormationUnitBase;
import com.game.xianxue.aoh.game.skill.SkillBase;
import com.game.xianxue.aoh.game.model.constant.ConstantColumn.BasePersonColumn;
import com.game.xianxue.aoh.game.model.constant.ConstantColumn.SkillColumn;
import com.game.xianxue.aoh.game.model.constant.ConstantColumn.BuffColumn;
import com.game.xianxue.aoh.game.model.constant.ConstantColumn.FormationColumn;
import com.game.xianxue.aoh.game.model.person.BasePerson;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/26/17.
 */
public class XmlUtils {
    private static final String TAG = "XmlUtils";
    private static final String DEFAULT_PAGE_TAG = "all_character.xml";
    private static final String SKILL_PAGE_TAG = "all_skills.xml";
    private static final String BUFF_PAGE_TAG = "all_buff.xml";
    private static final String LINE_UP_PAGE_TAG = "all_formation.xml";

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
                    } else if (BasePersonColumn.namePinyin.equals(tag)) {
                        person.setNamePinyin(pullParser.nextText());
                    } else if (BasePersonColumn.personId.equals(tag)) {
                        person.setPersonId(Integer.valueOf(pullParser.nextText()));
                    } else if (BasePersonColumn.sexuality.equals(tag)) {
                        person.setSexuality(Integer.valueOf(pullParser.nextText()));
                    }  else if (BasePersonColumn.strength_Raw.equals(tag)) {
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
                        person.setSkillStrings(pullParser.nextText());
                    } else if (BasePersonColumn.lead_buff_id.equals(tag)) {
                        person.setLeadSkillId(Integer.valueOf(pullParser.nextText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(pullParser.getName())) {
                        LogUtils.d(TAG,"person : "+person);
                        persons.add(person);
                        person = null;
                    }
                    break;
            }
            event = pullParser.next();
        }
        LogUtils.d(TAG, "解析所有人物使用的时间:" + (System.currentTimeMillis() - startTime));
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
        int event = pullParser.getEventType();// 触发第一个事件
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
                        skill.setSkillNature(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.skillType.equals(tag)) {
                        skill.setSkillType(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.accuracyRate.equals(tag)) {
                        skill.setAccuracyRate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.effectRate.equals(tag)) {
                        skill.setEffectRate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.cdTime.equals(tag)) {
                        skill.setCdTime(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.range.equals(tag)) {
                        skill.setRange(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.effectNumber.equals(tag)) {
                        skill.setEffectNumber(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.criteRate.equals(tag)) {
                        skill.setCriteRate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.damagePenetrate.equals(tag)) {
                        skill.setDamagePenetrate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.effectCamp.equals(tag)) {
                        skill.setEffectCamp(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.effectTarget.equals(tag)) {
                        skill.setEffectTarget(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.damageType.equals(tag)) {
                        skill.setDamageType(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.damageConstant.equals(tag)) {
                        skill.setDamageConstant(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.damageFluctuate.equals(tag)) {
                        skill.setDamageFluctuate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.assisteffect.equals(tag)) {
                        skill.setAssistEffect(Integer.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.levelUpFluctuate.equals(tag)) {
                        skill.setLevelUpFluctuate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.levelUpConstant.equals(tag)) {
                        skill.setLevelUpConstant(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.levelUpEffectRate.equals(tag)) {
                        skill.setLevelUpEffectRate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.levelUpNumber.equals(tag)) {
                        skill.setLevelUpNumber(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.levelUpPenetrate.equals(tag)) {
                        skill.setLevelUpPenetrate(Float.valueOf(pullParser.nextText()));
                    } else if (SkillColumn.levelUpRange.equals(tag)) {
                        skill.setLevelUpRange(Float.valueOf(pullParser.nextText()));
                    }else if (SkillColumn.levelUpCDTime.equals(tag)) {
                        skill.setLevelUpCDTime(Float.valueOf(pullParser.nextText()));
                    }else if (SkillColumn.levelUpAttackTime.equals(tag)) {
                        skill.setLevelUpAttackTime(Float.valueOf(pullParser.nextText()));
                    }else if (SkillColumn.attackTime.equals(tag)) {
                        skill.setAttackTime(Integer.valueOf(pullParser.nextText()));
                    }else if (SkillColumn.attackTimeDamageUp.equals(tag)) {
                        skill.setAttackTimeDamageUp(Float.valueOf(pullParser.nextText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(pullParser.getName())) {
                        LogUtils.d(TAG,"skill "+skill);
                        skillLists.add(skill);
                        skill = null;
                    }
                    break;
            }
            event = pullParser.next();
        }
        LogUtils.d(TAG, "解析所有技能使用的时间:" + (System.currentTimeMillis() - startTime));
        return skillLists;
    }


    /**
     * 从 xml 文件 解析 技能出来
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static ArrayList<BuffBase> getAllBuff(Context context) throws Exception {
        BuffBase buff = null;
        ArrayList<BuffBase> buffLists = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(context.getAssets().open(BUFF_PAGE_TAG), "UTF-8");
        int event = pullParser.getEventType();// 觸發第一個事件
        long startTime = System.currentTimeMillis();

        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    buffLists = new ArrayList<BuffBase>();
                    break;
                case XmlPullParser.START_TAG:
                    String tag = pullParser.getName();
                    if ("item".equals(tag)) {
                        buff = new BuffBase();
                    } else if (BuffColumn.buff_id.equals(tag)) {
                        buff.setBuffId(Integer.valueOf(pullParser.nextText()));
                    } else if (BuffColumn.name.equals(tag)) {
                        buff.setName(pullParser.nextText());
                    } else if (BuffColumn.introduce.equals(tag)) {
                        buff.setIntroduce(pullParser.nextText());
                    } else if (BuffColumn.buff_effect.equals(tag)) {
                        buff.setSbuff_effect(pullParser.nextText());
                    } else if (BuffColumn.superposition.equals(tag)) {
                        buff.setSuperposition(Integer.valueOf(pullParser.nextText()));
                    } else if (BuffColumn.buff_nature.equals(tag)) {
                        buff.setBuff_nature(Integer.valueOf(pullParser.nextText()));
                    } else if (BuffColumn.buff_constant.equals(tag)) {
                        buff.setSbuff_constant(pullParser.nextText());
                    } else if (BuffColumn.buff_fluctuate.equals(tag)) {
                        buff.setSbuff_fluctuate(pullParser.nextText());
                    } else if (BuffColumn.time.equals(tag)) {
                        buff.setTime(Integer.valueOf(pullParser.nextText()));
                    } else if (BuffColumn.range.equals(tag)) {
                        buff.setRange(Integer.valueOf(pullParser.nextText()));
                    } else if (BuffColumn.level_up_constant.equals(tag)) {
                        buff.setSlevel_up_constant(pullParser.nextText());
                    } else if (BuffColumn.level_up_fluctuate.equals(tag)) {
                        buff.setSlevel_up_fluctuate(pullParser.nextText());
                    } else if (BuffColumn.level_up_range.equals(tag)) {
                        buff.setLevel_up_range(Float.valueOf(pullParser.nextText()));
                    } else if (BuffColumn.level_up_time.equals(tag)) {
                        buff.setLevel_up_time(Float.valueOf(pullParser.nextText()));
                    }else if (BuffColumn.damage_type.equals(tag)) {
                        buff.setDamage_type(Integer.valueOf(pullParser.nextText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(pullParser.getName())) {
                        buffLists.add(buff);
                        buff = null;
                    }
                    break;
            }
            event = pullParser.next();
        }
        LogUtils.d(TAG, "解析所有buff使用的时间:" + (System.currentTimeMillis() - startTime));
        return buffLists;
    }


    /**
     * 从 xml 文件 解析 所有阵型
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static List<FormationBase> getAllFormations(Context context) throws Exception {

        List<FormationBase> lineupBaseList = null;
        FormationBase lineupBase = null;
        List<FormationUnitBase> unitList = null;
        FormationUnitBase unit = null;

        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(context.getAssets().open(LINE_UP_PAGE_TAG), "UTF-8");
        int event = pullParser.getEventType();// 觸發第一個事件
        long startTime = System.currentTimeMillis();

        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    lineupBaseList = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    String tag = pullParser.getName();
                    if ("item".equals(tag)) {
                        lineupBase = new FormationBase();
                        unitList = new ArrayList<FormationUnitBase>();
                    } else if (FormationColumn.formation_id.equals(tag)) {
                        lineupBase.setFormation_id(Integer.valueOf(pullParser.nextText()));
                    } else if (FormationColumn.name.equals(tag)) {
                        lineupBase.setName(pullParser.nextText());
                    } else if (FormationColumn.introduce.equals(tag)) {
                        lineupBase.setIntroduce(pullParser.nextText());
                    } else if (FormationColumn.max_person.equals(tag)) {
                        lineupBase.setMaxPerson(Integer.valueOf(pullParser.nextText()));
                    } else if (FormationColumn.unit.equals(tag)) {
                        unit = new FormationUnitBase(pullParser.nextText());
                        unitList.add(unit);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(pullParser.getName())) {
                        //lineupBase.setUnitList(unitList);
                        lineupBase.setFormationsJsonString(FormationUnitBase.toJsonString(unitList));
                        lineupBaseList.add(lineupBase);
                        unitList = null;
                        ShowUtils.showLists(TAG,lineupBaseList);
                        lineupBase = null;
                    }
                    break;
            }
            event = pullParser.next();
        }
        LogUtils.d(TAG, "解析所有阵型使用的时间:" + (System.currentTimeMillis() - startTime));
        return lineupBaseList;
    }
}


