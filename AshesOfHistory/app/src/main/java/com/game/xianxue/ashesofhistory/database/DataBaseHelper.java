package com.game.xianxue.ashesofhistory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.game.xianxue.ashesofhistory.App;
import com.game.xianxue.ashesofhistory.constant.Constant;
import com.game.xianxue.ashesofhistory.model.PlayerModel;
import com.game.xianxue.ashesofhistory.utils.XmlUtils;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by user on 5/25/17.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DataBaseHelper";

    private static Context mContext = App.getInstance().getApplicationContext();
    private static final String DB_NAME = Constant.GAME_NAME + ".db";
    private static final int DB_VERSION = 1;
    //private static DataBaseHelper sDatabaseHelper = null;

    /*public static synchronized DataBaseHelper getInstance() {
        Log.d(TAG, "");
        if (sDatabaseHelper == null) {
            Log.d(TAG, "anxi new");
            sDatabaseHelper = new DataBaseHelper(mContext);
            if(sDatabaseHelper == null){
                Log.d(TAG,"anxi but sDatabaseHelper is null");
            }
        }
        return sDatabaseHelper;
    }*/

    public DataBaseHelper() {
        super(mContext, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "anxi onCreate");
        createTableCharacter(db);
    }

    /**
     * 创建 人物 相关的表格
     *
     * @param db
     */
    private void createTableCharacter(SQLiteDatabase db) {
        //创建一个游戏所有人物数据表
        db.execSQL("CREATE TABLE " + PlayerModel.Column.tableName_initial
                + " (" + PlayerModel.Column.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PlayerModel.Column.character_id + " INTEGER,"
                + PlayerModel.Column.name + " TEXT,"
                + PlayerModel.Column.sexuality + " INTEGER,"
                + PlayerModel.Column.aptitude + " REAL,"
                + PlayerModel.Column.strength_Initial + " INTEGER,"
                + PlayerModel.Column.intellect_Initial + " INTEGER,"
                + PlayerModel.Column.dexterity_Initial + " INTEGER,"
                + PlayerModel.Column.physique_Initial + " INTEGER,"
                + PlayerModel.Column.spirit_Initial + " INTEGER,"
                + PlayerModel.Column.luck_Initial + " INTEGER,"
                + PlayerModel.Column.fascination_Initial + " INTEGER,"
                + PlayerModel.Column.skill_lists + " TEXT)");
        insertCharacterInitialData(db);

        // 创建一个玩家拥有的 人物 数据表
        db.execSQL("CREATE TABLE " + PlayerModel.Column.tableName_player
                + " (" + PlayerModel.Column.id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PlayerModel.Column.character_id + " INTEGER,"
                + PlayerModel.Column.name + " TEXT,"
                + PlayerModel.Column.sexuality + " INTEGER,"
                + PlayerModel.Column.level + " INTEGER,"            //等级
                + PlayerModel.Column.aptitude + " REAL,"
                + PlayerModel.Column.strength_Initial + " INTEGER,"
                + PlayerModel.Column.intellect_Initial + " INTEGER,"
                + PlayerModel.Column.dexterity_Initial + " INTEGER,"
                + PlayerModel.Column.physique_Initial + " INTEGER,"
                + PlayerModel.Column.spirit_Initial + " INTEGER,"
                + PlayerModel.Column.luck_Initial + " INTEGER,"
                + PlayerModel.Column.fascination_Initial + " INTEGER,"
                + PlayerModel.Column.equipId + " INTEGER,"          // 装备
                + PlayerModel.Column.treasureId + " INTEGER,"       // 携带宝物
                + PlayerModel.Column.experience + " INTEGER,"       // 经验
                + PlayerModel.Column.skill_lists + " TEXT)");
    }

    /**
     * 插入游戏初始任务数据
     * INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
     */
    private void insertCharacterInitialData(SQLiteDatabase db) {
        // 进行批处理
        ArrayList<PlayerModel> lists = null;
        db.beginTransaction();
        try {
            lists = XmlUtils.getAllCharacter(mContext);
            if (lists == null) return;
        } catch (Exception e) {
            Log.d(TAG, "anxi Fail !!! Exception e:" + e);
            e.printStackTrace();
        }

        for (PlayerModel player : lists) {
            db.execSQL(PlayerManager.getInsertString(player));
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