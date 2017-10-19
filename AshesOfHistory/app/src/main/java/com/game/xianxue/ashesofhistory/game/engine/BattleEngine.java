package com.game.xianxue.ashesofhistory.game.engine;

import com.game.xianxue.ashesofhistory.Log.BattleLog;
import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.DamgeModel;
import com.game.xianxue.ashesofhistory.game.model.TeamModel;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBattle;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.game.skill.SkillBattle;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Buff;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Skill;
import com.game.xianxue.ashesofhistory.utils.RandomUtils;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import java.util.ArrayList;


/**
 * 战斗处理引擎 单例
 */
public class BattleEngine implements Interface_Skill, Interface_Buff {
    private static final String TAG = "BattleEngine";
    private static final Object mSyncObject = new Object();
    private static BattleEngine mInstance;
    private ActiveValueManager mActiveMode;

    // 常量
    private int TIME_INTERVAL_INCRESE_ACTIVE = 200;       // 人物每一次增加行动值的时间间隔,默认是 200 ms
    private int TIME_INTERVAL_PER_ATION = 2000;           // 人物进攻花费的时间，默认是 2000
    private float RATE_SKILL_RELEASE = 0.5f;              // 人物正常情况下，技能释放的概率

    // 变量
    private boolean mIsBattleing = false;                // 是否正在战斗

    private int mTimePersonAction = TIME_INTERVAL_PER_ATION;          // 人物进攻花费的时间，默认是 TIME_INTERVAL_PER_ATION
    private int mTimeActiveIncrese = TIME_INTERVAL_INCRESE_ACTIVE;     // 人物每一次增加行动值的时间间隔

    // 执行游戏逻辑的可控制线程
    SuspendThread mLogicThread = null;

    //
    TeamModel t1, t2;
    ArrayList<BattlePerson> mPersonsList;

    private BattleEngine() {
        mInstance = this;
    }

    public static BattleEngine getInstance() {
        synchronized (mSyncObject) {
            if (null == mInstance) {
                mInstance = new BattleEngine();
            }
        }
        return mInstance;
    }

    /**
     * 开始一场战斗
     */
    public void startBattle() {
        BattleLog.log("开始战斗！！！");
        mActiveMode = new ActiveValueManager(mInstance, mPersonsList);
        mActiveMode.setTimeActiveInterval(mTimeActiveIncrese);

        mLogicThread = new SuspendThread() {
            @Override
            public void runPersonelLogic() {

                // 判断战斗情况，是否要结束战斗
                if (!mIsBattleing || (isBattleFinish(t1, t2) != 0)) {
                    BattleLog.log("检测到战斗已经分出胜负，战斗引擎尝试关闭...");
                    stopBattle();
                    return;
                }

                mActiveMode.resume();
            }
        };
        mLogicThread.start(mTimePersonAction);
    }

    /**
     * 结束战斗
     */
    public void stopBattle() {
        BattleLog.log("结束战斗！！！");
        if (mActiveMode != null) {
            mActiveMode.stop();
            mActiveMode = null;
        }
        if (mLogicThread != null) {
            mLogicThread.stop();
        }
        mIsBattleing = false;
    }

    /**
     * 战斗之前的准备，数据初始化之类的工作
     * 最后返回 所有参战的人员list
     *
     * @param t1
     * @param t2
     */
    private ArrayList<BattlePerson> prepareBattle(TeamModel t1, TeamModel t2) {
        ArrayList<BattlePerson> playerLists = new ArrayList<BattlePerson>();
        playerLists.addAll(t1.getMembersList());
        playerLists.addAll(t2.getMembersList());

        t1.setmCamp(TeamModel.CAMP_LEFT);
        t2.setmCamp(TeamModel.CAMP_RIGHT);

        mIsBattleing = true;
        return playerLists;
    }

    /**
     * @return 0 两队的生命都>0 ,战斗未结束
     * 1 主队胜利 ,战斗结束
     * 2 敌人胜利 ,战斗结束
     * 3 两败俱伤
     */
    private static int isBattleFinish(TeamModel t1, TeamModel t2) {
        boolean isLeftFail = false;
        boolean isRightFail = false;
        if (t1.isAllDie()) {
            isLeftFail = true;
        }

        if (t2.isAllDie()) {
            isRightFail = true;
        }
        if (!isLeftFail && !isRightFail) {
            BattleLog.log("战斗还在继续");
            return 0;
        } else if (!isLeftFail && isRightFail) {
            BattleLog.log("战斗结束 我军胜利");
            return 1;
        } else if (isLeftFail && !isRightFail) {
            BattleLog.log("战斗结束 敌军胜利");
            return 2;
        } else {
            BattleLog.log("战斗结束 两败俱伤");
            return 3;
        }
    }

