package com.game.xianxue.aoh.game.model.lineup;

import java.util.List;

/**
 * 阵型的基础类，这个类只包含了阵型的基本信息，在战斗中的阵型比这个复杂一点，继承这个类进行扩展
 *      行\列 0 1 2
 *      0  [ 0 0 0 ]
 *   前 1  [ A 0 0 ]  后
 *   排 2  [ 0 0 0 ]  排
 *     3  [ 0 0 B ]
 *     4  [ 0 0 0 ]
 *
 *  定义数组[5][3]
 * 这是一个5行3列的阵型，row = 5, col = 3, 左上角是(0,0) A坐标(1,0) B坐标(2,3),A在前排,B在后排
 */
public class FormationBase {
    final static String TAG = "FormationBase";

    public static int LINEUP_MAX_X = 6;        // 阵型X坐标最大数字
    public static int LINEUP_MAX_Y = 6;        // 阵型Y坐标最大数字

    public static int LINEUP_MINI_Y = 1;     // 阵型最小行数
    public static int LINEUP_MINI_X = 1;     // 阵型最小列数

    // 阵法类型，不同类型的阵法站位不同，加成效果不同。
    protected int formation_id;                           // 阵法id
    protected String name;                                // 阵法名字
    protected String introduce;                           // 阵法介绍
    protected int maxPerson;                              // 阵法可以容纳的人数
    protected List<FormationUnitBase> unitList;           // 阵型矩阵，相当于阵型的实体化
    protected String formationsJsonString;                // 阵型矩阵的json字符串，方便存储在数据库中
    public FormationBase() {

    }

    public int getFormation_id() {
        return formation_id;
    }

    public void setFormation_id(int formation_id) {
        this.formation_id = formation_id;
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

    public List<FormationUnitBase> getUnitList() {
        if(unitList == null && formationsJsonString!= null){
            unitList = FormationUnitBase.toObjectLists(formationsJsonString);
        }
        return unitList;
    }

    public void setUnitList(List<FormationUnitBase> unitList) {
        this.unitList = unitList;
    }

    public String getFormationsJsonString() {
        return formationsJsonString;
    }

    public void setFormationsJsonString(String formationsJsonString) {
        this.formationsJsonString = formationsJsonString;
    }

    @Override
    public String toString() {
        return "FormationBase{" +
                "formation_id=" + formation_id +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", maxPerson=" + maxPerson +
                ", formationsJsonString='" + formationsJsonString + '\'' +
                '}';
    }
}
