package com.game.xianxue.aoh.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.game.xianxue.aoh.Log.LogUtils;
import com.game.xianxue.aoh.game.model.lineup.FormationBase;
import com.game.xianxue.aoh.game.model.constant.ConstantColumn.FormationColumn;
import com.game.xianxue.aoh.utils.ShowUtils;

import java.util.ArrayList;

/**
 * Created by user on 9/1/17.
 */

public class FormationManager {

    private static final String TAG = "FormationManager";


    /**
     * 把对象转化为可以插入数据库的SQL语句
     *
     * @param data
     * @return
     */
    public static String getInsertString(FormationBase data) {
        String format = "INSERT INTO " + FormationColumn.tableName + " ("
                + FormationColumn.formation_id + " ,"
                + FormationColumn.name + " ,"
                + FormationColumn.introduce + " ,"
                + FormationColumn.max_person + " ,"
                + FormationColumn.formation_json + " )"
                + " VALUES ("
                + " '%d','%s','%s','%d','%s'"
                + " )";

        return String.format(format,
                data.getFormation_id(),
                data.getName(),
                data.getIntroduce(),
                data.getMaxPerson(),
                data.getFormationsJsonString()
        );
    }


    /**
     * 从数据库里面读出所有的阵法
     * @return
     */
    public static ArrayList<FormationBase>getAllDataFromDataBase() {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + FormationColumn.tableName;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        ArrayList<FormationBase> arrays = cursorToDataLists(cursor);
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
    public static FormationBase getDataFromDataBaseById(int id) {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + FormationColumn.tableName + " WHERE " + FormationColumn.formation_id + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        FormationBase data = cursorToData(cursor);
        cursor.close();
        db.close();
        LogUtils.d(TAG, data.toString());
        return data;
    }

    /**
     * 返回一群buff对象
     *
     * @param c
     * @return
     */
    private static ArrayList<FormationBase> cursorToDataLists(Cursor c) {
        ArrayList<FormationBase> lists = null;
        FormationBase data = null;
        if (c == null) return lists;
        if (c.getCount() > 0) {
            lists = new ArrayList<FormationBase>();
            while (c.moveToNext()) {
                data = new FormationBase();
                data.setFormation_id(c.getInt(c.getColumnIndex(FormationColumn.formation_id)));
                data.setName(c.getString(c.getColumnIndex(FormationColumn.name)));
                data.setIntroduce(c.getString(c.getColumnIndex(FormationColumn.introduce)));
                data.setMaxPerson(c.getInt(c.getColumnIndex(FormationColumn.max_person)));
                data.setFormationsJsonString(c.getString(c.getColumnIndex(FormationColumn.formation_json)));
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
    private static FormationBase cursorToData(Cursor c) {
        FormationBase data = null;
        if (c == null) return data;
        if (c.getCount() <= 0) {
            return null;
        }
        c.moveToNext();
        data = new FormationBase();
        data.setFormation_id(c.getInt(c.getColumnIndex(FormationColumn.formation_id)));
        data.setName(c.getString(c.getColumnIndex(FormationColumn.name)));
        data.setIntroduce(c.getString(c.getColumnIndex(FormationColumn.introduce)));
        data.setMaxPerson(c.getInt(c.getColumnIndex(FormationColumn.max_person)));
        data.setFormationsJsonString(c.getString(c.getColumnIndex(FormationColumn.formation_json)));
        return data;
    }
}