    /**
     * 开始执行 行动
     *
     * @param actionPerson
     */
    public void doAction(BattlePerson actionPerson) {
        // TODO: 2017/10/15 这里不应该直接进行释放技能或者普通攻击，应该在执行之前，判断一下，是否有什么负面状态，导致本次行动不能正常执行的。

        // 处理Buff效果,这个每个回合都应该处理一次
        handleBuff(actionPerson);
        String aBuff = actionPerson.showActiveBuff();
        String pBuff = actionPerson.showPassiveBuff();
        BattleLog.log("主动Buff:" + aBuff);
        BattleLog.log("被动Buff:" + pBuff);

        // 刷新属性
        actionPerson.updateBattleAttribute();

        // 每回合的自动回血的数量，那些持续回血的效果，还有持续掉血的效果，都在这里统一处理
        restoreHp(actionPerson);

        if (actionPerson.getActiveSkillsList().size() == 1) {
            // 因为每个人的第一个主动技能都是普通攻击，如果技能列表里面只有一个普通攻击技能，则只能进行普通攻击
            startAttack(actionPerson, true);
        } else {
            // 如果技能数量不为一个，则需要通过随机算法，计算出释放技能和进行普通攻击的概率
            float skillProbability = 0; //  释放技能的概率
            skillProbability = RATE_SKILL_RELEASE + actionPerson.getSkillRate();// 概率 = 统一释放技能的概率+ 人物个人的 skillRate 属性
            if (RandomUtils.isHappen(skillProbability)) {
                // 释放技能
                startAttack(actionPerson, false);
            } else {
                // 进行普通攻击
                startAttack(actionPerson, true);
            }
        }
    }

    /**
     * 处理Buff效果,这个每个回合都应该处理一次
     * 1.去掉过时的buff
     * 2.处理每回合叠加的buff
     *
     * @param actionPerson
     */
    private void handleBuff(BattlePerson actionPerson) {
        // TODO: 10/17/17 处理持续buff效果
        ArrayList<BuffBattle> buffPassives = actionPerson.getPassiveBuffList();
        ArrayList<BuffBattle> buffActives = actionPerson.getActiveBuffList();
        BuffBattle buff = null;

        // 处理被动技能的Buff
        if (buffPassives != null) {
            for (int i = 0; i < buffPassives.size(); i++) {
                buff = buffPassives.get(i);
                if (buff == null) continue;

                // buff一般都有回合限制，把时间到了的清除掉
                if (BUFF_TIME_UNLIMITED != buff.getTime()) {
                    int remainTime = buff.getRemainTime();
                    if (remainTime > 0) {
                        buff.setRemainTime(remainTime--);
                    } else {
                        buffPassives.remove(i);
                    }
                }

                // 处理可以持续叠加的buff
                if (BUFF_TYPE_LAST != buff.getBuff_type()) continue;
                buff.setDuration(buff.getDuration() + 1);
            }
        }

        // 处理主动加持的buff
        if (buffActives != null) {
            for (int i = 0; i < buffActives.size(); i++) {
                buff = buffActives.get(i);
                if (buff == null) continue;

                // buff一般都有回合限制，把时间到了的清除掉
                if (BUFF_TIME_UNLIMITED != buff.getTime()) {
                    int remainTime = buff.getRemainTime();
                    if (remainTime > 0) {
                        buff.setRemainTime(remainTime--);
                    } else {
                        buffActives.remove(i);
                    }
                }

                // 处理可以持续叠加的buff
                if (BUFF_TYPE_LAST != buff.getBuff_type()) continue;
                buff.setDuration(buff.getDuration() + 1);
            }
        }
    }

    /**
     * 战斗中每回合恢复HP
     */
    private void restoreHp(BattlePerson actionPerson) {
        // TODO: 10/18/17 这个方法只是实现了，还没有测试，需要在正常战斗中测试

        // TODO: 10/18/17 那些回血的buff和掉血的buff也在这里进行处理
        int HP_Loss = actionPerson.getHP_MAX() - actionPerson.getHP_Current();       // 当前受损了多少生命值

        int HP_Current = actionPerson.getHP_Current() + actionPerson.getHpRestore(); // 恢复生命值
        if (HP_Current > actionPerson.getHP_MAX()) {
            actionPerson.setHP_Current(actionPerson.getHP_MAX());
            BattleLog.log(actionPerson.getName() + " 恢复了" + HP_Loss + "点生命值");
        } else {
            actionPerson.setHP_Current(HP_Current);
            if (actionPerson.getHpRestore() > 0) {
                BattleLog.log(actionPerson.getName() + " 恢复了" + actionPerson.getHpRestore() + "点生命值");
            } else {
                BattleLog.log(actionPerson.getName() + " 损失了" + actionPerson.getHpRestore() + "点生命值");
            }
        }
    }

