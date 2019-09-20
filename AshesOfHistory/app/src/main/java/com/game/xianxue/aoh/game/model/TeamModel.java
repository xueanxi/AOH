package com.game.xianxue.aoh.game.model;

import com.game.xianxue.aoh.Log.LogUtils;
import com.game.xianxue.aoh.game.model.lineup.FormationBattle;
import com.game.xianxue.aoh.game.model.person.BattlePerson;

import java.util.ArrayList;
import java.util.List;

/**
 * 阵营模型
 */
public class TeamModel {
    //常量
    private static final String TAG = "TeamModel";

    public static final int CAMP_NEUTRAL = 0;       // 阵营 中立
    public static final int CAMP_LEFT = 1;          // 阵营 左方
    public static final int CAMP_RIGHT = 2;         // 阵营 右方


    private int mCamp = CAMP_NEUTRAL;                // 阵营，对战的两方 (左方 1，右方 2)
    private FormationBattle mLineup;                    // 阵容 ，阵容里面包含了战斗的成员

    /**
     * 构造函数
     *
     * @param mCamp
     * @param membersList
     */
    public TeamModel(int mCamp, ArrayList<BattlePerson> membersList) {
        setCamp(mCamp);
    }

    /**
     * 构造函数
     *
     * @param mCamp
     * @param lineup
     */
    public TeamModel(int mCamp, FormationBattle lineup) {
        this.mLineup = lineup;
        setCamp(mCamp);
    }

    /**
     * 是否团灭，只要有一人存活，就不算团灭
     *
     * @return true 团灭 ，false 非团灭
     */
    public boolean isAllDie() {
        if (mLineup == null){
            LogUtils.e(TAG,"isAllDie() : mLineup == null");
            return true;
        }
        return mLineup.isAllDie();
    }

    /**
     * 获得队伍成员列表
     *
     * @return
     */
    public List<BattlePerson> getMembersList() {
        if(mLineup == null){
            LogUtils.e(TAG,"getMembersList() : mLineup == null");
            return null;
        }
        return mLineup.getMemberList();
    }

    public FormationBattle getLineup() {
        return mLineup;
    }

    public void setLineup(FormationBattle mLineup) {
        this.mLineup = mLineup;
    }

    public int getCamp() {
        return mCamp;
    }

    public void setCamp(int mCamp) {
        this.mCamp = mCamp;
        List<BattlePerson> membersList = mLineup.getMemberList();   // 成员列表
        if (membersList != null && membersList.size() > 0) {
            for (BattlePerson player : membersList) {
                player.setCamp(mCamp);
            }
        }
    }

    /**
     * 计算每一个成员的战斗面板属性
     */
    private void calculateBattleAttribute() {

    }
}
