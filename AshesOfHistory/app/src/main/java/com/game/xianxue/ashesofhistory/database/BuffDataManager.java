package com.game.xianxue.ashesofhistory.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.buff.BuffBase;
import com.game.xianxue.ashesofhistory.model.constant.ConstantColumn.BuffColumn;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import java.util.ArrayList;

/**
 * Created by user on 9/1/17.
 */

public class BuffDataManager {

    private static final String TAG = "BuffDataManager";


    /**
     * 把对象转化为可以插入数据库的SQL语句
     *
     * @param buff
     * @return
     */
    public static String getInsertString(BuffBase buff) {
        String format = "INSERT INTO " + BuffColumn.tableName + " ("
                + BuffColumn.buff_id + " ,"
                + BuffColumn.name + " ,"
                + BuffColumn.introduce + " ,"
                + BuffColumn.buff_type + " ,"
                + BuffColumn.buff_nature + " ,"
                + BuffColumn.buff_constant + " ,"
                + BuffColumn.buff_fluctuate + " ,"
                + BuffColumn.time + " ,"
                + BuffColumn.range + " ,"
                + BuffColumn.level + " ,"
                + BuffColumn.effect_up + " )"
                + " VALUES ("
                + " '%d','%s','%s','%d','%d','%d','%f','%d','%d','%d','%f'"
                + " )";

        return String.format(format,
                buff.getBuffId(),
                buff.getName(),
                buff.getIntroduce(),
                buff.getBuff_type(),
                buff.getBuff_nature(),
                buff.getBuff_constant(),
                buff.getBuff_fluctuate(),
                buff.getTime(),
                buff.getRange(),
                buff.getLevel(),
                buff.getEffect_up()
        );
    }

    public static ArrayList<BuffBase> getAllBuffFromDataBase() {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + BuffColumn.tableName;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        ArrayList<BuffBase> arrays = cursorToBuffs(cursor);
        cursor.close();
        db.close();
        ShowUtils.showArrays(TAG, arrays);
        SimpleLog.logd(TAG, "加载buff完毕！！！");
        return arrays;
    }

    public static BuffBase getBuffFromDataBaseById(int id) {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + BuffColumn.tableName + " WHERE " + BuffColumn.buff_id + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        BuffBase buff = cursorToBuff(cursor);
        cursor.close();
        db.close();
        SimpleLog.logd(TAG,buff.toString());
        return buff;
    }

    /**
     * 返回一群buff对象
     *
     * @param c
     * @return
     */
    private static ArrayList<BuffBase> cursorToBuffs(Cursor c) {
        ArrayList<BuffBase> lists = null;
        BuffBase buff = null;
        if (c == null) return lists;
        if (c.getCount() > 0) {
            lists = new ArrayList<BuffBase>();
            while (c.moveToNext()) {
                buff = new BuffBase();
                buff.setBuffId(c.getInt(c.getColumnIndex(BuffColumn.buff_id)));
                buff.setName(c.getString(c.getColumnIndex(BuffColumn.name)));
                buff.setIntroduce(c.getString(c.getColumnIndex(BuffColumn.introduce)));
                buff.setBuff_type(c.getInt(c.getColumnIndex(BuffColumn.buff_type)));
                buff.setBuff_nature(c.getInt(c.getColumnIndex(BuffColumn.buff_nature)));
                buff.setBuff_constant(c.getInt(c.getColumnIndex(BuffColumn.buff_constant)));
                buff.setBuff_fluctuate(c.getFloat(c.getColumnIndex(BuffColumn.buff_fluctuate)));
                buff.setTime(c.getInt(c.getColumnIndex(BuffColumn.time)));
                buff.setRange(c.getInt(c.getColumnIndex(BuffColumn.range)));
                buff.setLevel(c.getInt(c.getColumnIndex(BuffColumn.level)));
                buff.setEffect_up(c.getFloat(c.getColumnIndex(BuffColumn.effect_up)));
                lists.add(buff);
            }
        }
        return lists;
    }

    /**
     * 返回一个B对象
     *
     * @param c
     * @return
     */
    private static BuffBase cursorToBuff(Cursor c) {
        BuffBase buff = null;
        if (c == null) return buff;
        if (c.getCount() <= 0) {
            return null;
        }
        c.moveToNext();
        buff = new BuffBase();
        buff.setBuffId(c.getInt(c.getColumnIndex(BuffColumn.buff_id)));
        buff.setName(c.getString(c.getColumnIndex(BuffColumn.name)));
        buff.setIntroduce(c.getString(c.getColumnIndex(BuffColumn.introduce)));
        buff.setBuff_type(c.getInt(c.getColumnIndex(BuffColumn.buff_type)));
        buff.setBuff_nature(c.getInt(c.getColumnIndex(BuffColumn.buff_nature)));
        buff.setBuff_constant(c.getInt(c.getColumnIndex(BuffColumn.buff_constant)));
        buff.setBuff_fluctuate(c.getFloat(c.getColumnIndex(BuffColumn.buff_fluctuate)));
        buff.setTime(c.getInt(c.getColumnIndex(BuffColumn.time)));
        buff.setRange(c.getInt(c.getColumnIndex(BuffColumn.range)));
        buff.setLevel(c.getInt(c.getColumnIndex(BuffColumn.level)));
        buff.setEffect_up(c.getFloat(c.getColumnIndex(BuffColumn.effect_up)));
        return buff;
    }
}