    /**
     * 开始发动攻击
     *
     * @param actionPerson   执行这次行本的人，即释放技能的人
     * @param isNormalAttack 是否是进行普通攻击
     */
    public void startAttack(BattlePerson actionPerson, boolean isNormalAttack) {
        BattleLog.log(actionPerson.getName() + "发动攻击");
        BattleLog.log(actionPerson.display());

        int actionCamp = actionPerson.getCamp();    // 当前行动的阵营
        int effectCamp = TeamModel.CAMP_NEUTRAL;    // 技能作用的阵营

        SkillBattle skill = null;                   // 本次发动的技能
        ArrayList<SkillBattle> skillLists;          // 所有可以释放的技能列表
        ArrayList<SkillBattle> tempLists;           // 存放临时数据的列表

        skillLists = actionPerson.getActiveSkillsList();
        // 获取这次攻击的技能
        if (isNormalAttack) {
            skill = skillLists.get(0);
            if (skill == null) {
                SimpleLog.loge(TAG, "Error !!! 获取普通攻击技能失败，放弃这次普通攻击！！！");
                return;
            }
        } else {
            // 从技能列表里面随机选择出一个技能(排除掉普通攻击)
            if (skillLists == null || skillLists.size() == 0) {
                SimpleLog.loge(TAG, "Error !!! " + actionPerson.getName() + "的技能列表为空,停止释放技能 return");
                return;
            } else if (skillLists.size() == 1) {
                SimpleLog.loge(TAG, "Error !!! " + actionPerson.getName() + "的技能列表只有一个普通攻击，放弃技能释放，转为进行普通攻击");
                BattleLog.log(actionPerson.getName() + "的技能列表只有一个普通攻击，放弃技能释放，转为进行普通攻击");
                skill = actionPerson.getActiveSkillsList().get(0);
            } else {
                // 从技能列表里面，随机挑选出一个技能(排除掉普通攻击)
                tempLists = new ArrayList<SkillBattle>();
                StringBuilder skillStrings = new StringBuilder();
                for (int i = 1; i < skillLists.size(); i++) {
                    skillStrings.append(skillLists.get(i).getName() + " 恢复时间:" + skillLists.get(i).getRecoverTime() + " CD:" + skillLists.get(i).getCdTime() + "| ");
                    if (skillLists.get(i).isSkillCoolDown()) {
                        tempLists.add(skillLists.get(i));
                    }
                }

                BattleLog.log(actionPerson.getName() + "技能列表：" + skillStrings.toString());

                if (tempLists == null || tempLists.size() == 0) {
                    BattleLog.log(actionPerson.getName() + "想要释放技能，但是没有冷却完毕的技能，所以准备进行普通攻击。");
                    tempLists.add(actionPerson.getActiveSkillsList().get(0));
                }

                int skillIndex = RandomUtils.getRandomTarget(tempLists.size(), 1)[0];
                skill = tempLists.get(skillIndex);
            }
        }

        // 每回合恢复一点技能CD
        skillLists = actionPerson.getActiveSkillsList();
        SkillBattle skill2 = null;
        for (int i = 0; i < actionPerson.getActiveSkillsList().size(); i++) {
            skill2 = skillLists.get(i);
            if (skill2 == null) continue;
            if (skill2.getRecoverTime() > 0) {
                skillLists.get(i).setRecoverTime(skill2.getRecoverTime() - 1);
            }
        }

        int effectNumber = skill.getEffectNumber() + actionPerson.getAttackNumberUp();   // 技能的影响人数
        int effectRange = skill.getRange() + actionPerson.getAttackRangeUp();            // 技能的影响范围
        ArrayList<BattlePerson> personsBeAttackList = new ArrayList<BattlePerson>();            // 被技能攻击的目标
        TeamModel teamBeAttack = null;            // 被技能影响的阵营
        BattlePerson personBeAttack = null;       // 被技能影响的人
        BattleLog.log(actionPerson.getName() + " " + skill.getName() + "Lv." + skill.getLevel() + " 的距离为:" + effectRange + ",人数为:" + effectNumber);

        ArrayList<BattlePerson> tempList = new ArrayList<BattlePerson>();   // 存放临时数据的列表1
        ArrayList<BattlePerson> tempList2 = new ArrayList<BattlePerson>();  // 存放临时数据的列表2
        BattlePerson temp = null;                                // 临时变量


        // 计算本次技能作用的阵营
        if (SKILL_CAMP_ENEMY == skill.getEffectCamp()) {
            if (actionCamp == TeamModel.CAMP_LEFT) {
                effectCamp = TeamModel.CAMP_RIGHT;
            } else if (actionCamp == TeamModel.CAMP_RIGHT) {
                effectCamp = TeamModel.CAMP_LEFT;
            }
        } else if (SKILL_CAMP_FRIEND == skill.getEffectCamp()) {
            if (actionCamp == TeamModel.CAMP_LEFT) {
                effectCamp = TeamModel.CAMP_LEFT;
            } else if (actionCamp == TeamModel.CAMP_RIGHT) {
                effectCamp = TeamModel.CAMP_RIGHT;
            }
        }

        // 挑选出承受此次技能的阵营是 t1 还是 t2
        if (t1.getmCamp() == effectCamp) {
            teamBeAttack = t1;
        } else if (t2.getmCamp() == effectCamp) {
            teamBeAttack = t2;
        } else {
            SimpleLog.loge(TAG, "Error !!! 技能找不到攻击的阵营");
        }

        // 挑选出承受此次技能的人
        boolean isNeedSelectTarget = false;
        if (effectRange == SKILL_RANGE_AOE) {
            // 技能是全场范围
            tempList = teamBeAttack.getMembersList();
            personsBeAttackList = new ArrayList<BattlePerson>();
            for(int i =0;i<tempList.size();i++){
                if(tempList.get(i).getHP_Current() >0){
                    personsBeAttackList.add(tempList.get(i));
                }
            }
            tempList.clear();
            if (SKILL_EFFECT_NUMBER_ALL == effectNumber) {
                // 全场AOE 情况下 personsBeAttackList 就是最终的结果
                isNeedSelectTarget = false;
            }else{
                isNeedSelectTarget = true;
            }
        } else if (effectRange == SKILL_RANGE_SELF) {
            // 技能只能对自己释放
            personsBeAttackList.add(actionPerson);
            isNeedSelectTarget = false;
        } else {
            // 技能范围是具体的数字，此时需要结合此技能的攻击人数进行双重判断
            // 1.挑选出攻击范围内的人
            personsBeAttackList = teamBeAttack.getLineup().getPersonsByDistance(effectRange);
            // 2.查看这个技能是否是全场AOE，如果是则跳过第三步
            if (SKILL_EFFECT_NUMBER_ALL == effectNumber) {
                // 全场AOE 情况下 personsBeAttackList 就是最终的结果
                isNeedSelectTarget = false;
            }
            // 3.如果 effectNumber 大于范围内的人数，则 personsBeAttackList 都是影响目标，不需要进一步筛选
            else if (effectNumber >= personsBeAttackList.size()) {
                // 同全场AOE，personsBeAttackList 就是最终的结果
                isNeedSelectTarget = false;
            }
            // 4.根据此技能的攻击目标选择方式，对 personsBeAttackList 进行筛选
            else {
                isNeedSelectTarget = true;
            }
        }

        // 技能选择哪种类型的人物进行攻击
        if(isNeedSelectTarget){
            int skillTarget = skill.getEffectTarget();// 技能选择哪种类型的人物进行攻击

            int farDistance;// 最远目标的距离
            int nearDistance;//最近目标的距离
            int currentDistance;// 当前遍历到的距离
            int[] distanceArrays;// 存放每个距离人数的数组。 Ex:数组[3,4,5] 表示距离为1的有3个人，距离为2的有4个人，距离为3的有5个人。
            int attackNumberRemain;// 还剩余多少个人需要攻击
            int[] attackResultIndex;// 被技能影响的人物的索引

            // 根据这个技能的特性，挑选出可以影响到的目标，结果存放在 personsBeAttackList
            switch (skillTarget) {
                case SKILL_TARGET_RANDOM:
                    SimpleLog.logd(TAG, "技能随机选择攻击目标");
                    // 从所有攻击范围内的人里面，随机挑选出 effectNumber 个攻击目标
                    attackResultIndex = RandomUtils.getRandomTarget(personsBeAttackList.size(), effectNumber);
                    for (int i = 0; i < attackResultIndex.length; i++) {
                        tempList.add(personsBeAttackList.get(attackResultIndex[i]));
                    }
                    personsBeAttackList = tempList;
                    break;
                case SKILL_TARGET_MINI_HP:
                    SimpleLog.logd(TAG, "技能选择攻击生命低的目标");
                    // 使用冒泡排序法，对被选中的人物按照HP从少到多进行排序
                    for (int x = 0; x < personsBeAttackList.size() - 1; x++) {
                        for (int y = x + 1; y < personsBeAttackList.size(); y++) {
                            if (personsBeAttackList.get(x).getHP_Current() > personsBeAttackList.get(y).getHP_Current()) {
                                temp = personsBeAttackList.get(x);
                                personsBeAttackList.set(x, personsBeAttackList.get(y));
                                personsBeAttackList.set(y, temp);
                            }
                        }
                    }
                    // 取出HP最低的 effectNumber 个人放在 tempList
                    for (int i = 0; i < effectNumber; i++) {
                        tempList.add(personsBeAttackList.get(i));
                    }
                    personsBeAttackList = tempList;
                    break;
                case SKILL_TARGET_MAX_HP:
                    SimpleLog.logd(TAG, "技能选择攻击生命高的目标");
                    // 使用冒泡排序法，对被选中的人物按照HP从多到少进行排序
                    for (int x = 0; x < personsBeAttackList.size() - 1; x++) {
                        for (int y = x + 1; y < personsBeAttackList.size(); y++) {
                            if (personsBeAttackList.get(x).getHP_Current() < personsBeAttackList.get(y).getHP_Current()) {
                                temp = personsBeAttackList.get(x);
                                personsBeAttackList.set(x, personsBeAttackList.get(y));
                                personsBeAttackList.set(y, temp);
                            }
                        }
                    }
                    // 取出HP最低的 effectNumber 个人放在 tempList
                    for (int i = 0; i < effectNumber; i++) {
                        tempList.add(personsBeAttackList.get(i));
                    }
                    personsBeAttackList = tempList;
                    break;
                case SKILL_TARGET_DISTANCE_NEAR:
                    SimpleLog.logd(TAG, "技能选择攻击近距离目标");
                    // 获得当前距离最近的攻击目标
                    farDistance = personsBeAttackList.get(personsBeAttackList.size() - 1).getDistance(); // 最远目标的距离
                    nearDistance = personsBeAttackList.get(0).getDistance();//最近目标的距离
                    distanceArrays = new int[farDistance - nearDistance + 1];
                    attackNumberRemain = effectNumber;
                    tempList = new ArrayList<BattlePerson>();
                    tempList2 = new ArrayList<BattlePerson>();
                    //SimpleLog.logd(TAG,"farDistance = "+farDistance+" nearDistance="+nearDistance);
                    // 获得每个距离的人数 放在distanceArrays数组里面
                    for (int i = 0; i < personsBeAttackList.size(); i++) {
                        currentDistance = personsBeAttackList.get(i).getDistance();
                        distanceArrays[currentDistance - 1]++;
                    }
                    // 从最后的列开始，向前选择 effectNumber个目标
                    for (int i = 0; i < distanceArrays.length; i++) {
                        // 如果当前还需要攻击的人数 大于 当前列的人数，则当前列的所有人都需要加到临时列表 tempList 中
                        if (attackNumberRemain > distanceArrays[i]) {
                            for (int j = 0; j < personsBeAttackList.size(); j++) {
                                // i 是从0开始的，但是距离是从1开始的，所以 i+1
                                if (personsBeAttackList.get(j).getDistance() == (i + 1)) {
                                    tempList.add(personsBeAttackList.get(j));
                                }
                            }
                            attackNumberRemain -= distanceArrays[i];// 剩余的需要攻击的人数
                            continue;
                        } else {
                            // 如果当前还需要攻击的人数 小于等于 当前列的人数，则从当前列随机选择出 attackNumberRemain 人，加入到临时表 tempList
                            for (int j = 0; j < personsBeAttackList.size(); j++) {
                                if (personsBeAttackList.get(j).getDistance() == (i + 1)) {
                                    tempList2.add(personsBeAttackList.get(j));
                                }
                            }
                            int[] result = RandomUtils.getRandomTarget(distanceArrays[i], attackNumberRemain);
                            for (int k = 0; k < result.length; k++) {
                                tempList.add(tempList2.get(result[k]));
                            }
                            attackNumberRemain = 0;
                            break;
                        }
                    }
                    personsBeAttackList.clear();
                    personsBeAttackList = tempList;
                    break;
                case SKILL_TARGET_DISTANCE_FAR:
                    SimpleLog.logd(TAG, "技能选择攻击远距离目标");
                    // 获得当前距离最远的攻击目标
                    farDistance = personsBeAttackList.get(personsBeAttackList.size() - 1).getDistance(); // 最远目标的距离
                    nearDistance = personsBeAttackList.get(0).getDistance();//最近目标的距离
                    distanceArrays = new int[farDistance - nearDistance + 1];
                    attackNumberRemain = effectNumber;
                    tempList = new ArrayList<BattlePerson>();
                    tempList2 = new ArrayList<BattlePerson>();
                    //SimpleLog.logd(TAG,"farDistance = "+farDistance+" nearDistance="+nearDistance);
                    // 获得每个距离的人数 放在distanceArrays数组里面
                    for (int i = 0; i < personsBeAttackList.size(); i++) {
                        currentDistance = personsBeAttackList.get(i).getDistance();
                        distanceArrays[currentDistance - 1]++;
                    }
                    // 从最后的列开始，向前选择 effectNumber个目标
                    for (int i = (distanceArrays.length - 1); i >= 0; i--) {
                        // 如果当前还需要攻击的人数 大于 当前列的人数，则当前列的所有人都需要加到临时列表 tempList 中
                        if (attackNumberRemain > distanceArrays[i]) {
                            for (int j = 0; j < personsBeAttackList.size(); j++) {
                                // i 是从0开始的，但是距离是从1开始的，所以 i+1
                                if (personsBeAttackList.get(j).getDistance() == (i + 1)) {
                                    tempList.add(personsBeAttackList.get(j));
                                }
                            }
                            attackNumberRemain -= distanceArrays[i];// 剩余的需要攻击的人数
                            continue;
                        } else {
                            // 如果当前还需要攻击的人数 小于等于 当前列的人数，则从当前列随机选择出 attackNumberRemain 人，加入到临时表 tempList
                            for (int j = 0; j < personsBeAttackList.size(); j++) {
                                if (personsBeAttackList.get(j).getDistance() == (i + 1)) {
                                    tempList2.add(personsBeAttackList.get(j));
                                }
                            }
                            int[] result = RandomUtils.getRandomTarget(distanceArrays[i], attackNumberRemain);
                            for (int k = 0; k < result.length; k++) {
                                tempList.add(tempList2.get(result[k]));
                            }
                            attackNumberRemain = 0;
                            break;
                        }
                    }
                    personsBeAttackList.clear();
                    personsBeAttackList = tempList;
                    break;
                default:
                    SimpleLog.logd(TAG, "错误！！！ 技能选择攻击目标出错");
                    break;
            }
        }

        ShowUtils.showArrayLists(TAG + " 攻击目标：", personsBeAttackList);

        if (personsBeAttackList == null) {
            SimpleLog.loge(TAG, "startNormalAttack() 获取不到攻击目标：personsBeAttackList == null");
            return;
        }

        //开始分别对 personsBeAttackList 列表中的每一个人进行攻击
        for (int i = 0; i < personsBeAttackList.size(); i++) {
            personBeAttack = personsBeAttackList.get(i);
            attackSinglePerson(actionPerson, personBeAttack, skill);
        }
    }

