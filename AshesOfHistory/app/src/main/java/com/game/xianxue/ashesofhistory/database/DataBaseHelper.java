package com.game.xianxue.ashesofhistory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.game.xianxue.ashesofhistory.App;
import com.game.xianxue.ashesofhistory.constant.Constant;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBase;
import com.game.xianxue.ashesofhistory.game.model.lineup.LineUpBase;
import com.game.xianxue.ashesofhistory.game.skill.SkillBase;
import com.game.xianxue.ashesofhistory.game.model.constant.ConstantColumn.*;
import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.utils.XmlUtils;

import java.util.ArrayList;


/**
 * Created by user on 5/25/17.
 * <p>
 * <p>
 * 一些语句：
 * select * from calc_history;
 * select * from calc_history order by _id desc  ;
 * select name from calc_history group by calc_value  having count(*)>1  ;
 * select * from calc_history  limit 5 offset 3  ;
 * select * from calc_history limit 3,5 ;
 * insert into calc_history (calc_value) values('1+1=2') ;
 * update calc_history set calc_value=‘2+2=4‘ where _id=1 ;
 * delete from calc_history where id=1;
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DataBaseHelper";

    private static Context mContext = App.getInstance().getApplicationContext();
    private static final String DB_NAME = Constant.GAME_NAME + ".db";
    private static final int DB_VERSION = 1;

    public DataBaseHelper() {
        super(mContext, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, " onCreate");
        createTableBuff(db);
        createTableLineUp(db);
        createTableSkill(db);
        createTablePerson(db);
    }

    /**
     * 创建一张 阵型 的表格
     *
     * @param db
     */
    private void createTableLineUp(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LineUpColumn.tableName + " ( "
                + LineUpColumn.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LineUpColumn.lineup_id + " INTEGER,"
                + LineUpColumn.name + " TEXT,"
                + LineUpColumn.introduce + " TEXT,"
                + LineUpColumn.max_person + " INTEGER,"
                + LineUpColumn.lineup_json + " TEXT)");
        insertLineUpData(db);
    }

    /**
     * 插入阵型数据到数据库
     * @param db
     */
    private void insertLineUpData(SQLiteDatabase db) {
        // 进行批处理
        ArrayList<LineUpBase> lists = null;
        db.beginTransaction();
        try {
            lists = XmlUtils.getAllLineUp(mContext);
            if (lists == null) return;
        } catch (Exception e) {
            Log.d(TAG, " Fail !!! Exception e:" + e);
            e.printStackTrace();
        }

        for (LineUpBase data : lists) {
            db.execSQL(LineUpDataManager.getInsertString(data));
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        lists.clear();
        lists = null;
    }

    /**
     * 创建一张Buff的表
     *
     * @param db
     */
    private void createTableBuff(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BuffColumn.tableName + " ( "
                + BuffColumn.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BuffColumn.buff_id + " INTEGER,"
                + BuffColumn.name + " TEXT,"
                + BuffColumn.introduce + " TEXT,"
                + BuffColumn.buff_type + " INTEGER,"
                + BuffColumn.buff_nature + " INTEGER,"
                + BuffColumn.buff_constant + " INTEGER,"
                + BuffColumn.buff_fluctuate + " REAL,"
                + BuffColumn.time + " INTEGER,"
                + BuffColumn.range + " INTEGER,"
                + BuffColumn.constant_up + " REAL,"
                + BuffColumn.fluctuate_up + " REAL)");
        insertBuffData(db);
    }

    private void insertBuffData(SQLiteDatabase db) {
        // 进行批处理
        ArrayList<BuffBase> lists = null;
        db.beginTransaction();
        try {
            lists = XmlUtils.getAllBuff(mContext);
            if (lists == null) return;
        } catch (Exception e) {
            Log.d(TAG, " Fail !!! Exception e:" + e);
            e.printStackTrace();
        }

        for (BuffBase data : lists) {
            db.execSQL(BuffDataManager.getInsertString(data));
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        lists.clear();
        lists = null;
    }

    /**
     * 创建一个游戏所有技能的表格
     *
     * @param db
     */
    public void createTableSkill(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SkillColumn.tableName + " ( "
                + SkillColumn.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SkillColumn.skillId + " INTEGER,"
                + SkillColumn.name + " TEXT,"
                + SkillColumn.introduce + " TEXT,"
                + SkillColumn.skillType + " INTEGER,"
                + SkillColumn.naturetype + " INTEGER,"
                + SkillColumn.accuracyRate + " REAL,"
                + SkillColumn.effectRate + " REAL,"
                + SkillColumn.cdTime + " INTEGER,"
                + SkillColumn.time + " INTEGER,"
                + SkillColumn.range + " INTEGER,"
                + SkillColumn.effectNumber + " INTEGER,"
                + SkillColumn.level + " INTEGER,"
                + SkillColumn.effectUp + " REAL,"
                + SkillColumn.damageType + " INTEGER,"
                + SkillColumn.effectCamp + " INTEGER,"
                + SkillColumn.damageConstant + " INTEGER,"
                + SkillColumn.damageFluctuate + " REAL,"
                + SkillColumn.assisteffect + " TEXT,"
                + SkillColumn.effectTarget + " INTEGER)");
        insertSkillData(db);
    }

    /**
     * 插入所有技能数据
     *
     * @param db
     */
    private void insertSkillData(SQLiteDatabase db) {
        // 进行批处理
        ArrayList<SkillBase> lists = null;
        db.beginTransaction();
        try {
            lists = XmlUtils.getAllSkill(mContext);
            if (lists == null) return;
        } catch (Exception e) {
            Log.d(TAG, "anxi Fail !!! Exception e:" + e);
            e.printStackTrace();
        }

        for (SkillBase skill : lists) {
            db.execSQL(SkillDataManager.getInsertString(skill));
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        lists.clear();
        lists = null;
    }

    /**
     * 创建 人物 相关的表格
     *
     * @param db
     */
    private void createTablePerson(SQLiteDatabase db) {
        //创建一个游戏所有人物数据表
        db.execSQL("CREATE TABLE " + BasePersonColumn.tableName
                + " (" + BasePersonColumn.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BasePersonColumn.personId + " INTEGER,"
                + BasePersonColumn.name + " TEXT,"
                + BasePersonColumn.name2 + " TEXT,"
                + BasePersonColumn.sexuality + " INTEGER,"
                + BasePersonColumn.aptitude + " REAL,"
                + BasePersonColumn.strength_Raw + " INTEGER,"
                + BasePersonColumn.intellect_Raw + " INTEGER,"
                + BasePersonColumn.dexterity_Raw + " INTEGER,"
                + BasePersonColumn.physique_Raw + " INTEGER,"
                + BasePersonColumn.spirit_Raw + " INTEGER,"
                + BasePersonColumn.luck_Raw + " INTEGER,"
                + BasePersonColumn.fascination_Raw + " INTEGER,"
                + BasePersonColumn.skill_lists_Raw + " TEXT)");
        insertBasePersonData(db);

        // 创建一个玩家拥有的 人物 数据表
        db.execSQL("CREATE TABLE " + OwnPersonColumn.tableName
                + " (" + BasePersonColumn.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"

                + BasePersonColumn.personId + " INTEGER,"
                + BasePersonColumn.name + " TEXT,"
                + BasePersonColumn.name2 + " TEXT,"
                + BasePersonColumn.sexuality + " INTEGER,"
                + BasePersonColumn.aptitude + " REAL,"
                + BasePersonColumn.strength_Raw + " INTEGER,"
                + BasePersonColumn.intellect_Raw + " INTEGER,"
                + BasePersonColumn.dexterity_Raw + " INTEGER,"
                + BasePersonColumn.physique_Raw + " INTEGER,"
                + BasePersonColumn.spirit_Raw + " INTEGER,"
                + BasePersonColumn.luck_Raw + " INTEGER,"
                + BasePersonColumn.fascination_Raw + " INTEGER,"
                + BasePersonColumn.skill_lists_Raw + " TEXT,"

                + OwnPersonColumn.level + " TEXT,"
                + OwnPersonColumn.weaponId + " TEXT,"
                + OwnPersonColumn.equipId + " TEXT,"
                + OwnPersonColumn.treasureId + " TEXT,"
                + OwnPersonColumn.riderId + " TEXT,"
                + OwnPersonColumn.experience + " TEXT,"
                + OwnPersonColumn.skill_lists + " TEXT)");
    }

    /**
     * 插入游戏初始人物数据
     * INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
     */
    private void insertBasePersonData(SQLiteDatabase db) {
        // 进行批处理
        ArrayList<BasePerson> lists = null;
        db.beginTransaction();
        try {
            lists = XmlUtils.getAllCharacter(mContext);
            if (lists == null) return;
        } catch (Exception e) {
            Log.d(TAG, "anxi Fail !!! Exception e:" + e);
            e.printStackTrace();
        }

        for (BasePerson player : lists) {
            db.execSQL(BasePersonManager.getInsertString(player));
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        lists.clear();
        lists = null;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}