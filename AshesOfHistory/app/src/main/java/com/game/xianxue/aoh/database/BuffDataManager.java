package com.game.xianxue.aoh.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.game.xianxue.aoh.Log.LogUtils;
import com.game.xianxue.aoh.game.model.buff.BuffBase;
import com.game.xianxue.aoh.game.model.constant.ConstantColumn;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 9/1/17.
 */

public class BuffDataManager {
    private static final String TAG = "BuffDataManager";

    private static BuffDataManager sInstance;
    private Map<Integer, BuffBase> allBuffs;
    private BuffDataManager(){
        loadAllBuffFromDataBase();
    }
    public synchronized static BuffDataManager getInstance(){
        if(sInstance == null){
            sInstance = new BuffDataManager();
        }

        return sInstance;
    }

    public BuffBase getBuffById(int buffId){
        if(allBuffs == null){
            loadAllBuffFromDataBase();
        }

        if(allBuffs == null){
            LogUtils.e(TAG,"getBuffById() fail!!! return null");
            return null;
        }

        return allBuffs.get(buffId);
    }


    /**
     * 把对象转化为可以插入数据库的SQL语句
     *
     * @param buff
     * @return
     */
    public static String getInsertString(BuffBase buff) {
        String format = "INSERT INTO " + ConstantColumn.BuffColumn.tableName + " ("
                + ConstantColumn.BuffColumn.buff_id + " ,"
                + ConstantColumn.BuffColumn.name + " ,"
                + ConstantColumn.BuffColumn.introduce + " ,"
                + ConstantColumn.BuffColumn.buff_effect + " ,"
                + ConstantColumn.BuffColumn.superposition + " ,"
                + ConstantColumn.BuffColumn.buff_nature + " ,"
                + ConstantColumn.BuffColumn.buff_constant + " ,"
                + ConstantColumn.BuffColumn.buff_fluctuate + " ,"
                + ConstantColumn.BuffColumn.time + " ,"
                + ConstantColumn.BuffColumn.range + " ,"
                + ConstantColumn.BuffColumn.damage_type + " ,"
                + ConstantColumn.BuffColumn.level_up_constant + " ,"
                + ConstantColumn.BuffColumn.level_up_fluctuate + " ,"
                + ConstantColumn.BuffColumn.level_up_range + " ,"
                + ConstantColumn.BuffColumn.level_up_time + " )"
                + " VALUES ("
                + " '%d','%s','%s','%s','%d','%d','%s','%s','%d','%d','%d','%s','%s','%f','%f'"
                + " )";

        return String.format(format,
                buff.getBuffId(),
                buff.getName(),
                buff.getIntroduce(),
                buff.getSbuff_effect(),
                buff.getSuperposition(),
                buff.getBuff_nature(),
                buff.getSbuff_constant(),
                buff.getSbuff_fluctuate(),
                buff.getTime(),
                buff.getRange(),
                buff.getDamage_type(),
                buff.getSlevel_up_constant(),
                buff.getSlevel_up_fluctuate(),
                buff.getLevel_up_range(),
                buff.getLevel_up_time()
        );
    }

    public void loadAllBuffFromDataBase() {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + ConstantColumn.BuffColumn.tableName;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return;
        allBuffs = cursorToBuffs(cursor);
        cursor.close();
        db.close();
        //ShowUtils.showArrayLists(TAG, arrays);
        LogUtils.d(TAG, "loadAllBuffFromDataBase() 所有buff加载完毕！！！");
    }

    public static BuffBase getBuffFromDataBaseById(int id) {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + ConstantColumn.BuffColumn.tableName + " WHERE " + ConstantColumn.BuffColumn.buff_id + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return null;
        BuffBase buff = cursorToBuff(cursor);
        cursor.close();
        db.close();
        //LogUtils.d(TAG, buff.toString());
        return buff;
    }

    /**
     * 返回一群buff对象
     *
     * @param c
     * @return
     */
    private  Map<Integer,BuffBase> cursorToBuffs(Cursor c) {
        Map<Integer,BuffBase> map = new HashMap<>();
        BuffBase buff = null;
        if (c == null) return null;
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                buff = new BuffBase();
                buff.setBuffId(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.buff_id)));
                buff.setName(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.name)));
                buff.setIntroduce(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.introduce)));
                buff.setSbuff_effect(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.buff_effect)));
                buff.setSuperposition(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.superposition)));
                buff.setBuff_nature(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.buff_nature)));
                buff.setSbuff_constant(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.buff_constant)));
                buff.setSbuff_fluctuate(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.buff_fluctuate)));
                buff.setTime(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.time)));
                buff.setRange(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.range)));
                buff.setSlevel_up_constant(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.level_up_constant)));
                buff.setSlevel_up_fluctuate(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.level_up_fluctuate)));
                buff.setLevel_up_range(c.getFloat(c.getColumnIndex(ConstantColumn.BuffColumn.level_up_range)));
                buff.setLevel_up_time(c.getFloat(c.getColumnIndex(ConstantColumn.BuffColumn.level_up_time)));
                buff.setDamage_type(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.damage_type)));
                map.put(buff.getBuffId(),buff);
            }
        }
        return map;
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
        buff.setBuffId(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.buff_id)));
        buff.setName(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.name)));
        buff.setIntroduce(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.introduce)));
        buff.setSbuff_effect(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.buff_effect)));
        buff.setSuperposition(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.superposition)));
        buff.setBuff_nature(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.buff_nature)));
        buff.setSbuff_constant(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.buff_constant)));
        buff.setSbuff_fluctuate(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.buff_fluctuate)));
        buff.setTime(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.time)));
        buff.setRange(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.range)));
        buff.setSlevel_up_constant(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.level_up_constant)));
        buff.setSlevel_up_fluctuate(c.getString(c.getColumnIndex(ConstantColumn.BuffColumn.level_up_fluctuate)));
        buff.setLevel_up_range(c.getFloat(c.getColumnIndex(ConstantColumn.BuffColumn.level_up_range)));
        buff.setLevel_up_time(c.getFloat(c.getColumnIndex(ConstantColumn.BuffColumn.level_up_time)));
        buff.setDamage_type(c.getInt(c.getColumnIndex(ConstantColumn.BuffColumn.damage_type)));
        return buff;
    }
}
