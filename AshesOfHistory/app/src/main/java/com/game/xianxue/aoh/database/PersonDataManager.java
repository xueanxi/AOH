package com.game.xianxue.aoh.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.game.xianxue.aoh.game.model.constant.ConstantColumn.BasePersonColumn;
import com.game.xianxue.aoh.game.model.person.BasePerson;
import com.game.xianxue.aoh.utils.ShowUtils;

import java.util.ArrayList;

/**
 * 基础人物数据管理类
 */
public class PersonDataManager {

    private static final String TAG = "PersonDataManager";

    /**
     * 把对象转化为可以插入数据库的SQL语句
     *
     * @param person
     * @return
     */
    public static String getInsertString(BasePerson person) {
        String format = "INSERT INTO " + BasePersonColumn.tableName + " ("
                + BasePersonColumn.personId + " ,"
                + BasePersonColumn.name + " ,"
                + BasePersonColumn.namePinyin + " ,"
                + BasePersonColumn.sexuality + " ,"
                + BasePersonColumn.strength_Raw + " ,"
                + BasePersonColumn.intellect_Raw + " ,"
                + BasePersonColumn.dexterity_Raw + " ,"
                + BasePersonColumn.physique_Raw + " ,"
                + BasePersonColumn.spirit_Raw + " ,"
                + BasePersonColumn.luck_Raw + " ,"
                + BasePersonColumn.fascination_Raw + " ,"
                + BasePersonColumn.lead_buff_id + " ,"
                + BasePersonColumn.skill_lists_Raw + " )"
                + " VALUES ("
                + " '%d','%s','%s','%d','%d','%d','%d','%d','%d','%d','%d','%d','%s'"
                + " )";
        return String.format(format,
                person.getPersonId(),
                person.getName(),
                person.getNamePinyin(),
                person.getSexuality(),
                person.getStrength_Raw(),
                person.getIntellect_Raw(),
                person.getDexterity_Raw(),
                person.getPhysique_Raw(),
                person.getSpirit_Raw(),
                person.getLuck_Raw(),
                person.getFascination_Raw(),
                person.getLeadSkillId(),
                person.getSkillStrings());
    }

    public static ArrayList<BasePerson> getAllPersonFromDataBase() {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + BasePersonColumn.tableName;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        ArrayList<BasePerson> arrays = cursorToPersons(cursor);
        return arrays;
    }

    public static void showAllPersonFromDataBase() {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + BasePersonColumn.tableName;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return;
        ArrayList<BasePerson> arrays = cursorToPersons(cursor);
        ShowUtils.showArrayLists(arrays);
    }

    /**
     * SELECT 列名称 FROM 表名称 WHERE 列 运算符 值
     */
    public static BasePerson getCharacterFromDataBaseByName(String name) {
        DataBaseHelper helper = new DataBaseHelper();
        BasePerson person = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        //String sql = "SELECT * FROM " + BasePersonColumn.tableName_initial + " WHERE " + BasePersonColumn.name + " = ? ";
        String selection = BasePersonColumn.name + " = ? ";
        String[] args = new String[]{name};
        Cursor cursor = db.query(BasePersonColumn.tableName, null, selection, args, null, null, null);
        if (cursor == null) return person;
        person = cursorToPerson(cursor);
        cursor.close();
        db.close();
        return person;
    }

    /**
     * SELECT 列名称 FROM 表名称 WHERE 列 运算符 值
     */
    public static BasePerson getPersonFromDataBaseByPinyin(String namePingyin) {
        DataBaseHelper helper = new DataBaseHelper();
        BasePerson person = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        //String sql = "SELECT * FROM " + BasePersonColumn.tableName_initial + " WHERE " + BasePersonColumn.name + " = ? ";
        String selection = BasePersonColumn.namePinyin + " = ? ";
        String[] args = new String[]{namePingyin};
        Cursor cursor = db.query(BasePersonColumn.tableName, null, selection, args, null, null, null);
        if (cursor == null) return person;
        person = cursorToPerson(cursor);
        cursor.close();
        db.close();
        return person;
    }

    /**
     * 返回一群武将对象
     *
     * @param c
     * @return
     */
    private static ArrayList<BasePerson> cursorToPersons(Cursor c) {
        ArrayList<BasePerson> lists = null;
        BasePerson person = null;
        if (c == null) return lists;
        if (c.getCount() > 0) {
            lists = new ArrayList<BasePerson>();
            while (c.moveToNext()) {
                person = new BasePerson();
                person.setName(c.getString(c.getColumnIndex(BasePersonColumn.name)));
                person.setStrength_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.strength_Raw)));
                person.setIntellect_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.intellect_Raw)));
                person.setPhysique_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.physique_Raw)));
                person.setDexterity_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.dexterity_Raw)));
                person.setSpirit_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.spirit_Raw)));
                person.setSexuality(c.getInt(c.getColumnIndex(BasePersonColumn.sexuality)));
                person.setPersonId(c.getInt(c.getColumnIndex(BasePersonColumn.personId)));
                person.setLuck_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.luck_Raw)));
                person.setFascination_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.fascination_Raw)));
                person.setSkillStrings(c.getString(c.getColumnIndex(BasePersonColumn.skill_lists_Raw)));
                person.setLeadSkillId(c.getInt(c.getColumnIndex(BasePersonColumn.lead_buff_id)));
                lists.add(person);
            }
        }
        return lists;
    }

    /**
     * 返回一个武将对象
     *
     * @param c
     * @return
     */
    private static BasePerson cursorToPerson(Cursor c) {
        BasePerson person = null;
        if (c == null) return person;
        if (c.getCount() > 0) {
            c.moveToNext();
            person = new BasePerson();
            person.setName(c.getString(c.getColumnIndex(BasePersonColumn.name)));
            person.setNamePinyin(c.getString(c.getColumnIndex(BasePersonColumn.namePinyin)));
            person.setStrength_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.strength_Raw)));
            person.setIntellect_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.intellect_Raw)));
            person.setPhysique_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.physique_Raw)));
            person.setDexterity_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.dexterity_Raw)));
            person.setSpirit_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.spirit_Raw)));
            person.setSexuality(c.getInt(c.getColumnIndex(BasePersonColumn.sexuality)));
            person.setPersonId(c.getInt(c.getColumnIndex(BasePersonColumn.personId)));
            person.setLuck_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.luck_Raw)));
            person.setFascination_Raw(c.getInt(c.getColumnIndex(BasePersonColumn.fascination_Raw)));
            person.setSkillStrings(c.getString(c.getColumnIndex(BasePersonColumn.skill_lists_Raw)));
            person.setLeadSkillId(c.getInt(c.getColumnIndex(BasePersonColumn.lead_buff_id)));
        }
        return person;
    }
}