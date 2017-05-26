package com.game.xianxue.ashesofhistory.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.game.xianxue.ashesofhistory.model.PlayerModel;

import java.util.ArrayList;

/**
 * Created by user on 5/26/17.
 */
public class PlayerManager {

    private static final String TAG = "PlayerManager";

    /**
     * 把对象转化为可以插入数据库的SQL语句
     *
     * @param player
     * @return
     */
    public static String getInsertString(PlayerModel player) {
        String format = "INSERT INTO " + PlayerModel.Column.tableName_initial + " ("
                + PlayerModel.Column.character_id + " ,"
                + PlayerModel.Column.name + " ,"
                + PlayerModel.Column.sexuality + " ,"
                + PlayerModel.Column.aptitude + " ,"
                + PlayerModel.Column.strength_Initial + " ,"
                + PlayerModel.Column.intellect_Initial + " ,"
                + PlayerModel.Column.dexterity_Initial + " ,"
                + PlayerModel.Column.physique_Initial + " ,"
                + PlayerModel.Column.spirit_Initial + " ,"
                + PlayerModel.Column.luck_Initial + " ,"
                + PlayerModel.Column.fascination_Initial + " ,"
                + PlayerModel.Column.skill_lists + " )"
                + " VALUES ("
                + " '%d','%s','%d','%f','%d','%d','%d','%d','%d','%d','%d','%s'"
                + " )";
        return String.format(format,
                player.getCharacter_id(),
                player.getName(),
                player.getSexuality(),
                player.getAptitude(),
                player.getStrength_Initial(),
                player.getIntellect_Initial(),
                player.getDexterity_Initial(),
                player.getPhysique_Initial(),
                player.getSpirit_Initial(),
                player.getLuck_Initial(),
                player.getFascination_Initial(),
                player.getSkillLists());
    }

    public static void getAllCharacterFromDataBase() {
        DataBaseHelper helper = new DataBaseHelper();
        if (helper == null) return;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + PlayerModel.Column.tableName_initial;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null) return;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(PlayerModel.Column.name));
            Log.d(TAG, "anxi name" + name);
        }
    }

    /**
     * SELECT 列名称 FROM 表名称 WHERE 列 运算符 值
     */
    public static PlayerModel getCharacterFromDataBaseByName(String name) {
        DataBaseHelper helper = new DataBaseHelper();
        PlayerModel player = null;
        if (helper == null) return player;
        SQLiteDatabase db = helper.getReadableDatabase();
        //String sql = "SELECT * FROM " + PlayerModel.Column.tableName_initial + " WHERE " + PlayerModel.Column.name + " = ? ";
        String selection = PlayerModel.Column.name + " = ? ";
        String[] args = new String[]{name};
        Cursor cursor = db.query(PlayerModel.Column.tableName_initial, null, selection, args, null, null, null);
        if (cursor == null) return player;
        player = cursorToPlayer(cursor);
        cursor.close();
        db.close();
        return player;
    }

    /**
     * 返回一群武将对象
     * @param c
     * @return
     */
    private static ArrayList<PlayerModel> cursorToPlayers(Cursor c) {
        ArrayList<PlayerModel> lists = null;
        PlayerModel player = null;
        if (c == null) return lists;
        if (c.getCount() > 0) {
            lists = new ArrayList<PlayerModel>();
            while (c.moveToNext()) {
                player = new PlayerModel();
                player.setName(c.getString(c.getColumnIndex(PlayerModel.Column.name)));
                player.setStrength_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.strength_Initial)));
                player.setIntellect_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.intellect_Initial)));
                player.setPhysique_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.physique_Initial)));
                player.setDexterity_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.dexterity_Initial)));
                player.setSpirit_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.spirit_Initial)));
                player.setSexuality(c.getInt(c.getColumnIndex(PlayerModel.Column.sexuality)));
                player.setCharacter_id(c.getInt(c.getColumnIndex(PlayerModel.Column.character_id)));
                player.setAptitude(c.getInt(c.getColumnIndex(PlayerModel.Column.aptitude)));
                player.setLuck_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.luck_Initial)));
                player.setFascination_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.fascination_Initial)));
                lists.add(player);
            }
        }
        return lists;
    }

    /**
     * 返回一个武将对象
     * @param c
     * @return
     */
    private static PlayerModel cursorToPlayer(Cursor c) {
        PlayerModel player = null;
        if (c == null) return player;
        if (c.getCount() > 0) {
            c.moveToNext();
            player = new PlayerModel();
            player.setName(c.getString(c.getColumnIndex(PlayerModel.Column.name)));
            player.setStrength_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.strength_Initial)));
            player.setIntellect_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.intellect_Initial)));
            player.setPhysique_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.physique_Initial)));
            player.setDexterity_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.dexterity_Initial)));
            player.setSpirit_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.spirit_Initial)));
            player.setSexuality(c.getInt(c.getColumnIndex(PlayerModel.Column.sexuality)));
            player.setCharacter_id(c.getInt(c.getColumnIndex(PlayerModel.Column.character_id)));
            player.setAptitude(c.getInt(c.getColumnIndex(PlayerModel.Column.aptitude)));
            player.setLuck_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.luck_Initial)));
            player.setFascination_Initial(c.getInt(c.getColumnIndex(PlayerModel.Column.fascination_Initial)));
        }
        return player;
    }
}