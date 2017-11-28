package com.game.xianxue.ashesofhistory.game.model.person;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBase;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBattle;
import com.game.xianxue.ashesofhistory.interfaces.Interface_LineUp;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import java.util.ArrayList;

/**
 * 人物战斗时的模型
 * 比普通状态下的模型多了一些战斗中才会有的属性
 */
public class BattlePerson extends NormalPerson {
    private static final String TAG = "BattlePerson";

    // 战斗属性，只有在战斗时才会有的属性
    private int battleId;                   // 一场战斗中分配的id
    private int lineupId;                   // 在阵型中的位置id
    private int activeValues;               // 当前的行动值，当行动值达到最大行动值之后，就可以行动。
    private int camp;                       // 阵营
    private boolean isLeader = false;       // 是否统帅
    private boolean isCounsellor = false;   // 是否军师
    private int distance;                   // 与攻击者的距离
    public int HP_Current;                  // 当前生命值
    private Interface_LineUp lineUp;        // 阵型的接口

    ArrayList<BuffBattle> activeBuffList = null; // 主动加持的buff(指战斗中，使用技能加持的)

    public BattlePerson() {
    }

    /**
     * 构造函数 2
     *
     * @param person
     * @param camp
     */
    public BattlePerson(NormalPerson person, int camp) {
        // 原始资料
        this.psersonId = person.getPsersonId();                 // id唯一标识这个人物
        this.aptitude = person.getAptitude();                   // 资质（影响每次升级 基础属性的增加数量 资质取值为1～5 1为庸碌无为 5为天神下凡）
        this.name = person.getName();                           // 名字
        this.name2 = person.getName2();                         // 名字拼音
        this.sexuality = person.getSexuality();                 // 男1 女0

        // 获得原始属性
        this.strength_Raw = person.getStrength_Raw();           // 原始力量
        this.intellect_Raw = person.getIntellect_Raw();         // 原始智力
        this.dexterity_Raw = person.getDexterity_Raw();         // 原始敏捷
        this.physique_Raw = person.getPhysique_Raw();           // 原始体质
        this.spirit_Raw = person.getSpirit_Raw();               // 原始精神
        this.fascination_Raw = person.getFascination_Raw();     // 原始魅力
        this.luck_Raw = person.getLuck_Raw();                   // 原始运气
        this.skillStrings = person.getSkillStrings();           // 原始技能字符串
        this.leadSkillId = person.getLeadSkillId();             // 领导技能

        // 初始化技能
        setLevel(person.level);
        initSkill();

        // 刷新属性
        updateAttribute();

        // 初始战斗相关的数据
        this.activeValues = 0;// 战斗活跃值初始为0
        this.camp = camp;
    }

    /**
     * 构造函数 2
     *
     * @param person
     */
    public BattlePerson(NormalPerson person) {
        // 原始资料
        this.psersonId = person.getPsersonId();                 // id唯一标识这个人物
        this.aptitude = person.getAptitude();                   // 资质（影响每次升级 基础属性的增加数量 资质取值为1～5 1为庸碌无为 5为天神下凡）
        this.name = person.getName();                           // 名字
        this.name2 = person.getName2();                         // 名字拼音
        this.sexuality = person.getSexuality();                 // 男1 女0

        // 获得原始属性
        this.strength_Raw = person.getStrength_Raw();           // 原始力量
        this.intellect_Raw = person.getIntellect_Raw();         // 原始智力
        this.dexterity_Raw = person.getDexterity_Raw();         // 原始敏捷
        this.physique_Raw = person.getPhysique_Raw();           // 原始体质
        this.spirit_Raw = person.getSpirit_Raw();               // 原始精神
        this.fascination_Raw = person.getFascination_Raw();     // 原始魅力
        this.luck_Raw = person.getLuck_Raw();                   // 原始运气
        this.skillStrings = person.getSkillStrings();           // 获得原始技能列表
        this.leadSkillId = person.getLeadSkillId();             // 领导技能

        // 初始化技能
        setLevel(person.level);
        initSkill();

        // 刷新属性
        updateAttribute();

        // 初始战斗相关的数据
        this.HP_Current = HP_MAX;                               // 当前的生命值
        this.activeValues = 0;                                  // 战斗活跃值初始为0
    }

    /**
     * 初始化
     */
    public void init(int battleId) {
        this.activeValues = 0;
        this.battleId = battleId;
    }

