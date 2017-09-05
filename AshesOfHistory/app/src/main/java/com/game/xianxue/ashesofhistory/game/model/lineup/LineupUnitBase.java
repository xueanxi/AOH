package com.game.xianxue.ashesofhistory.game.model.lineup;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * 阵型中的每一个基本单位
 * Created by user on 9/4/17.
 */
public class LineupUnitBase {
    private static final String TAG = "=LineupUnitBase";

    int x = 0;                      // x 坐标，以左上角为原点
    int y = 0;                      // y 坐标，以左上角为原点
    ArrayList<Integer> buffIDs;     // 阵型中这个位置能获得哪些Buff的加成
    boolean canSetPerson = true;
    //BattlePerson person = null;


    public LineupUnitBase() {

    }

    /**
     * 通过一个包含 LineupUnitBase 信息的字符串来初始化
     * initString 可能是这样的："1,2,3" 表示 x = 1,y = 2 ,作用的buff id为 3
     * initString 可能是这样的："1,2,3,4,5,6" 表示 x = 1,y = 2 ,作用的 buff 有多个分别为3,4,5,6
     *
     * @param initString
     */
    public LineupUnitBase(String initString) {
        if (initString == null || initString.length() < 5) {
            SimpleLog.loge(TAG, "Error !!! 初始化LineupUint失败");
        }

        this.x = Integer.valueOf((initString.split(",")[0]));
        this.y = Integer.valueOf((initString.split(",")[1]));

        initString = initString.substring(4, initString.length());
        String[] buffid = initString.split(",");
        buffIDs = new ArrayList<>();
        for (int i = 0; i < buffid.length; i++) {
            buffIDs.add(Integer.valueOf(buffid[i]));
        }
        this.canSetPerson = true;
    }

    public LineupUnitBase(int x, int y, ArrayList<Integer> buffIdArrays, boolean canSetPerson) {
        this.x = x;
        this.y = y;
        this.buffIDs = buffIdArrays;
        this.canSetPerson = canSetPerson;
    }

    public void setBatterPerson(BattlePerson person) {
        //this.person = person;
    }

    /**
     * 把 ArrayList<LineupUnitBase>数据 转化成 json字符串
     *
     * @param lineupUnitBases
     * @return
     */
    public static String toJsonString(ArrayList<LineupUnitBase> lineupUnitBases) {
        Gson gson = new Gson();
        return gson.toJson(lineupUnitBases);
    }

    /**
     * 把 Json 字符串转换成 ArrayList<LineupUnitBase>
     *
     * @param jsonString
     * @return
     */
    public static final ArrayList<LineupUnitBase> toDataLists(String jsonString) {
        Gson gson = new Gson();
        ArrayList<LineupUnitBase> itemInfos = gson.fromJson(jsonString, new TypeToken<ArrayList<LineupUnitBase>>() {
        }.getType());
        return itemInfos;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Integer> getBuffIDs() {
        return buffIDs;
    }

    public void setBuffIDs(ArrayList<Integer> buffIDs) {
        this.buffIDs = buffIDs;
    }

    public boolean isCanSetPerson() {
        return canSetPerson;
    }

    public void setCanSetPerson(boolean canSetPerson) {
        this.canSetPerson = canSetPerson;
    }

    public BattlePerson getPerson() {
        //return person;
        return null;
    }

    public void setPerson(BattlePerson person) {
        //this.person = person;
    }

    @Override
    public String toString() {
        return "LineupUnitBase{" +
                "x=" + x +
                ", y=" + y +
                ", buffIDs=" + buffIDs +
                ", canSetPerson=" + canSetPerson +
                //", person=" + person +
                '}';
    }
}
