package com.game.xianxue.ashesofhistory.game.engine;

import android.util.Log;

import com.game.xianxue.ashesofhistory.Log.BattleLog;
import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.TeamModel;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.game.skill.SkillBattle;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Skill;
import com.game.xianxue.ashesofhistory.utils.RandomUtils;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import java.util.ArrayList;


/**
 * 战斗处理引擎 单例
 */
public class BattleEngine implements Interface_Skill{
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

    private int mTimePersonAction = TIME_INTERVAL_PER_ATION;              // 人物进攻花费的时间，默认是 TIME_INTERVAL_PER_ATION
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
    public void stopBattle(){
        BattleLog.log("结束战斗！！！");
        if(mActiveMode!= null){
            mActiveMode.stop();
            mActiveMode = null;
        }
        if(mLogicThread != null){
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

    public void doAction(BattlePerson actionPerson){
        // 进攻
        int currentActionCamp = actionPerson.getCamp();
        try {
            if (currentActionCamp == t1.getmCamp()) {
                attack(actionPerson, t1, t2);
            } else {
                attack(actionPerson, t2, t1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void attack(BattlePerson actionPerson) {
        // TODO: 5/25/17  这里要处理阵法的Buff效果，然后再进行攻击
        // TODO: 5/25/17  这里要处理actionPlayer的技能释放效果
        int actionCamp = actionPerson.getCamp();    // 当前行动的阵营
        int effectCamp = TeamModel.CAMP_NEUTRAL;    // 技能作用的阵营
        int effectNumber = 0;                       // 本次技能影响的人数
        int effectRange = 0;                        // 本次技能影响的范围
        SkillBattle skill = null;                   // 本次发动的技能

        // TODO: 10/11/17 在处理技能释放之前，先处理BattlePerson 继承 NormalPerson
        if(RandomUtils.isHappen(RATE_SKILL_RELEASE+actionPerson.getSkillRate())){
            BattleLog.log(actionPerson.getName()+"准备进行技能攻击");
            //skill = actionPlayer.gets
        }else{
            BattleLog.log(actionPerson.getName()+"准备进行普通攻击");
            skill = actionPerson.getSkillActivesLists().get(0);
            effectNumber = skill.getEffectNumber() + actionPerson.getAttackNumberUp();
            effectRange = skill.getRange() + actionPerson.getAttackRangeUp();
            ArrayList<BattlePerson> personsBeAttackList = new ArrayList<BattlePerson>();
            TeamModel teamBeAttack = null;

            // 计算本次技能作用的阵营
            if(SKILL_CAMP_ENEMY == skill.getEffectCamp()){
                if(actionCamp == TeamModel.CAMP_LEFT){
                    effectCamp = TeamModel.CAMP_RIGHT;
                }else if(actionCamp == TeamModel.CAMP_RIGHT){
                    effectCamp = TeamModel.CAMP_LEFT;
                }
            } else if(SKILL_CAMP_FRIEND == skill.getEffectCamp()){
                if(actionCamp == TeamModel.CAMP_LEFT){
                    effectCamp = TeamModel.CAMP_LEFT;
                }else if(actionCamp == TeamModel.CAMP_RIGHT){
                    effectCamp = TeamModel.CAMP_RIGHT;
                }
            }

            // 挑选出承受此次技能的阵营是 t1 还是 t2
            if(t1.getmCamp() == effectCamp){
                teamBeAttack = t1;
            }else if(t2.getmCamp() == effectCamp){
                teamBeAttack = t2;
            }else{
                SimpleLog.loge(TAG,"Error !!! 技能找不到攻击的阵营");
            }

            // 挑选出承受此次技能的人
            if(effectRange == SKILL_RANGE_AOE){
                // 技能是全场范围
                personsBeAttackList = teamBeAttack.getMembersList();
            }else if(effectRange == SKILL_RANGE_SELF){
                // 技能只能对自己释放
                personsBeAttackList.add(actionPerson);
            }else{
                // 技能范围是具体的数字，此时需要结合此技能的攻击人数进行双重判断
                // 1.挑选出攻击范围内的人
                personsBeAttackList = teamBeAttack.getLineup().getPersonsByDistance(skill.getRange());
                // 2.查看这个技能是否是全场AOE，如果是则跳过第三步
                if(SKILL_EFFECT_NUMBER_ALL == effectNumber){
                    // 全场AOE 情况下 personsBeAttackList 就是最终的结果
                }
                // 3.如果 effectNumber 大于范围内的人数，则 personsBeAttackList 都是影响目标，不需要进一步筛选
                else if(effectNumber >= personsBeAttackList.size()){

                }
                // 4.根据此技能的攻击目标选择方式，对 personsBeAttackList 进行筛选
                else{
                    int skillTarget = skill.getEffectTarget();
                    int[] attackResultIndex;
                    ArrayList<BattlePerson> tempList = new ArrayList<BattlePerson>();
                    BattlePerson temp = null;   // 临时变量

                    int farDistance;// 最远目标的距离
                    int nearDistance;//最近目标的距离
                    int[] distanceArrays ;
                    int currentDistance ;
                    int totalNumber;// 从最近距离开始，一列一列加起来的人数

                    switch(skillTarget){
                        case SKILL_TARGET_RANDOM:
                            // 从所有攻击范围内的人里面，随机挑选出 skillTarget 个攻击目标
                            attackResultIndex = RandomUtils.getRandomTarget(personsBeAttackList.size(),skillTarget);
                            for(int i = 0;i<attackResultIndex.length;i++){
                                tempList.add(personsBeAttackList.get(attackResultIndex[i]));
                                personsBeAttackList = tempList;
                            }
                            break;
                        case SKILL_TARGET_MINI_HP:
                            // 使用冒泡排序法，对被选中的人物按照HP从少到多进行排序
                            for(int x =0;x<personsBeAttackList.size()-1;x++){
                                for(int y=x+1;y<personsBeAttackList.size();y++){
                                    if(personsBeAttackList.get(x).getHP() > personsBeAttackList.get(y).getHP()){
                                        temp = personsBeAttackList.get(x);
                                        personsBeAttackList.set(x,personsBeAttackList.get(y));
                                        personsBeAttackList.set(y,temp);
                                    }
                                }
                            }
                            // 取出HP最低的 skillTarget 个人放在 tempList
                            for(int i = 0;i< skillTarget;i++){
                                tempList.add(personsBeAttackList.get(i));
                            }
                            personsBeAttackList = tempList;
                            break;
                        case SKILL_TARGET_MAX_HP:
                            // 使用冒泡排序法，对被选中的人物按照HP从多到少进行排序
                            for(int x =0;x<personsBeAttackList.size()-1;x++){
                                for(int y=x+1;y<personsBeAttackList.size();y++){
                                    if(personsBeAttackList.get(x).getHP() < personsBeAttackList.get(y).getHP()){
                                        temp = personsBeAttackList.get(x);
                                        personsBeAttackList.set(x,personsBeAttackList.get(y));
                                        personsBeAttackList.set(y,temp);
                                    }
                                }
                            }
                            // 取出HP最低的 skillTarget 个人放在 tempList
                            for(int i = 0;i< skillTarget;i++){
                                tempList.add(personsBeAttackList.get(i));
                            }
                            personsBeAttackList = tempList;
                            break;
                        case SKILL_TARGET_DISTANCE_NEAR:
                            // 获得当前距离最近的攻击目标
                            farDistance = personsBeAttackList.get(personsBeAttackList.size()-1).getDistance(); // 最远目标的距离
                            nearDistance = personsBeAttackList.get(0).getDistance();//最近目标的距离
                            distanceArrays = new int[farDistance - nearDistance +1];
                             currentDistance = 0;
                             totalNumber = 0;// 从最近距离开始，一列一列加起来的人数
                            // 获得每个距离的人数 放在distanceArrays数组里面
                            for(int i=0;i<personsBeAttackList.size();i++){
                                currentDistance = personsBeAttackList.get(i).getDistance();
                                distanceArrays[currentDistance-1] = distanceArrays[currentDistance-1]++;
                            }


                            for(int i = 0;i<distanceArrays.length;i++){
                                totalNumber += distanceArrays[i];
                                // 作用人数，少于最近累加的人数，则开始从最近累加的人里面挑出 effectNumber 个攻击目标
                                if(effectNumber<=totalNumber){
                                    attackResultIndex = RandomUtils.getRandomTarget(totalNumber,effectNumber);
                                    for(int j = 0;j<attackResultIndex.length;j++){
                                        tempList.add(personsBeAttackList.get(attackResultIndex[j]));
                                        personsBeAttackList = tempList;
                                    }
                                    break;
                                }
                            }
                            break;
                        case SKILL_TARGET_DISTANCE_FAR:
                            // 获得当前距离最远的攻击目标
                            farDistance = personsBeAttackList.get(personsBeAttackList.size()-1).getDistance(); // 最远目标的距离
                            nearDistance = personsBeAttackList.get(0).getDistance();//最近目标的距离
                            distanceArrays = new int[farDistance - nearDistance +1];
                            currentDistance = 0;
                            totalNumber = 0;// 从最远距离开始，一列一列加起来的人数
                            // 获得每个距离的人数 放在distanceArrays数组里面
                            for(int i=0;i<personsBeAttackList.size();i++){
                                currentDistance = personsBeAttackList.get(i).getDistance();
                                distanceArrays[currentDistance-1] = distanceArrays[currentDistance-1]++;
                            }

                            for(int i = farDistance-1;i>=0;i--){
                                totalNumber += distanceArrays[i];
                                // 作用人数，少于最近累加的人数，则开始从最近累加的人里面挑出 effectNumber 个攻击目标
                                if(effectNumber<=totalNumber){
                                    attackResultIndex = RandomUtils.getRandomTarget(totalNumber,effectNumber);
                                    for(int j = 0;j<attackResultIndex.length;j++){
                                        tempList.add(personsBeAttackList.get(attackResultIndex[j]));
                                        personsBeAttackList = tempList;
                                    }
                                    break;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }


        BattlePerson beAttackPlayer = getBeAttackedPlayer(t1);//挑选出被攻击的人员
        if (beAttackPlayer == null) {
            mIsBattleing = false;
            BattleLog.log((actionCamp == TeamModel.CAMP_LEFT ? "敌方" : "我方") + " 已经没有可以战斗的人员。");
        } else {
            String actionPlayerName = actionPerson.getName();
            String beAttackedPlayName = beAttackPlayer.getName();
            BattleLog.log("=============" + actionPlayerName + " 对 " + beAttackedPlayName + "发起攻击" + "=============");
            //随机判断对方进行格档还是闪避，
            if (RandomUtils.isHappen(0.5f)) {
                attackBlock(actionPerson, beAttackPlayer);
            } else {
                attackDodge(actionPerson, beAttackPlayer);
            }
        }
    }

    public void attackNormal(BattlePerson actionPerson){
        BattleLog.log(actionPerson.getName()+"准备进行普通攻击");

        int actionCamp = actionPerson.getCamp();    // 当前行动的阵营
        int effectCamp = TeamModel.CAMP_NEUTRAL;    // 技能作用的阵营
        int effectNumber = 0;                       // 本次技能影响的人数
        int effectRange = 0;                        // 本次技能影响的范围
        SkillBattle skill = null;                   // 本次发动的技能

        skill = actionPerson.getSkillActivesLists().get(0);                          // 获得普通攻击技能
        effectNumber = skill.getEffectNumber() + actionPerson.getAttackNumberUp();   // 技能的影响人数
        effectRange = skill.getRange() + actionPerson.getAttackRangeUp();            // 技能的影响范围
        ArrayList<BattlePerson> personsBeAttackList = new ArrayList<BattlePerson>(); // 被技能攻击的目标
        TeamModel teamBeAttack = null;                                               // 被技能影响的阵营
        SimpleLog.logd(TAG,actionPerson.getName()+"的 "+ skill.getName()+" 的距离为:"+effectRange+",人数为:"+effectNumber);

        // 计算本次技能作用的阵营
        if(SKILL_CAMP_ENEMY == skill.getEffectCamp()){
            if(actionCamp == TeamModel.CAMP_LEFT){
                effectCamp = TeamModel.CAMP_RIGHT;
            }else if(actionCamp == TeamModel.CAMP_RIGHT){
                effectCamp = TeamModel.CAMP_LEFT;
            }
        } else if(SKILL_CAMP_FRIEND == skill.getEffectCamp()){
            if(actionCamp == TeamModel.CAMP_LEFT){
                effectCamp = TeamModel.CAMP_LEFT;
            }else if(actionCamp == TeamModel.CAMP_RIGHT){
                effectCamp = TeamModel.CAMP_RIGHT;
            }
        }

        // 挑选出承受此次技能的阵营是 t1 还是 t2
        if(t1.getmCamp() == effectCamp){
            teamBeAttack = t1;
        }else if(t2.getmCamp() == effectCamp){
            teamBeAttack = t2;
        }else{
            SimpleLog.loge(TAG,"Error !!! 技能找不到攻击的阵营");
        }

        // 挑选出承受此次技能的人
        if(effectRange == SKILL_RANGE_AOE){
            // 技能是全场范围
            personsBeAttackList = teamBeAttack.getMembersList();
        }else if(effectRange == SKILL_RANGE_SELF){
            // 技能只能对自己释放
            personsBeAttackList.add(actionPerson);
        }else{
            // 技能范围是具体的数字，此时需要结合此技能的攻击人数进行双重判断
            // 1.挑选出攻击范围内的人
            personsBeAttackList = teamBeAttack.getLineup().getPersonsByDistance(effectRange);
            // 2.查看这个技能是否是全场AOE，如果是则跳过第三步
            if(SKILL_EFFECT_NUMBER_ALL == effectNumber){
                // 全场AOE 情况下 personsBeAttackList 就是最终的结果
            }
            // 3.如果 effectNumber 大于范围内的人数，则 personsBeAttackList 都是影响目标，不需要进一步筛选
            else if(effectNumber >= personsBeAttackList.size()){
                // 同全场AOE，personsBeAttackList 就是最终的结果
            }
            // 4.根据此技能的攻击目标选择方式，对 personsBeAttackList 进行筛选
            else{
                int skillTarget = skill.getEffectTarget();
                int[] attackResultIndex;
                ArrayList<BattlePerson> tempList = new ArrayList<BattlePerson>();
                ArrayList<BattlePerson> tempList2 = new ArrayList<BattlePerson>();
                BattlePerson temp = null;   // 临时变量

                int farDistance;// 最远目标的距离
                int nearDistance;//最近目标的距离
                int currentDistance;// 当前遍历到的距离
                int[] distanceArrays ;
                int attackNumberRemain;// 还剩余多少个人需要攻击攻击


                // 根据这个技能的特性，挑选出可以影响到的目标，结果存放在 personsBeAttackList
                switch(skillTarget){
                    case SKILL_TARGET_RANDOM:
                        SimpleLog.logd(TAG,"技能随机选择攻击目标");
                        // 从所有攻击范围内的人里面，随机挑选出 effectNumber 个攻击目标
                        attackResultIndex = RandomUtils.getRandomTarget(personsBeAttackList.size(),effectNumber);
                        for(int i= 0;i<attackResultIndex.length;i++){
                            tempList.add(personsBeAttackList.get(attackResultIndex[i]));
                        }
                        personsBeAttackList = tempList;
                        break;
                    case SKILL_TARGET_MINI_HP:
                        SimpleLog.logd(TAG,"技能选择攻击生命低的目标");
                        // 使用冒泡排序法，对被选中的人物按照HP从少到多进行排序
                        for(int x =0;x<personsBeAttackList.size()-1;x++){
                            for(int y=x+1;y<personsBeAttackList.size();y++){
                                if(personsBeAttackList.get(x).getHP() > personsBeAttackList.get(y).getHP()){
                                    temp = personsBeAttackList.get(x);
                                    personsBeAttackList.set(x,personsBeAttackList.get(y));
                                    personsBeAttackList.set(y,temp);
                                }
                            }
                        }
                        // 取出HP最低的 effectNumber 个人放在 tempList
                        for(int i = 0;i< effectNumber;i++){
                            tempList.add(personsBeAttackList.get(i));
                        }
                        personsBeAttackList = tempList;
                        break;
                    case SKILL_TARGET_MAX_HP:
                        SimpleLog.logd(TAG,"技能选择攻击生命高的目标");
                        // 使用冒泡排序法，对被选中的人物按照HP从多到少进行排序
                        for(int x =0;x<personsBeAttackList.size()-1;x++){
                            for(int y=x+1;y<personsBeAttackList.size();y++){
                                if(personsBeAttackList.get(x).getHP() < personsBeAttackList.get(y).getHP()){
                                    temp = personsBeAttackList.get(x);
                                    personsBeAttackList.set(x,personsBeAttackList.get(y));
                                    personsBeAttackList.set(y,temp);
                                }
                            }
                        }
                        // 取出HP最低的 effectNumber 个人放在 tempList
                        for(int i = 0;i< effectNumber;i++){
                            tempList.add(personsBeAttackList.get(i));
                        }
                        personsBeAttackList = tempList;
                        break;
                    case SKILL_TARGET_DISTANCE_NEAR:
                        SimpleLog.logd(TAG,"技能选择攻击近距离目标");
                        // 获得当前距离最近的攻击目标
                        farDistance = personsBeAttackList.get(personsBeAttackList.size()-1).getDistance(); // 最远目标的距离
                        nearDistance = personsBeAttackList.get(0).getDistance();//最近目标的距离
                        distanceArrays = new int[farDistance - nearDistance +1];
                        attackNumberRemain = effectNumber;
                        tempList = new ArrayList<BattlePerson>();
                        tempList2 = new ArrayList<BattlePerson>();
                        //SimpleLog.logd(TAG,"farDistance = "+farDistance+" nearDistance="+nearDistance);
                        // 获得每个距离的人数 放在distanceArrays数组里面
                        for(int i=0;i<personsBeAttackList.size();i++){
                            currentDistance = personsBeAttackList.get(i).getDistance();
                            distanceArrays[currentDistance-1]++;
                        }
                        // 从最后的列开始，向前选择 effectNumber个目标
                        for(int i = 0;i<distanceArrays.length;i++){
                            // 如果当前还需要攻击的人数 大于 当前列的人数，则当前列的所有人都需要加到临时列表 tempList 中
                            if(attackNumberRemain > distanceArrays[i]){
                                for(int j = 0;j<personsBeAttackList.size();j++){
                                    // i 是从0开始的，但是距离是从1开始的，所以 i+1
                                    if(personsBeAttackList.get(j).getDistance() == (i+1)){
                                        tempList.add(personsBeAttackList.get(j));
                                    }
                                }
                                attackNumberRemain -= distanceArrays[i];// 剩余的需要攻击的人数
                                continue;
                            }else {
                                // 如果当前还需要攻击的人数 小于等于 当前列的人数，则从当前列随机选择出 attackNumberRemain 人，加入到临时表 tempList
                                for(int j = 0;j<personsBeAttackList.size();j++){
                                    if(personsBeAttackList.get(j).getDistance() == (i+1)){
                                        tempList2.add(personsBeAttackList.get(j));
                                    }
                                }
                                int[] result = RandomUtils.getRandomTarget(distanceArrays[i],attackNumberRemain);
                                for(int k =0;k<result.length;k++){
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
                        SimpleLog.logd(TAG,"技能选择攻击远距离目标");
                        // 获得当前距离最远的攻击目标
                        farDistance = personsBeAttackList.get(personsBeAttackList.size()-1).getDistance(); // 最远目标的距离
                        nearDistance = personsBeAttackList.get(0).getDistance();//最近目标的距离
                        distanceArrays = new int[farDistance - nearDistance +1];
                        attackNumberRemain = effectNumber;
                        tempList = new ArrayList<BattlePerson>();
                        tempList2 = new ArrayList<BattlePerson>();
                        //SimpleLog.logd(TAG,"farDistance = "+farDistance+" nearDistance="+nearDistance);
                        // 获得每个距离的人数 放在distanceArrays数组里面
                        for(int i=0;i<personsBeAttackList.size();i++){
                            currentDistance = personsBeAttackList.get(i).getDistance();
                            distanceArrays[currentDistance-1]++;
                        }
                        // 从最后的列开始，向前选择 effectNumber个目标
                        for(int i = (distanceArrays.length-1);i>=0;i--){
                            // 如果当前还需要攻击的人数 大于 当前列的人数，则当前列的所有人都需要加到临时列表 tempList 中
                            if(attackNumberRemain > distanceArrays[i]){
                                for(int j = 0;j<personsBeAttackList.size();j++){
                                    // i 是从0开始的，但是距离是从1开始的，所以 i+1
                                    if(personsBeAttackList.get(j).getDistance() == (i+1)){
                                        tempList.add(personsBeAttackList.get(j));
                                    }
                                }
                                attackNumberRemain -= distanceArrays[i];// 剩余的需要攻击的人数
                                continue;
                            }else {
                                // 如果当前还需要攻击的人数 小于等于 当前列的人数，则从当前列随机选择出 attackNumberRemain 人，加入到临时表 tempList
                                for(int j = 0;j<personsBeAttackList.size();j++){
                                    if(personsBeAttackList.get(j).getDistance() == (i+1)){
                                        tempList2.add(personsBeAttackList.get(j));
                                    }
                                }
                                int[] result = RandomUtils.getRandomTarget(distanceArrays[i],attackNumberRemain);
                                for(int k =0;k<result.length;k++){
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
                        SimpleLog.logd(TAG,"错误！！！ 技能选择攻击目标出错");
                        break;
                }
            }
        }

        // personsBeAttackList 是技能可以影响到的目标，
        ShowUtils.showArrayLists(TAG+" 普通攻击目标：",personsBeAttackList);

        //// TODO: 2017/10/15 开始分别对 personsBeAttackList 进行攻击
        a
    }

    private void attack(BattlePerson actionPlayer, TeamModel actionTeam, TeamModel beAttackedTeam) {
        // TODO: 5/25/17  这里要处理阵法的Buff效果，然后再进行攻击
        // TODO: 5/25/17  这里要处理actionPlayer的技能释放效果
        int actionCamp = TeamModel.CAMP_NEUTRAL;  // 当前行动的阵营

        BattlePerson beAttackPlayer = getBeAttackedPlayer(beAttackedTeam);//挑选出被攻击的人员
        if (beAttackPlayer == null) {
            mIsBattleing = false;
            BattleLog.log((actionCamp == TeamModel.CAMP_LEFT ? "敌方" : "我方") + " 已经没有可以战斗的人员。");
        } else {
            String actionPlayerName = actionPlayer.getName();
            String beAttackedPlayName = beAttackPlayer.getName();
            BattleLog.log("=============" + actionPlayerName + " 对 " + beAttackedPlayName + "发起攻击" + "=============");
            //随机判断对方进行格档还是闪避，
            if (RandomUtils.isHappen(0.5f)) {
                attackBlock(actionPlayer, beAttackPlayer);
            } else {
                attackDodge(actionPlayer, beAttackPlayer);
            }
        }
    }

    /**
     * 攻击尝试格档的敌人
     * 根据攻击者的命中率和敌人的格档率来计算，敌人可能可当成功也可能格档失败
     *
     * @param p1
     * @param p2
     */
    public void attackBlock(BattlePerson p1, BattlePerson p2) {
        //进行格档
        float blockPhysic = 0.3f;       //成功格档，物理攻击只承受30%伤害
        float blockMagic = 0.7f;        //成功格档，魔法攻击只承受70%伤害

        float accuracy = block(p1.getAccuracy(), p2.getBlock());

        int reduceHP = 0;
        if (RandomUtils.isHappen(accuracy)) {
            BattleLog.log(p2.getName() + "格档失败 !!! 他的格档率为" + (1f - accuracy) * 100 + "%");
            // 敌人格档失败,格档的伤害减免系数变为1
            blockPhysic = 1f;
            blockMagic = 1f;
        } else {
            BattleLog.log(p2.getName() + "格档成功 !!!  的格档率为" + (1f - accuracy) * 100 + "%");
        }

        if (p1.getSkillStrings() == null) {
            //技能列表为空，则进行普通攻击。
            BattleLog.log(p1.getName() + "的物理伤害为" + p1.getPhysicDamage() + " 真实伤害为" + p1.getRealDamage() + " " + p2.getName() + "的护甲为" + p2.getArmor());
            // 计算敌方需要减少多少生命值
            reduceHP = (int) (attack(p1.getPhysicDamage(), p2.getArmor(), p1.getPhysicsPenetrate()) * blockPhysic) + p1.getRealDamage();
        } else {
            // TODO: 5/24/17 技能攻击的代码处理
        }

        if (reduceHP <= 0) reduceHP = 0;
        int remainHp = p2.getHP() - reduceHP;
        if (remainHp <= 0) {
            remainHp = 0;
            BattleLog.log(p2.getName() + "受到了" + reduceHP + "点伤害，死亡了");
        } else {
            BattleLog.log(p2.getName() + "受到了" + reduceHP + "点伤害，剩下" + remainHp + "生命值");
        }
        p2.setHP(remainHp);
    }


    /**
     * 攻击尝试躲闪的敌人
     * 根据攻击者的命中率和敌人的躲闪率来计算，敌人可能可当成功也可能躲闪失败
     *
     * @param p1
     * @param p2
     */
    public void attackDodge(BattlePerson p1, BattlePerson p2) {
        //进行躲闪
        float blockPhysic = 0.1f;       //成功躲闪，物理攻击只承受10%伤害
        float blockMagic = 0.3f;        //成功躲闪，魔法攻击只承受30%伤害

        float accuracy = dodge(p1.getAccuracy(), p2.getDodge());


        int reduceHP = 0;
        if (RandomUtils.isHappen(accuracy)) {
            // 敌人躲闪失败,躲闪的伤害减免系数变为1
            BattleLog.log(p2.getName() + "闪避失败 ！！！ 他的躲闪率为" + (1f - accuracy) * 100 + "% 他的Dodge为" + p2.getDodge());
            blockPhysic = 1f;
            blockMagic = 1f;
        } else {
            BattleLog.log(p2.getName() + "闪避成功 ！！！ 他的躲闪率为" + (1f - accuracy) * 100 + "% 他的Dodge为" + p2.getDodge());
        }

        //p1.getSkil

        if (p1.getSkillStrings() == null) {
            //技能列表为空，则进行普通攻击。
            BattleLog.log(p1.getName() + "的物理伤害为" + p1.getPhysicDamage() + " 真实伤害为" + p1.getRealDamage() + " " + p2.getName() + "的护甲为" + p2.getArmor());
            // 计算敌方需要减少多少生命值
            reduceHP = (int) (attack(p1.getPhysicDamage(), p2.getArmor(), p1.getPhysicsPenetrate()) * blockPhysic) + p1.getRealDamage();
        } else {
            // TODO: 5/24/17 技能攻击的代码处理
        }

        if (reduceHP <= 0) reduceHP = 0;
        int remainHp = p2.getHP() - reduceHP;
        if (remainHp <= 0) {
            remainHp = 0;
            BattleLog.log(p2.getName() + "受到了" + reduceHP + "点伤害，死亡了");
        } else {
            BattleLog.log(p2.getName() + "受到了" + reduceHP + "点伤害，剩下" + remainHp + "生命值");
        }
        p2.setHP(remainHp);
    }

    /**
     * 从对方的阵营里面挑选出被攻击的目标
     *
     * @param p
     * @return
     */
    private static BattlePerson getBeAttackedPlayer(TeamModel p) {
        ArrayList<BattlePerson> players = p.getMembersList();
        for (BattlePerson player : players) {
            if (player.getHP() > 0) {
                return player;
            }
        }
        return null;
    }

    /**
     * 传入攻击力，抗性，穿透。
     * 返回造成的伤害
     *
     * @param PhysicDamage
     * @param Armor
     * @param Penetrate
     */
    public int attack(float PhysicDamage, float Armor, float Penetrate) {
        Armor -= Penetrate;
        float result = PhysicDamage * (1f - (Armor / (Armor + 200f)));
        result = result * RandomUtils.getRandomNumberbetween(0.95f, 1.05f);// 造成的伤害随机波动
        return ((int) (Math.ceil(result)));
    }

    /**
     * 传入攻击力，抗性，百分比穿透。
     * 返回造成的伤害
     *
     * @param PhysicDamage
     * @param Armor
     * @param Penetrate
     */
    public int attackWithPercent(float PhysicDamage, float Armor, float Penetrate) {
        Armor = Armor * (1f - Penetrate);
        float result = PhysicDamage * (1f - (Armor / (Armor + 200f)));
        result = result * RandomUtils.getRandomNumberbetween(0.95f, 1.05f);// 造成的伤害随机波动
        return ((int) (Math.ceil(result)));
    }

    /**
     * 传入命中值，和格档值
     * 返回 格档失败的概率，也就是进攻方，攻击成功的概率
     *
     * @param Accuracy
     * @param Block
     */
    public float block(float Accuracy, float Block) {
        return Accuracy / (Accuracy + Block);
    }

    /**
     * 传入命中值，和躲闪值
     * 返回 躲闪失败的概率，也就是进攻方，攻击成功的概率
     *
     * @param Accuracy
     * @param Dodge
     */
    public float dodge(float Accuracy, float Dodge) {
        return Accuracy / (Accuracy + Dodge);
    }

    public int getmTimePersonAction() {
        return mTimePersonAction;
    }

    public void setmTimePersonAction(int mTimePersonAction) {
        this.mTimePersonAction = mTimePersonAction;
    }

    public int getmTimeActiveIncrese() {
        return mTimeActiveIncrese;
    }

    public void setmTimeActiveIncrese(int mTimeActiveIncrese) {
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