    /**
     * 刷新战斗时的属性
     */
    public void updateBattleAttribute() {
        resetAllAttribute();// 重置所有属性

        // TODO: 8/29/17 处理影响 基础属性 的效果比如增加力量的装备等等
        // TODO：处理基础属性 包括 被动技能中涉及基础属性的部分、武器装备中涉及基础属性的部分、阵型中涉及基础属性的部分

        // 先处理基础属性的buff，因为面板属性是由基础属性计算出来的
        calculateBaseAttributeFromPassiveBuffList();
        calculateBaseAttributeFormActiveBuffList();
        calculateBasicPencent();//把基础属性百分比加成 计算到属性里面

        // 通过基础属性，计算面板属性
        HP_MAX = calculateHpMax();                                 // 最大生命值
        physicDamage = calculatePhysicDamage();                 // 物理伤害
        magicDamage = calculateMagicDamage();                   // 魔法伤害
        realDamage = calculateRealDamage();                     // 真实伤害
        physicsPenetrate = calculatePhysicsPenetrate();         // 物理穿透
        magicPenetrate = calculateMagicPenetrate();             // 魔法穿透
        accuracy = calculateAccuracy();                         // 命中值
        criteRate = calculateCriteRate();                       // 暴击值
        reduceBeCriteRate = calculatereduceBeCriteRate();       // 减少被暴击的值
        criteDamage = calculateCriteDamege();                   // 暴击伤害
        armor = calculateArmor();                               // 护甲（物抗）
        magicResist = calculateMagicResist();                   // 魔抗
        dodge = calculateDodge();                               // 闪避值
        actionSpeed = calculateSpeed();                         // 速度
        hpRestore = calculateHpRestore();                       // 发起进攻时，生命恢复
        actionValuesMax = calculateMaxActiveValues();           // 执行一次行动，需要的行动值（越少越好）

        // TODO: 8/29/17 处理 面板属性 的效果
        // 以下处理面板属性的加成
        calculatePanelAttributeFromPassiveBuffList();
        calculatePanelAttributeFromActiveBuffList();


        calculatePanelPencent();//把面板百分比加成 计算到人物属性里面
    }

    /**
     * 从 ActiveBuffList 里面，找出对基础属性有加成的buff，并且处理
     * 基础属性包括：力量，智力，敏捷，精神，体质，魅力，运气 这七个
     */
    private void calculateBaseAttributeFormActiveBuffList() {
        if (activeBuffList == null) return;

        // 通过for循环来处理每一个buff
        for (BuffBattle buff : activeBuffList) {
            // 一个buff可能有多项加成，所以通过for循环来遍历
            for (int i = 0; i < buff.getBuff_effect().length; i++) {
                int buffEffect = buff.getBuff_effect()[i];
                if (BuffBattle.isBisisBuff(buffEffect)) {
                    switch (buffEffect) {
                        case BUFF_STRENGTH:
                            strength += buff.getBuff_constant()[i];
                            strengthRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_INTELLECT:
                            intellect += buff.getBuff_constant()[i];
                            intellectRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_DEXTERITY:
                            dexterity += buff.getBuff_constant()[i];
                            dexterityRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_PHYSIQUE:
                            physique += buff.getBuff_constant()[i];
                            physiqueRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_SPIRIT:
                            spirit += buff.getBuff_constant()[i];
                            spiritRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_FASCINATION:
                            fascination += buff.getBuff_constant()[i];
                            fascinationRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_LUCK:
                            luck += buff.getBuff_constant()[i];
                            luckRate += buff.getBuff_fluctuate()[i];
                            break;
                    }
                }
            }
        }
    }

