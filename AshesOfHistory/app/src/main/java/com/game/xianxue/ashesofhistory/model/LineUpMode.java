package com.game.xianxue.ashesofhistory.model;

import com.game.xianxue.ashesofhistory.game.skill.SkillBase;
import com.game.xianxue.ashesofhistory.model.person.BattlePerson;

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
    int id;                     // 阵法id
    String name;                // 阵法名字
    String introduce;           // 阵法介绍
    int maxPerson;              // 阵法可以容纳的人数
    long lineup;                // 阵型（一个long型的数字，可以解析成一个阵型）
    ArrayList<Uint> lineupList; // 阵行数组

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
    int zhenXing = 33554431;

    public LineUpMode(int id,String name,String introduce,int maxPerson,long lineup){
        this.id = id;
        this.name = name;
        this.introduce = introduce;
        this.maxPerson = maxPerson;
        this.lineup = lineup;

        initLineUp(lineup);
    }

    /**
     * 初始化阵型
     * @param lineup
     */
    private void initLineUp(long lineup) {
        lineupList = new ArrayList<Uint>();
        String lineupCode = null;
        char last;
        maxPerson = 0;
        int index = 0;
        Uint unit = null;
        for(int i=LINEUP_MAX_ROW -1;i>=0;i--){
            for(int j =LINEUP_MAX_ROW -1;j>=0;j--){
                lineupCode = Long.toBinaryString(lineup);
                last = lineupCode.charAt(lineupCode.length() - 1);
                unit = new Uint();
                if (last == '1') {
                    maxPerson++;
                    unit.x = i;
                    unit.y = j;


                } else {

                }


                lineup = lineup>>1;

            }
        }
    }



    /**
     * 每一个位置
     */
    class Uint{
        int x = 0;
        int y = 0;
        int skillID = 0;
        boolean canSetPerson = false;
        BattlePerson person = null;
    }
}
