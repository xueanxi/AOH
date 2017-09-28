package com.game.xianxue.ashesofhistory.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.lineup.LineUpBase;
import com.game.xianxue.ashesofhistory.game.model.constant.ConstantColumn.LineUpColumn;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import java.util.ArrayList;

/**
 * Created by user on 9/1/17.
 */

public class LineUpDataManager {

    private static final String TAG = "LineUpDataManager";


    /**
     * 把对象转化为可以插入数据库的SQL语句
     *
     * @param data
     * @return
     */
    public static String getInsertString(LineUpBase data) {
        String format = "INSERT INTO " + LineUpColumn.tableName + " ("
                + LineUpColumn.lineup_id + " ,"
                + LineUpColumn.name + " ,"
                + LineUpColumn.introduce + " ,"
                + LineUpColumn.max_person + " ,"
                + LineUpColumn.lineup_json + " )"
                + " VALUES ("
                + " '%d','%s','%s','%d','%s'"
                + " )";

        return String.format(format,
                data.getLineup_id(),
                data.getName(),
                data.getIntroduce(),
                data.getMaxPerson(),
                data.getLineupJson()
        );
    }


    /**
     * 从数据库里面读出所有的阵法
     * @return
     */
    public static ArrayList<LineUpBase>getAllDataFromDataBase() {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + LineUpColumn.tableName;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        ArrayList<LineUpBase> arrays = cursorToBuffs(cursor);
        cursor.close();
        db.close();
        ShowUtils.showArrayLists(TAG, arrays);
        return arrays;
    }

    /**
     * 从数据库里面挑选一个阵法 id 为参数传进来的值
     * @param id
     * @return
     */
    public static LineUpBase getDataFromDataBaseById(int id) {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + LineUpColumn.tableName + " WHERE " + LineUpColumn.lineup_id + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        LineUpBase data = cursorToBuff(cursor);
        cursor.close();
        db.close();
        SimpleLog.logd(TAG, data.toString());
        return data;
    }

    /**
     * 返回一群buff对象
     *
     * @param c
     * @return
     */
    private static ArrayList<LineUpBase> cursorToBuffs(Cursor c) {
        ArrayList<LineUpBase> lists = null;
        LineUpBase data = null;
        if (c == null) return lists;
        if (c.getCount() > 0) {
            lists = new ArrayList<LineUpBase>();
            while (c.moveToNext()) {
                data = new LineUpBase();
                data.setLineup_id(c.getInt(c.getColumnIndex(LineUpColumn.lineup_id)));
                data.setName(c.getString(c.getColumnIndex(LineUpColumn.name)));
                data.setIntroduce(c.getString(c.getColumnIndex(LineUpColumn.introduce)));
                data.setMaxPerson(c.getInt(c.getColumnIndex(LineUpColumn.max_person)));
                data.setLineupJson(c.getString(c.getColumnIndex(LineUpColumn.lineup_json)));
                lists.add(data);
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
    private static LineUpBase cursorToBuff(Cursor c) {
        LineUpBase data = null;
        if (c == null) return data;
        if (c.getCount() <= 0) {
            return null;
        }
        c.moveToNext();
        data = new LineUpBase();
        data.setLineup_id(c.getInt(c.getColumnIndex(LineUpColumn.lineup_id)));
        data.setName(c.getString(c.getColumnIndex(LineUpColumn.name)));
        data.setIntroduce(c.getString(c.getColumnIndex(LineUpColumn.introduce)));
        data.setMaxPerson(c.getInt(c.getColumnIndex(LineUpColumn.max_person)));
        data.setLineupJson(c.getString(c.getColumnIndex(LineUpColumn.lineup_json)));
        return data;
    }
}