    /**
     * 从 ActiveBuffList 里面获取可以提升面板属性的buff，并且处理
     * 处理方式：
     * 通过循环遍历所有被动buff
     * 1.buff固定部分直接加到属性里面就好
     * 2.buff的浮动部分是百分比，需要把所有加起来，再计算到属性里面
     */
    protected void calculatePanelAttributeFromActiveBuffList() {
        if (activeBuffList == null) return;

        // 遍历buff数组，把buff的加成加到属性里面
        for (BuffBattle buff : activeBuffList) {
            // 一个buff可能有多项加成，所以通过for循环来遍历
            for (int i = 0; i < buff.getBuff_effect().length; i++) {
                int buffEffect = buff.getBuff_effect()[i];
                if (!BuffBattle.isBisisBuff(buffEffect)) {
                    switch (buffEffect) {
                        case BUFF_PHYSICDAMAGE:
                            // buff 中的固定部分直接加到 physicDamage 中就可以
                            // buff 中的浮动部分先加到 physicDamageRate ，在最后再统一计算
                            physicDamage += buff.getBuff_constant()[i];
                            physicDamageRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_MAGICDAMAGE:
                            magicDamage += buff.getBuff_constant()[i];
                            magicDamageRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_REALDAMAGE:
                            realDamage += buff.getBuff_constant()[i];
                            realDamageRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_PHYSICSPENETRATE:
                            physicsPenetrate += buff.getBuff_constant()[i];
                            physicsPenetrateRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_MAGICPENETRATE:
                            magicPenetrate += buff.getBuff_constant()[i];
                            magicPenetrateRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_ACCURACY:
                            accuracy += buff.getBuff_constant()[i];
                            accuracyRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_CRITERATE:
                            criteRate += buff.getBuff_constant()[i];
                            criteRateRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_CRITEDAMAGE:
                            criteDamage += buff.getBuff_constant()[i];
                            criteDamageRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_ARMOR:
                            armor += buff.getBuff_constant()[i];
                            armorRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_MAGICRESIST:
                            magicResist += buff.getBuff_constant()[i];
                            magicResistRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_DODGE:
                            dodge += buff.getBuff_constant()[i];
                            dodgeRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_ACTIONSPEED:
                            actionSpeed += buff.getBuff_constant()[i];
                            actionSpeedRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_HPRESTORE:
                            hpRestore += buff.getBuff_constant()[i];
                            hpRestoreRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_ACTIVEVALUE:
                            actionValuesMax += buff.getBuff_constant()[i];
                            actionValuesMaxRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_REDUCEBECRITERATE:
                            reduceBeCriteRate += buff.getBuff_constant()[i];
                            reduceBeCriteRateRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_HP_MAX:
                            HP_MAX += buff.getBuff_constant()[i];
                            HPMAXRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_SKILL_RATE:
                            skillRate += buff.getBuff_constant()[i];
                            skillRateRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_ATTACK_NUMBER:
                            attackNumberUp += buff.getBuff_constant()[i];
                            attackNumberRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_ATTACK_RANGE:
                            attackRangeUp += buff.getBuff_constant()[i];
                            attackRangeRate += buff.getBuff_fluctuate()[i];
                            break;
                        case BUFF_REDUCE_HP:
                            // 这种掉血的buff，不属于人物的属性，是战斗属性，在战斗中处理。
                            break;
                    }
                }
            }
        }
    }

    /**
     * 在战斗中加持Buff
     *
     * @param buff
     */
    public void addBuffInBattle(BuffBattle buff) {
        if (activeBuffList == null) {
            activeBuffList = new ArrayList<BuffBattle>();
        }

        // 如果有相同的buff，则先移除掉旧的
        for (int i = 0; i < activeBuffList.size(); i++) {
            if (activeBuffList.get(i).getBuffId() == buff.getBuffId()) {
                activeBuffList.remove(i);
                break;
            }
        }
        // 增加buff
        activeBuffList.add(buff);

        // 刷新属性
        updateBattleAttribute();
    }

    /**
     * 移除buff 参数为一个buff
     *
     * @param buff
     */
    public void removeBuffInBattle(BuffBase buff) {
        if (activeBuffList == null) {
            activeBuffList = new ArrayList<BuffBattle>();
        }

        // 如果有相同的buff，则先移除掉旧的
        for (int i = 0; i < activeBuffList.size(); i++) {
            if (activeBuffList.get(i).getBuffId() == buff.getBuffId()) {
                activeBuffList.remove(i);
                break;
            }
        }

        // 刷新属性
        updateBattleAttribute();
    }

    /**
     * 移除buff 参数为Buff id
     *
     * @param buffID
     */
    public void removeBuffInBattle(int buffID) {
        if (activeBuffList == null) {
            activeBuffList = new ArrayList<BuffBattle>();
        }

        // 如果有相同的buff，则移除掉的
        for (int i = 0; i < activeBuffList.size(); i++) {
            if (activeBuffList.get(i).getBuffId() == buffID) {
                activeBuffList.remove(i);
                break;
            }
        }

        // 刷新属性
        updateBattleAttribute();
    }

    public String showActiveBuff() {
        StringBuilder result = new StringBuilder();
        if (activeBuffList == null || activeBuffList.size() == 0) {
            result.append("没有主动加持的Buff");
        } else {
            BuffBattle buff;
            for (int i = 0; i < activeBuffList.size(); i++) {
                buff = activeBuffList.get(i);
                result.append(buff.getName() + " Lv." + buff.getLevel() + " dur:" + buff.getDuration()
                        + "constant:" + buff.getBuff_constant()[0] + "fluctuate:" + buff.getBuff_fluctuate()[0] + "\n");
            }
        }
        return result.toString();
    }

    public String showPassiveBuff() {
        StringBuilder result = new StringBuilder();
        if (passiveBuffList == null || passiveBuffList.size() == 0) {
            result.append("没有主动加持的Buff");
        } else {
            BuffBattle buff;
            for (int i = 0; i < passiveBuffList.size(); i++) {
                buff = passiveBuffList.get(i);
                result.append(buff.getName() + " Lv." + buff.getLevel() + " dur:" + buff.getDuration()
                        + "constant:" + buff.getBuff_constant()[0] + "fluctuate:" + buff.getBuff_fluctuate()[0] + "\n");
            }
        }
        return result.toString();
    }

