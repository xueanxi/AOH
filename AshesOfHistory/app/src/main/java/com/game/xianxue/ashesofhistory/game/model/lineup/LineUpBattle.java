package com.game.xianxue.ashesofhistory.game.model.lineup;

import android.util.Log;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;

import java.util.ArrayList;

/**
 * 战斗阵型，对基础阵型类LineUpBase进行扩展
 */
public class LineUpBattle extends LineUpBase {
    final static String TAG = "LineUpBattle";

    int battlePersonNumber;                 // 这个阵型中的人数
    //ArrayList<UnitBattle> lineupList;       // 阵型中的每一个位置的数组
    UnitBattle[][] LineupMatrixs;           // 阵型矩阵，相当于阵型的实体化
    ArrayList<BattlePerson> membersList;    // 阵型中的人物列表
    boolean isLineupWork = false;           // 阵型的效果是否发挥出来
    BattlePerson counsellor;                // 军师(可以布置军师懂得的阵型，对队伍有buff加成，如果军师被击杀，阵型buff还会存在)
    BattlePerson leader;                    // 队长、统帅(可以发挥统帅技能buff，但是统帅如果被击杀，buff会取消)
    int counsellorIndex = -1;               // 军师在 membersList 中的索引值
    int leaderIndex = -1;                   // 队长、统帅在 membersList 中的索引值

    public LineUpBattle(LineUpBase lineup, ArrayList<BattlePerson> membersList) {
        this.lineup_id = lineup.getLineup_id();
        this.name = lineup.getName();
        this.introduce = lineup.getIntroduce();
        this.maxPerson = lineup.getMaxPerson();
        this.lineupJson = lineup.getLineupJson();
        this.membersList = membersList;
        this.battlePersonNumber = membersList.size();

        // 为阵型中的每一个位置初始化
        // 把 lineupJson 转化为 ArrayList<UnitBattle>
        ArrayList<UnitBase> rawUnitLists = UnitBase.toDataLists(this.lineupJson);
        if (rawUnitLists == null || rawUnitLists.size() <= 0) {
            SimpleLog.loge(TAG, "Error!!! LineUpBattle 初始化失败，因为读取 rawUnitLists 信息不合法");
            return;
        }
/*        this.lineupList = new ArrayList<UnitBattle>();
        for (UnitBase data : rawUnitLists) {
            this.lineupList.add(new UnitBattle(data));
        }*/

        LineupMatrixs = new UnitBattle[LINEUP_MAX_ROW][LINEUP_MAX_COL];
        for (UnitBase data : rawUnitLists) {
            LineupMatrixs[data.getX()][data.getY()] = new UnitBattle(data);
        }

        fillPerson();
    }

    /**
     * 初始化阵型，把人物按照默认位置放进阵型中
     */
    private void fillPerson() {
        // 盘但阵容是否容纳的下队伍的人数
        if (this.maxPerson < membersList.size()) {
            SimpleLog.loge(TAG, "Error !!! fillPerson faile . 阵型无法容纳那么多人");
            counsellor = null;
            leader = null;
            isLineupWork = false;
            return;
        }
        // 遍历队伍成员，获得军师和统帅的索引，并且判断队伍是否合法
        for (int i = 0; i < membersList.size(); i++) {
            if (membersList.get(i).isCounsellor()) {
                counsellorIndex = i;
            } else if (membersList.get(i).isLeader()) {
                leaderIndex = i;
            }
        }
        if (counsellorIndex == -1 || leaderIndex == -1) {
            SimpleLog.loge(TAG, "Error !!! fillPerson faile. 队伍成员里面缺少军师和统帅");
            return;
        }

        UnitBattle unit = null;
        boolean isOver = false;

        // 开始往阵型里面填充人
        for (int i = 0; i < membersList.size(); i++) {
            BattlePerson person = membersList.get(i);
            // 填充团长
            if (person.isLeader()) {
                isOver = false;
                for(int x=0;x<LINEUP_MAX_ROW;x++){
                    for(int y =0;y<LINEUP_MAX_COL;y++){
                        unit = LineupMatrixs[x][y];
                        if (unit!=null && unit.isLeader()) {
                            unit.setPersonIndex(i);
                            unit.setEmpty(false);
                            isOver = true;
                            break;
                        }
                    }
                    if(isOver)break;
                }
            } else if (person.isCounsellor()) {
                // 填充军师
                isOver = false;
                for(int x=0;x<LINEUP_MAX_ROW;x++){
                    for(int y =0;y<LINEUP_MAX_COL;y++){
                        unit = LineupMatrixs[x][y];
                        if (unit!=null && unit.isCounsellor()) {
                            unit.setPersonIndex(i);
                            unit.setEmpty(false);
                            isOver = true;
                            break;
                        }
                    }
                    if(isOver)break;
                }
            } else if (!person.isLeader() && !person.isCounsellor()) {
                // 填充其他
                isOver = false;
                for(int x=0;x<LINEUP_MAX_ROW;x++){
                    for(int y =0;y<LINEUP_MAX_COL;y++){
                        unit = LineupMatrixs[x][y];
                        if (unit!=null && unit.isEmpty() && !unit.isCounsellor() && !unit.isLeader()) {
                            unit.setPersonIndex(i);
                            unit.setEmpty(false);
                            isOver = true;
                            break;
                        }
                    }
                    if(isOver)break;
                }
            }
        }
    }

