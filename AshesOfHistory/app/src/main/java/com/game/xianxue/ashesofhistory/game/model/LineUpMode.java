package com.game.xianxue.ashesofhistory.game.model;

import java.util.ArrayList;

/**
普通阵法
一字阵法
龙飞阵
铁甲阵
雁行阵
鱼鳞阵
 */
public class LineUpMode {
    final static String TAG = "LineUpMode";

    int LINEUP_MAX_ROW = 6;     // 阵型最大行数
    int LINEUP_MAX_COL = 6;     // 阵型最大列数

    // 阵法类型，不同类型的阵法站位不同，加成效果不同。
    int lineup_id;                      // 阵法id
    String name;                        // 阵法名字
    String introduce;                   // 阵法介绍
    int maxPerson;                      // 阵法可以容纳的人数
    ArrayList<LineupUnit> lineupList;   // 阵行数组
    String lineupJson;                  // 把lineupList 变成json字符串，方便存储在数据库中

    /**
     * 阵型 类型是一个整数
     * 使用 Integer.toBinaryString 转化为一个25位二进制数，把二进制数
     * 从左到右，从上到下排列，可以得到如下一个阵型，1表示可以放置武将，0表示不可以放置武将
     * 这样一个整数就可以表示一个阵型了。
     * 1 0 0 0 0 0
     * 0 1 0 0 0 0
     * 0 0 1 0 0 0
     * 0 1 0 0 0 0
     * 1 0 0 0 0 0
     */

    public LineUpMode(){

    }


    public LineUpMode(int lineup_id, String name, String introduce, int maxPerson, ArrayList<LineupUnit> lineupList, String lineupJson){
        this.lineup_id = lineup_id;
        this.name = name;
        this.introduce = introduce;
        this.maxPerson = maxPerson;
        this.lineupList = lineupList;
        this.lineupJson = lineupJson;
    }


    public int getLineup_id() {
        return lineup_id;
    }

    public void setLineup_id(int lineup_id) {
        this.lineup_id = lineup_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }

    public ArrayList<LineupUnit> getLineupList() {
        return lineupList;
    }

    public void setLineupList(ArrayList<LineupUnit> lineupList) {
        this.lineupList = lineupList;
    }

    public String getLineupJson() {
        return lineupJson;
    }

    public void setLineupJson(String lineupJson) {
        this.lineupJson = lineupJson;
    }

    @Override
    public String toString() {
        return "LineUpMode{" +
                "lineup_id=" + lineup_id +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", maxPerson=" + maxPerson +
                ", lineupList=" + lineupList +
                ", lineupJson='" + lineupJson + '\'' +
                '}';
    }
}