    public int getActiveValues() {
        return activeValues;
    }

    public void setActiveValues(int activeValues) {
        this.activeValues = activeValues;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getCamp() {
        return camp;
    }

    public void setCamp(int camp) {
        this.camp = camp;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * 计算当前活跃值的百分比
     *
     * @return
     */
    public float getActiveValuePencent() {
        return (float) activeValues / (float) actionValuesMax;
    }

    /**
     * 执行行动之后，需要消耗掉活跃值
     */
    public void reduceActiveValue() {
        int newValues = activeValues - actionValuesMax;
        if (newValues <= 0) newValues = 0;
        activeValues = newValues;
    }

    /**
     * 战斗中，隔一段时间会增加活跃值
     */
    public void increaseActiveValues() {
        activeValues += actionSpeed;
    }

    public int getLineupId() {
        return lineupId;
    }

    public void setLineupId(int lineupId) {
        this.lineupId = lineupId;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public boolean isCounsellor() {
        return isCounsellor;
    }

    public void setCounsellor(boolean counsellor) {
        isCounsellor = counsellor;
    }

    public int getHP_Current() {
        return HP_Current;
    }

    public void setHP_Current(int HP_Current) {
        if (HP_Current >= HP_MAX) {
            HP_Current = this.HP_MAX;
        }
        this.HP_Current = HP_Current;
    }

    public ArrayList<BuffBattle> getActiveBuffList() {
        return activeBuffList;
    }

    public void setActiveBuffList(ArrayList<BuffBattle> activeBuffList) {
        this.activeBuffList = activeBuffList;
    }

    @Override
    public String toString() {
        return "NormalPerson{" +
                "psersonId=" + psersonId +
                ", aptitude=" + aptitude +
                ", name='" + name + '\'' +
                ", HP_current='" + HP_Current +
                ", sexuality=" + sexuality +
                ", strength_Raw=" + strength_Raw +
                ", intellect_Raw=" + intellect_Raw +
                ", dexterity_Raw=" + dexterity_Raw +
                ", physique_Raw=" + physique_Raw +
                ", spirit_Raw=" + spirit_Raw +
                ", luck_Raw=" + luck_Raw +
                ", fascination_Raw=" + fascination_Raw +
                ", battleId=" + battleId +
                ", activeValues=" + activeValues +
                ", camp=" + camp +
                ", isCounsellor=" + isCounsellor +
                ", isLeader=" + isLeader +
                ", strength=" + strength +
                ", intellect=" + intellect +
                ", dexterity=" + dexterity +
                ", physique=" + physique +
                ", spirit=" + spirit +
                ", luck=" + luck +
                ", skillStrings='" + skillStrings + '\'' +
                ", skillArrays=" + skillArrays +
                ", startLevel=" + level +
                ", HP_MAX=" + HP_MAX +
                ", experiencePoint=" + experiencePoint +
                ", physicDamage=" + physicDamage +
                ", magicDamage=" + magicDamage +
                ", realDamage=" + realDamage +
                ", physicsPenetrate=" + physicsPenetrate +
                ", magicPenetrate=" + magicPenetrate +
                ", accuracy=" + accuracy +
                ", criteRate=" + criteRate +
                ", reduceBeCriteRate=" + reduceBeCriteRate +
                ", criteDamage=" + criteDamage +
                ", armor=" + armor +
                ", magicResist=" + magicResist +
                ", dodge=" + dodge +
                ", actionSpeed=" + actionSpeed +
                ", hpRestore=" + hpRestore +
                ", fascination=" + fascination +
                '}';
    }

    public Interface_LineUp getLineUp() {
        return lineUp;
    }

    public void setLineUp(Interface_LineUp lineUp) {
        this.lineUp = lineUp;
    }

    /**
     * 人物的HP降低为0的时候调用
     */
    public void personDie(){
        if(lineUp == null) return;
        if(isLeader()){
            lineUp.leaderDie();
        }
    }

    @Override
    public void showSkill() {
        SimpleLog.logd(TAG, "===showSkill()===");
        if (passiveBuffList == null) {
            SimpleLog.loge(TAG, "passiveBuffList == null");
        } else {
            if (passiveBuffList.size() == 0) {
                SimpleLog.loge(TAG, "passiveBuffList size == 0");
            }
        }
        if (activeSkillsList == null) {
            SimpleLog.loge(TAG, "activeSkillsList == null");
        } else {
            if (activeSkillsList.size() == 0) {
                SimpleLog.loge(TAG, "activeSkillsList size == 0");
            }
        }
        ShowUtils.showArrayLists(TAG, passiveBuffList);
        ShowUtils.showArrayLists(TAG, activeSkillsList);
    }
}
