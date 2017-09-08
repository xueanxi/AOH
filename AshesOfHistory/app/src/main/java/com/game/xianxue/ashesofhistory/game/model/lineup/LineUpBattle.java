package com.game.xianxue.ashesofhistory.game.model.lineup;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import java.util.ArrayList;

/**
 * 战斗阵型，对基础阵型类LineUpBase进行扩展
 */
public class LineUpBattle extends LineUpBase {
    final static String TAG = "LineUpBattle";

    int battlePersonNumber;                 // 这个阵型中的人数
    ArrayList<UnitBattle> lineupList;       // 阵型中的每一个位置的数组
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

        // 把 lineupJson 转化为 ArrayList<UnitBattle>
        ArrayList<UnitBase> rawUnitLists = UnitBase.toDataLists(this.lineupJson);
        if (rawUnitLists == null || rawUnitLists.size() <= 0) {
            SimpleLog.loge(TAG, "Error!!! LineUpBattle 初始化失败，因为读取 rawUnitLists 信息不合法");
            return;
        }
        this.lineupList = new ArrayList<UnitBattle>();
        for (UnitBase data : rawUnitLists) {
            this.lineupList.add(new UnitBattle(data));
        }




        initLineUp();
    }

    /**
     * 初始化阵型，把人物按照默认位置放进阵型中
     */
    private void initLineUp() {
        // 盘但阵容是否容纳的下队伍的人数
        if (this.maxPerson < membersList.size()) {
            SimpleLog.loge(TAG, "Error !!! initLineUp faile . 阵型无法容纳那么多人");
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
            SimpleLog.loge(TAG, "Error !!! initLineUp faile. 队伍成员里面缺少军师和统帅");
            return;
        }
        // 开始往阵型里面塞人

        for(int i =0 ;i<membersList.size();i++){
            BattlePerson person = membersList.get(i);
            if(person.isLeader()){
                for(int j =0;j<lineupList.size();j++){
                    UnitBattle unit = lineupList.get(j);
                    if(unit.isLeader()){
                        SimpleLog.logd(TAG,"i = "+i+" j ="+j);
                        unit.setPersonIndex(i);
                        SimpleLog.logd(TAG,"unit 1 = "+unit);
                        break;
                    }
                }
            }else if(person.isCounsellor()){
                for(UnitBattle unit:lineupList){
                    if(unit.isCounsellor()){
                        unit.setPersonIndex(i);
                        SimpleLog.logd(TAG,"unit 2 = "+unit);
                        break;
                    }
                }
            }
        }

        //ShowUtils.showArrays(TAG, lineupList);
    }

    public void displayLineUp() {
        ShowUtils.showArrays(TAG, lineupList);
    }

    /**
     * 把 lineupJson 的数据，转换成 ArrayList<UnitBase> ，方便在战斗中使用
     */
    private void initLineupList() {
    }

}