    /**
     * 把 lineupJson 的数据，转换成 ArrayList<UnitBase> ，方便在战斗中使用
     */
    private void initLineupList() {
    }

    /**
     * 判断是否团灭
     *
     * @return
     */
    public boolean isAllDie() {
        boolean isAllDie = true;
        if (membersList == null || membersList.size() == 0) {
            SimpleLog.loge(TAG, "isAllDie() membersList = null OR size =0");
            return isAllDie;
        }
        for (BattlePerson person : membersList) {
            if (person.getHP() > 0) {
                isAllDie = false;
                break;
            }
        }
        SimpleLog.loge(TAG, "isAllDie = " + isAllDie);
        return isAllDie;
    }

    /**
     * 返回阵型中的人员
     *
     * @return
     */
    public ArrayList<BattlePerson> getMemberList() {
        if (membersList == null || membersList.size() == 0) {
            SimpleLog.loge(TAG, "getMemberList() membersList = null OR size =0");
            return null;
        }
        return membersList;
    }

    /**
     * 根据距离返回 在这个距离之内的人物列表
     * @param distance
     * @return
     */
    public ArrayList<BattlePerson> getPersonsByDistance(int distance) {
        ArrayList<BattlePerson> result = null;
        UnitBattle unit = null;
        // nearCol 为距离敌人最近的一列的索引，
        // 比如 第0列，还有人存活着，那么nearCol =0
        // 比如 第0列，所有人都挂了，第1列还有人存活，此时nearCol等于1，而不是0
        // 比如 第0列和第1列所有人都挂了，第2列还有人存活，此时nearCol等于2，而不是1或者0
        int nearCol = 0;
        if(membersList == null ) return null;

        // 计算出 nearCol
        for(int x=0;x<LINEUP_MAX_COL;x++){
            boolean colHasPersonLift = false;
            for(int y=0;y<LINEUP_MAX_COL;y++){
                unit = LineupMatrixs[x][y];
                if(unit ==null)continue;
                int HP = membersList.get(unit.getPersonIndex()).getHP();
                if(HP>0){
                    colHasPersonLift = true;
                    nearCol = x;
                    break;
                }
            }
            if(colHasPersonLift)break;
        }

        // 得到所有攻击范围内存活的目标
        result = new ArrayList<BattlePerson>();
        int farCol = nearCol + distance -1;
        BattlePerson personInRange = null;
        for(int x = nearCol;x<=farCol;x++){
            for(int y=0;y<LINEUP_MAX_COL;y++){
                unit = LineupMatrixs[x][y];
                if(unit == null)continue;
                personInRange = membersList.get(unit.getPersonIndex());
                int HP = personInRange.getHP();
                if( HP > 0){
                    personInRange.setDistance(x); // 设置personInRange与攻击者之间的距离
                    result.add(personInRange);
                }
            }
        }
        return result;
    }

    /**
     * 展示 矩阵
     */
    public void displayMatrix(){
        UnitBattle unit = null;
        Log.d(TAG,"======"+this.name+"的阵型 start ======");
        for(int x=0;x<LINEUP_MAX_ROW;x++) {
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < LINEUP_MAX_COL; y++) {
                unit = LineupMatrixs[x][y];
                if(unit != null){
                    String name = membersList.get(unit.getPersonIndex()).getName();
                    if(name.length() == 2){
                        sb.append(name+"   ");
                    }else if(name.length() == 3){
                        sb.append(name+"  ");
                    }else if(name.length() == 4){
                        sb.append(name+" ");
                    }
                }else{
                    sb.append("空    ");
                }
            }
            Log.d(TAG,sb.toString());
        }
        Log.d(TAG,"======"+this.name+"的阵型 end ======");
    }
}
