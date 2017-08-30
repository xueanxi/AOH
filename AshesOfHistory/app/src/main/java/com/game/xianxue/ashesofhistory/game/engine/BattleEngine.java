package com.game.xianxue.ashesofhistory.game.engine;

import com.game.xianxue.ashesofhistory.Log.BattleLog;
import com.game.xianxue.ashesofhistory.game.model.ActiveValueManager;
import com.game.xianxue.ashesofhistory.model.TeamModel;
import com.game.xianxue.ashesofhistory.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.utils.RandomUtils;

import java.util.ArrayList;


/**
 * 战斗处理引擎 单例
 */
public class BattleEngine {
    private static final String TAG = "BattleEngine";
    private static final Object mSyncObject = new Object();
    private static BattleEngine mInstance;
    private ActiveValueManager mActiveMode;

    // 常量
    private int TIME_INTERVAL_INCRESE_ACTIVE = 200;       // 人物每一次增加行动值的时间间隔,默认是 200 ms
    private int TIME_INTERVAL_PER_ATION = 2000;           // 人物进攻花费的时间，默认是 2000

    // 变量
    private boolean mIsBattleing = false;                // 是否正在战斗

    private int mTimePerAction = TIME_INTERVAL_PER_ATION;              // 人物进攻花费的时间，默认是 TIME_INTERVAL_PER_ATION
    private int mTimeIncreseActive = TIME_INTERVAL_INCRESE_ACTIVE;     // 人物每一次增加行动值的时间间隔

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

        mLogicThread = new SuspendThread() {
            @Override
            public void runPersonelLogic() {

                // 判断战斗情况，是否要结束战斗
                if (!mIsBattleing || (isBattleFinish(t1, t2) != 0)) {
                    BattleLog.log("检测到战斗已经分出胜负，战斗引擎尝试关闭...");
                    stopBattle();
                    return;
                }

                // 使用 ActiveValueManager 模型来控制人物的活跃值并发动进攻
                if(mActiveMode.isStart()){
                    // 如果 actionMode 已经开始 则调用 resume， 让actionMode继续执行
                    mActiveMode.resume();
                }else{
                    // 如果 actionMode 没有开始 则调用 start， 让actionMode开始执行
                    mActiveMode.start();
                }
            }
        };
        mLogicThread.start(mTimePerAction);
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
        playerLists.addAll(t1.getmMembersList());
        playerLists.addAll(t2.getmMembersList());

        t1.setCampAndID(TeamModel.CAMP_LEFT, 0);
        t2.setCampAndID(TeamModel.CAMP_RIGHT, t1.getmMembersList().size());

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
        if (t1.isACE()) {
            isLeftFail = true;
        }

        if (t2.isACE()) {
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
            if (currentActionCamp == t1.getCamp()) {
                attack(actionPerson, t1, t2);
            } else {
                attack(actionPerson, t2, t1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            if (RandomUtils.flipCoin()) {
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

        if (p1.getSkillLists() == null) {
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

        if (p1.getSkillLists() == null) {
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
        ArrayList<BattlePerson> players = p.getmMembersList();
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
        result = result * RandomUtils.getRandombetween(0.95f, 1.05f);// 造成的伤害随机波动
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
        result = result * RandomUtils.getRandombetween(0.95f, 1.05f);// 造成的伤害随机波动
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

    public int getmTimePerAction() {
        return mTimePerAction;
    }

    public void setmTimePerAction(int mTimePerAction) {
        this.mTimePerAction = mTimePerAction;
    }

    public int getmTimeIncreseActive() {
        return mTimeIncreseActive;
    }

    public void setmTimeIncreseActive(int mTimeIncreseActive) {
        this.mTimeIncreseActive = mTimeIncreseActive;
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
