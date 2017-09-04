package com.game.xianxue.ashesofhistory.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.skill.SkillBase;
import com.game.xianxue.ashesofhistory.game.model.constant.ConstantColumn.SkillColumn;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import java.util.ArrayList;

/**
 * Created by user on 9/1/17.
 */

public class SkillDataManager {

    private static final String TAG = "SkillDataManager";


    /**
     * 把对象转化为可以插入数据库的SQL语句
     *
     * @param skill
     * @return
     */
    public static String getInsertString(SkillBase skill) {
        String format = "INSERT INTO " + SkillColumn.tableName + " ("
                + SkillColumn.skillId + " ,"
                + SkillColumn.name + " ,"
                + SkillColumn.introduce + " ,"
                + SkillColumn.naturetype + " ,"
                + SkillColumn.skillType + " ,"
                + SkillColumn.accuracyRate + " ,"
                + SkillColumn.effectRate + " ,"
                + SkillColumn.cdTime + " ,"
                + SkillColumn.time + " ,"
                + SkillColumn.range + " ,"
                + SkillColumn.effectNumber + " ,"
                + SkillColumn.level + " ,"
                + SkillColumn.effectUp + " ,"
                + SkillColumn.effectCamp + " ,"
                + SkillColumn.effectTarget + " ,"
                + SkillColumn.damageType + " ,"
                + SkillColumn.damageConstant + " ,"
                + SkillColumn.damageFluctuate + " ,"
                + SkillColumn.assisteffect + " )"
                + " VALUES ("
                + " '%d','%s','%s','%d','%d','%f','%f','%d','%d','%d','%d','%d','%f','%d','%d','%d','%d','%f','%s'"
                + " )";

        return String.format(format,
                skill.getSkillId(),
                skill.getName(),
                skill.getIntroduce(),
                skill.getNaturetype(),
                skill.getSkillType(),
                skill.getAccuracyRate(),
                skill.getEffectRate(),
                skill.getCdTime(),
                skill.getTime(),
                skill.getRange(),
                skill.getEffectNumber(),
                skill.getLevel(),
                skill.getEffectUp(),
                skill.getEffectCamp(),
                skill.getEffectTarget(),
                skill.getDamageType(),
                skill.getDamageConstant(),
                skill.getDamageFluctuate(),
                skill.getAssisteffect()
        );
    }

    public static ArrayList<SkillBase> getAllSkillFromDataBase() {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + SkillColumn.tableName;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        ArrayList<SkillBase> arrays = cursorToSkills(cursor);
        cursor.close();
        db.close();
        ShowUtils.showArrays(TAG, arrays);
        SimpleLog.logd(TAG, "加载技能完毕！！！");
        return arrays;
    }

    public static SkillBase getSkillFromDataBaseById(int id) {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + SkillColumn.tableName + " WHERE " + SkillColumn.skillId + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        SkillBase skill = cursorToSkill(cursor);
        cursor.close();
        db.close();
        SimpleLog.logd(TAG,skill.toString());
        return skill;
    }

    /**
     * 返回一群技能对象
     *
     * @param c
     * @return
     */
    private static ArrayList<SkillBase> cursorToSkills(Cursor c) {
        ArrayList<SkillBase> lists = null;
        SkillBase skill = null;
        if (c == null) return lists;
        if (c.getCount() > 0) {
            lists = new ArrayList<SkillBase>();
            while (c.moveToNext()) {
                skill = new SkillBase();
                skill.setSkillId(c.getInt(c.getColumnIndex(SkillColumn.skillId)));
                skill.setName(c.getString(c.getColumnIndex(SkillColumn.name)));
                skill.setIntroduce(c.getString(c.getColumnIndex(SkillColumn.introduce)));
                skill.setNaturetype(c.getInt(c.getColumnIndex(SkillColumn.naturetype)));
                skill.setSkillType(c.getInt(c.getColumnIndex(SkillColumn.skillType)));
                skill.setAccuracyRate(c.getFloat(c.getColumnIndex(SkillColumn.accuracyRate)));
                skill.setEffectRate(c.getFloat(c.getColumnIndex(SkillColumn.effectRate)));
                skill.setCdTime(c.getInt(c.getColumnIndex(SkillColumn.cdTime)));
                skill.setTime(c.getInt(c.getColumnIndex(SkillColumn.time)));
                skill.setRange(c.getInt(c.getColumnIndex(SkillColumn.range)));
                skill.setEffectNumber(c.getInt(c.getColumnIndex(SkillColumn.effectNumber)));
                skill.setLevel(c.getInt(c.getColumnIndex(SkillColumn.level)));
                skill.setEffectUp(c.getFloat(c.getColumnIndex(SkillColumn.effectUp)));
                skill.setEffectCamp(c.getInt(c.getColumnIndex(SkillColumn.effectCamp)));
                skill.setEffectTarget(c.getInt(c.getColumnIndex(SkillColumn.effectTarget)));
                skill.setDamageType(c.getInt(c.getColumnIndex(SkillColumn.damageType)));
                skill.setDamageConstant(c.getInt(c.getColumnIndex(SkillColumn.damageConstant)));
                skill.setDamageFluctuate(c.getFloat(c.getColumnIndex(SkillColumn.damageFluctuate)));
                skill.setAssisteffect(c.getString(c.getColumnIndex(SkillColumn.assisteffect)));
                lists.add(skill);
            }
        }
        return lists;
    }

    /**
     * 返回一群武将对象
     *
     * @param c
     * @return
     */
    private static SkillBase cursorToSkill(Cursor c) {
        SkillBase skill = null;
        if (c == null) return skill;
        if (c.getCount() <= 0) {
            return null;
        }
        skill = new SkillBase();
        c.moveToNext();
        skill.setSkillId(c.getInt(c.getColumnIndex(SkillColumn.skillId)));
        skill.setName(c.getString(c.getColumnIndex(SkillColumn.name)));
        skill.setIntroduce(c.getString(c.getColumnIndex(SkillColumn.introduce)));
        skill.setNaturetype(c.getInt(c.getColumnIndex(SkillColumn.naturetype)));
        skill.setSkillType(c.getInt(c.getColumnIndex(SkillColumn.skillType)));
        skill.setAccuracyRate(c.getFloat(c.getColumnIndex(SkillColumn.accuracyRate)));
        skill.setEffectRate(c.getFloat(c.getColumnIndex(SkillColumn.effectRate)));
        skill.setCdTime(c.getInt(c.getColumnIndex(SkillColumn.cdTime)));
        skill.setTime(c.getInt(c.getColumnIndex(SkillColumn.time)));
        skill.setRange(c.getInt(c.getColumnIndex(SkillColumn.range)));
        skill.setEffectNumber(c.getInt(c.getColumnIndex(SkillColumn.effectNumber)));
        skill.setLevel(c.getInt(c.getColumnIndex(SkillColumn.level)));
        skill.setEffectUp(c.getFloat(c.getColumnIndex(SkillColumn.effectUp)));
        skill.setEffectCamp(c.getInt(c.getColumnIndex(SkillColumn.effectCamp)));
        skill.setEffectTarget(c.getInt(c.getColumnIndex(SkillColumn.effectTarget)));
        skill.setDamageType(c.getInt(c.getColumnIndex(SkillColumn.damageType)));
        skill.setDamageConstant(c.getInt(c.getColumnIndex(SkillColumn.damageConstant)));
        skill.setDamageFluctuate(c.getFloat(c.getColumnIndex(SkillColumn.damageFluctuate)));
        skill.setAssisteffect(c.getString(c.getColumnIndex(SkillColumn.assisteffect)));

        return skill;
    }
}
