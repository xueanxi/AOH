package com.game.xianxue.ashesofhistory.game.model;

import com.game.xianxue.ashesofhistory.Log.BattleLog;
import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;

import java.util.ArrayList;

/**
 * 队伍模型
 */
public class TeamModel {
    //常量
    private static final String TAG = "TeamModel";

    public static final int CAMP_NEUTRAL = 0;  // 阵营 中立
    public static final int CAMP_LEFT = 1;     // 阵营 左方
    public static final int CAMP_RIGHT = 2;    // 阵营 右方


    private ArrayList<BattlePerson> mMembersList; // 成员列表
    private LineUpMode mZhenfa;                 // 阵法
    private int Camp = CAMP_NEUTRAL;             // 阵营，对战的两方 (左方 1，右方 2)

    /**
     * 构造函数
     * @param camp
     * @param membersList
     */
    public TeamModel(int camp,ArrayList<BattlePerson> membersList){
        this.mMembersList = membersList;
        setCamp(camp);
    }

    /**
     * 是否团灭，只要有一人存活，就不算团灭
     * @return true 团灭 ，false 非团灭
     */
    public boolean isACE() {
        if (mMembersList == null) {
            SimpleLog.loge(TAG,"isACE() mMembersList == null,"+getCamp() + " is ace");
            return true;
        }
        for (BattlePerson player : mMembersList) {
            if (player.getHP() > 0) {
                return false;
            }
        }
        BattleLog.log("Team:"+getCamp() + " is ace");
        return true;
    }

    public ArrayList<BattlePerson> getmMembersList() {
        return mMembersList;
    }

    public void setmMembersList(ArrayList<BattlePerson> mMembersList) {
        this.mMembersList = mMembersList;
    }

    public LineUpMode getmZhenfa() {
        return mZhenfa;
    }

    public void setmZhenfa(LineUpMode mZhenfa) {
        this.mZhenfa = mZhenfa;
    }

    public int getCamp() {
        return Camp;
    }

    /**
     * 设置队伍的阵营，同时设置队伍里面人员的阵营和这一场战斗的编号
     * @param camp
     * @param startId 从 startId 开始为编号，开始为每一个人物编号
     */
    public void setCampAndID(int camp,int startId) {
        Camp = camp;
        int battleId = startId;
        if (mMembersList != null && mMembersList.size() > 0) {
            for (BattlePerson player : mMembersList) {
                player.setCamp(camp);
                player.setBattleId(battleId);
                battleId++;
            }
        }
    }

    public void setCamp(int camp) {
        Camp = camp;
        if (mMembersList != null && mMembersList.size() > 0) {
            for (BattlePerson player : mMembersList) {
                player.setCamp(camp);
            }
        }
    }

    /**
     * 计算每一个成员的战斗面板属性
     */
    private void calculateBattleAttribute(){

    }
}
