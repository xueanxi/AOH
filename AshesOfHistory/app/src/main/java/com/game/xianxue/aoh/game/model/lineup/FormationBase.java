package com.game.xianxue.aoh.game.model.lineup;

import com.game.xianxue.aoh.Log.LogUtils;

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
 *  定义数组[5][3] 5行3列
 * 这是一个5行3列的阵型，row = 5, col = 3, 左上角是(0,0) A坐标(1,0) B坐标(2,3),A在前排,B在后排
 */
public class FormationBase {
    final static String TAG = "FormationBase";

    public static int LINEUP_MAX_COL = 6;           // 阵型最大列数
    public static int LINEUP_MAX_ROW = 6;           // 阵型最大行数

    public static int LINEUP_MINI_ROW = 1;          // 阵型最小行数
    public static int LINEUP_MINI_COL = 1;          // 阵型最小列数

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

    /**
     * 展示 矩阵
     */
    public void displayMatrix(String tag) {
        getUnitList();
        LogUtils.d(tag,"======" + this.name + "的阵型 start ======");
        for (int row = 0; row < LINEUP_MAX_ROW; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < LINEUP_MAX_COL; col++) {
                boolean isEmpty = true;
                for(FormationUnitBase unit:unitList){
                    if(unit.getRow() == row && unit.getCol() == col){
                        if(unit.isCounsellor){
                            sb.append("C");

                        }else if(unit.isLeader){
                            sb.append("L");
                        }else{
                            sb.append("X");
                        }
                        isEmpty = false;
                        break;
                    }
                }
                if(isEmpty){
                    sb.append("O");
                }
            }
            LogUtils.d(tag,sb.toString());
        }
        LogUtils.d(tag,"======" + this.name + "的阵型 end ======");
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
