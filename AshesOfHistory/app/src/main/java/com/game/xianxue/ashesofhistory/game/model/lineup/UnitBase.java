package com.game.xianxue.ashesofhistory.game.model.lineup;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * 阵型中的每一个位置
 * 每一个位置有xy坐标，一些高级的阵型某些位置还会有buff加成
 * 因为一个阵型必定会有一个主帅位置和军师位置，这两个位置是固定的，所以 UnitBase 还有 是否军师位置，是否主帅位置的变量
 * Created by user on 9/4/17.
 */
public class UnitBase {
    private static final String TAG = "=UnitBase";

    int x = 0;                      // x 坐标，以左上角为原点
    int y = 0;                      // y 坐标，以左上角为原点
    ArrayList<Integer> buffIDs;     // 阵型中这个位置能获得哪些Buff的加成
    boolean canSetPerson = false;   // 是否统帅的位置
    boolean isCounsellor = false;   // 是否军师的位置
    boolean isLeader = false;       // 是否统帅的位置

    public UnitBase() {

    }

    /**
     *  unitString 是xml文件里面读取出来的内容，内容的格式如下面两个例子。
     * 例子1： unitString可能是这样的："1,2,3" 表示 x = 1,y = 2 ,作用的buff id为 3
     * 例子2： unitString可能是这样的："1,2,3,4,5,6" 表示 x = 1,y = 2 ,作用的 buff 有多个分别为3,4,5,6
     * 特殊情况：主帅和军师的位置是固定的，主帅的字符串以k开头，军师的字符串以j开头
     *
     * @param unitString
     */
    public UnitBase(String unitString) {
        if (unitString == null || unitString.length() < 5) {
            SimpleLog.loge(TAG, "Error !!! 初始化LineupUint失败");
        }

        // 先判断是否是主帅和军师的单元格
        if(unitString.startsWith("k")){
            isLeader = true;
            unitString = unitString.substring(1,unitString.length());
        }else if(unitString.startsWith("j")){
            isCounsellor = true;
            unitString = unitString.substring(1,unitString.length());
        }

        this.x = Integer.valueOf((unitString.split(",")[0]));
        this.y = Integer.valueOf((unitString.split(",")[1]));

        unitString = unitString.substring(4, unitString.length());
        String[] buffid = unitString.split(",");
        buffIDs = new ArrayList<>();
        for (int i = 0; i < buffid.length; i++) {
            buffIDs.add(Integer.valueOf(buffid[i]));
        }
        this.canSetPerson = true;
    }


    /**
     * 把 ArrayList<UnitBase>数据 转化成 json字符串
     *
     * @param unitBases
     * @return
     */
    public static String toJsonString(ArrayList<UnitBase> unitBases) {
        Gson gson = new Gson();
        return gson.toJson(unitBases);
    }

    /**
     * 把 Json 字符串转换成 ArrayList<UnitBase>
     *
     * @param jsonString
     * @return
     */
    public static final ArrayList<UnitBase> toDataLists(String jsonString) {
        Gson gson = new Gson();
        ArrayList<UnitBase> itemInfos = gson.fromJson(jsonString, new TypeToken<ArrayList<UnitBase>>() {
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

    public boolean isCounsellor() {
        return isCounsellor;
    }

    public void setCounsellor(boolean counsellor) {
        isCounsellor = counsellor;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    @Override
    public String toString() {
        return "UnitBase{" +
                "x=" + x +
                ", y=" + y +
                ", buffIDs=" + buffIDs +
                ", canSetPerson=" + canSetPerson +
                ", isCounsellor=" + isCounsellor +
                ", isLeader=" + isLeader +
                '}';
    }
}
