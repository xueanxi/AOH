package com.game.xianxue.ashesofhistory.model;

import com.game.xianxue.ashesofhistory.model.PlayerModel;

import java.util.ArrayList;

/**
 * 队伍模型
 */
public class TeamModel {
    //常量
    private static final String TAG = "ProcessionModel";

    public static final int CAMP_NEUTRAL = 0;  // 阵营 中立
    public static final int CAMP_LEFT = 1;     // 阵营 左方
    public static final int CAMP_RIGHT = 2;    // 阵营 右方


    private ArrayList<PlayerModel> mPlayerList;  // 角色列表
    private ZhenFaModel mZhenfa;                 // 阵法
    private int Camp = CAMP_NEUTRAL;            // 阵营，对战的两方 (左方 1，右方 2)

    public TeamModel(int camp,ArrayList<PlayerModel> playerList){
        this.mPlayerList = playerList;
        setCamp(camp);
    }

    /**
     * 是否团灭，只要有一人存活，就不算团灭
     *
     * @return
     */
    public boolean isACE() {
        if (mPlayerList == null) {
            return true;
        }
        for (PlayerModel player : mPlayerList) {
            if (player.getHP() > 0) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<PlayerModel> getmPlayerList() {
        return mPlayerList;
    }

    public void setmPlayerList(ArrayList<PlayerModel> mPlayerList) {
        this.mPlayerList = mPlayerList;
    }

    public ZhenFaModel getmZhenfa() {
        return mZhenfa;
    }

    public void setmZhenfa(ZhenFaModel mZhenfa) {
        this.mZhenfa = mZhenfa;
    }

    public int getCamp() {
        return Camp;
    }

    /**
     * 设置队伍的阵营，同时设置队伍里面人员的阵营
     * @param camp
     */
    public void setCamp(int camp) {
        Camp = camp;
        if (mPlayerList != null && mPlayerList.size() > 0) {
            for (PlayerModel player : mPlayerList) {
                player.setCamp(camp);
            }
        }
    }
}
