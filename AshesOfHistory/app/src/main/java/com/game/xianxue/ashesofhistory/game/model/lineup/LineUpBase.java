package com.game.xianxue.ashesofhistory.game.model.lineup;

/**
 * 阵型的基础类，这个类只包含了阵型的基本信息，在战斗中的阵型比这个复杂一点，继承这个类进行扩展
 */
public class LineUpBase {
    final static String TAG = "LineUpBase";

    int LINEUP_MAX_ROW = 9;     // 阵型最大行数
    int LINEUP_MAX_COL = 9;     // 阵型最大列数

    int LINEUP_MINI_ROW = 1;     // 阵型最小行数
    int LINEUP_MINI_COL = 1;     // 阵型最小列数

    // 阵法类型，不同类型的阵法站位不同，加成效果不同。
    protected int lineup_id;                              // 阵法id
    protected String name;                                // 阵法名字
    protected String introduce;                           // 阵法介绍
    protected int maxPerson;                              // 阵法可以容纳的人数
    protected String lineupJson;                          // 把lineupList 变成json字符串，方便存储在数据库中

    public LineUpBase() {

    }

    public LineUpBase(int lineup_id, String name, String introduce, int maxPerson, String lineupJson) {
        this.lineup_id = lineup_id;
        this.name = name;
        this.introduce = introduce;
        this.maxPerson = maxPerson;
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

    public String getLineupJson() {
        return lineupJson;
    }

    public void setLineupJson(String lineupJson) {
        this.lineupJson = lineupJson;
    }

    @Override
    public String toString() {
        return "LineUpBase{" +
                "lineup_id=" + lineup_id +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", maxPerson=" + maxPerson +
                ", lineupJson='" + lineupJson + '\'' +
                '}';
    }
}