    /**
     * actionPerson 对 beAttackPerson 发动普通攻击
     *
     * @param actionPerson   发动普通攻击的人
     * @param beAttackPerson 被普通攻击的人
     * @param skill          普通攻击这个技能
     */
    private void attackSinglePerson(BattlePerson actionPerson, BattlePerson beAttackPerson, SkillBattle skill) {

        float accuracy = 0;             // 本次技能的命中率
        int reduceHP = 0;               // 本次技能减少目标多少HP

        // 技能攻击次数
        // TODO: 2017/10/19 这里还要处理每次攻击的加强
        for (int i = 0; i < skill.getAttackTime(); i++) {
            // 获得此次技能的命中率
            if (SKILL_ACCURACY_RATE_MUST_SUCCESS == skill.getAccuracyRate()) {
                // 必中系列的技能
                accuracy = 1f;
            } else {
                // 本次的命中值为：人物命中值 * 技能命中率
                float skillAccuracy = actionPerson.getAccuracy() * skill.getAccuracyRate();
                // 最终命中率为:我方命中率/(我方命中率+ 对方的躲闪值)
                accuracy = skillAccuracy / (skillAccuracy + beAttackPerson.getDodge());
            }

            // 使用随机算法，判断是否命中
            if (RandomUtils.isHappen(accuracy)) {
                // 攻击命中
                int damage = 0;
                float penetrate = 0;

                // 是否暴击
                float criteValue = actionPerson.getCriteRate() * skill.getCriteRate();
                float criteRate = criteValue / (criteValue + beAttackPerson.getReduceBeCriteRate());
                boolean isCrite = RandomUtils.isHappen(criteRate);
                String criteString = "";
                String criteString2 = "";
                if (isCrite) {
                    criteString = "(暴击)";
                    criteString2 = "(暴击率" + criteRate + ")--->结果：暴击";
                } else {
                    criteString2 = "(暴击率" + criteRate + ")--->结果：未暴击";
                }
                BattleLog.log(actionPerson.getName() + " " + skill.getName() + "(命中率" + accuracy + ") ---> " + beAttackPerson.getName() + " 结果：命中 " + criteString2);

                switch (skill.getDamageType()) {
                    case SKILL_DAMAGE_TYPE_PHYSICS:
                        // 技能伤害为物理伤害
                        damage = (int) (skill.getDamageConstant() + (skill.getDamageFluctuate() * actionPerson.getPhysicDamage()));
                        if (isCrite) damage = (int) (damage * actionPerson.getCriteDamage());
                        reduceHP = DamgeModel.getDamageResult(damage, actionPerson.getPhysicsPenetrate(), skill.getDamagePenetrate(), beAttackPerson.getArmor(), false);
                        BattleLog.log(skill.getName() + " 物理伤害为" + damage + criteString + " " + beAttackPerson.getName() + " 护甲：" + beAttackPerson.getArmor() + "最终伤害:" + reduceHP);
                        break;
                    case SKILL_DAMAGE_TYPE_MAGIC:
                        // 技能伤害为魔法伤害
                        damage = (int) (skill.getDamageConstant() + (skill.getDamageFluctuate() * actionPerson.getMagicDamage()));
                        if (isCrite) damage = (int) (damage * actionPerson.getCriteDamage());
                        reduceHP = DamgeModel.getDamageResult(damage, actionPerson.getMagicPenetrate(), skill.getDamagePenetrate(), beAttackPerson.getMagicResist(), false);
                        BattleLog.log(skill.getName() + " 魔法伤害为" + damage + criteString + " " + beAttackPerson.getName() + " 魔抗：" + beAttackPerson.getArmor() + "最终伤害:" + reduceHP);
                        break;
                    case SKILL_DAMAGE_TYPE_REAL:
                        // 技能伤害为真实伤害
                        damage = (int) (skill.getDamageConstant() + (skill.getDamageFluctuate() * actionPerson.getRealDamage()));
                        if (isCrite) damage = (int) (damage * actionPerson.getCriteDamage());
                        reduceHP = DamgeModel.getRealDamageResult(damage);
                        BattleLog.log(skill.getName() + " 真实伤害为" + damage + criteString + " " + beAttackPerson.getName() + "最终伤害:" + reduceHP);
                        break;
                    case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT:
                        // 技能伤害为当前生命百分比物理伤害
                        reduceHP = DamgeModel.getPercentDamageResult(SKILL_DAMAGE_TYPE_PHYSICS_PERCENT,
                                skill.getDamageConstant(), actionPerson.getPhysicsPenetrate(), skill.getDamagePenetrate()
                                , beAttackPerson.getHP_Current(), 0, beAttackPerson.getArmor());
                        BattleLog.log(skill.getName() + " 当前生命" + (int) (skill.getDamageConstant() * 100) + "%物理伤害 " + beAttackPerson.getName() + "当前HP：" + beAttackPerson.getHP_Current() + " 护甲:" + beAttackPerson.getArmor() + " 最终伤害:" + reduceHP);
                        break;
                    case SKILL_DAMAGE_TYPE_MAGIC_PERCENT:
                        // 技能伤害为当前生命百分比魔法伤害
                        reduceHP = DamgeModel.getPercentDamageResult(SKILL_DAMAGE_TYPE_MAGIC_PERCENT,
                                skill.getDamageConstant(), actionPerson.getMagicPenetrate(), skill.getDamagePenetrate()
                                , beAttackPerson.getHP_Current(), 0, beAttackPerson.getMagicResist());
                        BattleLog.log(skill.getName() + " 当前生命" + (int) (skill.getDamageConstant() * 100) + "%魔法伤害 " + beAttackPerson.getName() + "当前HP：" + beAttackPerson.getHP_Current() + " 魔抗:" + beAttackPerson.getMagicResist() + " 最终伤害:" + reduceHP);
                        break;
                    case SKILL_DAMAGE_TYPE_REAL_PERCENT:
                        // 技能伤害为当前生命百分比真实伤害
                        reduceHP = DamgeModel.getPercentDamageResult(SKILL_DAMAGE_TYPE_REAL_PERCENT,
                                skill.getDamageConstant(), 0, 0, beAttackPerson.getHP_Current(), 0, 0);
                        BattleLog.log(skill.getName() + " 当前生命" + (int) (skill.getDamageConstant() * 100) + "%真实伤害 " + beAttackPerson.getName() + "当前HP：" + beAttackPerson.getHP_Current() + " 最终伤害:" + reduceHP);
                        break;
                    case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT_MAX:
                        // 技能伤害为最大生命百分比物理伤害
                        reduceHP = DamgeModel.getPercentDamageResult(SKILL_DAMAGE_TYPE_PHYSICS_PERCENT_MAX,
                                skill.getDamageConstant(), actionPerson.getPhysicsPenetrate(), skill.getDamagePenetrate()
                                , 0, beAttackPerson.getHP_MAX(), beAttackPerson.getArmor());
                        BattleLog.log(skill.getName() + " 最大生命百分" + (int) (skill.getDamageConstant() * 100) + "%物理伤害 " + beAttackPerson.getName() + "最大HP：" + beAttackPerson.getHP_MAX() + " 护甲:" + beAttackPerson.getArmor() + " 最终伤害:" + reduceHP);
                        break;
                    case SKILL_DAMAGE_TYPE_MAGIC_PERCENT_MAX:
                        // 技能伤害为最大生命百分比魔法伤害
                        reduceHP = DamgeModel.getPercentDamageResult(SKILL_DAMAGE_TYPE_MAGIC_PERCENT_MAX,
                                skill.getDamageConstant(), actionPerson.getMagicPenetrate(), skill.getDamagePenetrate()
                                , 0, beAttackPerson.getHP_MAX(), beAttackPerson.getMagicResist());
                        BattleLog.log(skill.getName() + " 最大生命百分" + (int) (skill.getDamageConstant() * 100) + "%魔法伤害 " + beAttackPerson.getName() + "最大HP：" + beAttackPerson.getHP_MAX() + " 魔抗:" + beAttackPerson.getMagicResist() + " 最终伤害:" + reduceHP);
                        break;
                    case SKILL_DAMAGE_TYPE_REAL_PERCENT_MAX:
                        // 技能伤害为最大生命百分比真实伤害
                        reduceHP = DamgeModel.getPercentDamageResult(SKILL_DAMAGE_TYPE_REAL_PERCENT_MAX,
                                skill.getDamageConstant(), 0, 0, 0, beAttackPerson.getHP_MAX(), 0);
                        BattleLog.log(skill.getName() + " 最大生命百分" + (int) (skill.getDamageConstant() * 100) + "%真实伤害 " + beAttackPerson.getName() + "最大HP：" + beAttackPerson.getHP_MAX() + "最终伤害:" + reduceHP);
                        break;
                    default:
                        reduceHP = 0;
                        break;
                }

                if (reduceHP <= 0) reduceHP = 0;
                int remainHp = beAttackPerson.getHP_Current() - reduceHP;
                if (remainHp <= 0) {
                    remainHp = 0;
                    BattleLog.log(beAttackPerson.getName() + "受到了" + reduceHP + "点伤害，死亡了");
                    beAttackPerson.personDie();
                } else {
                    BattleLog.log(beAttackPerson.getName() + "受到了" + reduceHP + "点伤害，剩下" + remainHp + "生命值");
                }
                beAttackPerson.setHP_Current(remainHp);

            } else {
                // 攻击被躲闪
                BattleLog.log(actionPerson.getName() + " " + skill.getName() + "(命中率" + accuracy + ") ---> " + beAttackPerson.getName() + " 结果：躲闪");
            }
        }
        BattleLog.log("=================");
    }

    public int getTimePersonAction() {
        return mTimePersonAction;
    }

    public void setTimePersonAction(int mTimePersonAction) {
        this.mTimePersonAction = mTimePersonAction;
    }

    public int getTimeActiveIncrese() {
        return mTimeActiveIncrese;
    }

    public void setTimeActiveIncrese(int mTimeActiveIncrese) {
        this.mTimeActiveIncrese = mTimeActiveIncrese;
    }

    public TeamModel getT1() {
        return t1;
    }

    public TeamModel getT2() {
        return t2;
    }


    public void setBattleTeam(TeamModel t1, TeamModel t2) {
        this.t1 = t1;
        this.t2 = t2;
        this.mPersonsList = prepareBattle(this.t1, this.t2);
    }

    public ArrayList<BattlePerson> getmPersonsList() {
        return mPersonsList;
    }
}
