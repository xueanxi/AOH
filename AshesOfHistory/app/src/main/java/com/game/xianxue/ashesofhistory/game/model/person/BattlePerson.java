package com.game.xianxue.ashesofhistory.game.model.person;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBattle;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

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

    public BattlePerson() {}

    /**
     * 构造函数 2
     * @param person
     * @param camp
     */
    public BattlePerson(NormalPerson person,int camp) {
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

        // 初始化技能
        setLevel(person.level);
        initSkill();

        // 刷新属性
        updateAttribute();

        // 初始战斗相关的数据
        this.activeValues = 0;// 战斗活跃值初始为0
    }

    /**
     * 重写父类 NormalPerson 的 updateAttribute方法。
     * 因为战斗面板 比 非战斗面板 需要考虑的更加多
     * 刷新属性
     * 在设置等级，穿了装备等操作之后需要刷属性
     */
    @Override
    public void updateAttribute() {
        strength = getNewAttribute(strength_Raw, level);
        intellect = getNewAttribute(intellect_Raw, level);
        dexterity = getNewAttribute(dexterity_Raw, level);
        physique = getNewAttribute(physique_Raw, level);
        spirit = getNewAttribute(spirit_Raw, level);

        // TODO: 8/29/17 处理影响基础属性的效果比如增加力量的装备等等

        addBasisBuff();

        HP = calculateHp();                                     // 生命值
        experiencePoint = 0;                                    // 经验值
        physicDamage = calculatePhysicDamage();                 // 物理伤害
        magicDamage = calculateMagicDamage();                   // 魔法伤害
        realDamage = calculateRealDamage();                     // 真实伤害
        physicsPenetrate = calculatePhysicsPenetrate();         // 物理穿透
        magicPenetrate = calculateMagicPenetrate();             // 魔法穿透
        accuracy = calculateAccuracy();                         // 命中率
        criteRate = calculateCriteRate();                       // 暴击率
        criteDamage = calculateCriteDamege();                   // 暴击伤害
        armor = calculateArmor();                               // 护甲（物抗）
        magicResist = calculateMagicResist();                   // 魔抗
        dodge = calculateDodge();                               // 闪避值（闪避成功承受10%的物理伤害 或者 承受30%的魔法伤害）
        block = calculateBlock();                               // 格档值（格档成功只承受30%物理伤害 或者 承受70%魔法伤害）
        actionSpeed = calculateSpeed();                         // 速度
        hpRestore = calculateHpRestore();                       // 发起进攻时，生命恢复
        actionValuesMax = calculateMaxActiveValues();           // 最大行动值是多少，这个值越小越好
        // TODO: 8/29/17 处理其他增幅效果

        addPanelBuff();
    }


    /**
     * 加持 面板 被动 buff效果
     * 处理方式：
     * 通过循环遍历所有被动buff
     * 1.buff固定部分直接加到属性里面就好
     * 2.buff的浮动部分是百分比，需要把所有加起来，再计算到属性里面
     */
    protected void addPanelBuff() {
        if (buffPassive != null && buffPassive.size() > 0) {
            // SimpleLog.logd(TAG,this.name+" before:numberUp = "+this.attackNumberUp);
            // 面板屬性在所有buff的影响下增幅的比例
            float physicDamageRate = 0;
            float magicDamageRate = 0;
            float realDamageRate = 0;
            float physicsPenetrateRate = 0;
            float magicPenetrateRate = 0;
            float accuracyRate = 0;
            float criteRateRate = 0;
            float criteDamageRate = 0;
            float armorRate = 0;
            float magicResistRate = 0;
            float dodgeRate = 0;
            float blockRate = 0;
            float actionSpeedRate = 0;
            float hpRestoreRate = 0;
            float actionValuesMaxRate = 0;
            float reduceBeCriteRateRate = 0;
            float HPRate = 0;
            float skillRateRate = 0;
            float attackNumberRate = 0;
            float attackRangeRate = 0;

            // 遍历buff数组，把buff的加成加到属性里面
            for (BuffBattle buff : buffPassive) {
                int buffNumber = buff.getBuff_effect().length;
                for (int i = 0; i < buffNumber; i++) {
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
                            case BUFF_BLOCK:
                                block += buff.getBuff_constant()[i];
                                blockRate += buff.getBuff_fluctuate()[i];
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
                            case BUFF_HP:
                                HP += buff.getBuff_constant()[i];
                                HPRate += buff.getBuff_fluctuate()[i];
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
                        }
                    }
                }

                // buff 中浮动的属性，在最后一起计算
                strength = (int) (strength * (1f + physicDamageRate));
                magicDamage = (int) (magicDamage * (1f + magicDamageRate));
                realDamage = (int) (realDamage * (1f + realDamageRate));
                physicsPenetrate = (int) (physicsPenetrate * (1f + physicsPenetrateRate));
                magicPenetrate = (int) (magicPenetrate * (1f + magicPenetrateRate));
                accuracy = (int) (accuracyRate * (1f + accuracyRate));
                criteRate = (int) (criteRate * (1f + criteRateRate));
                criteDamage = (int) (criteDamage * (1f + criteDamageRate));
                armor = (int) (armor * (1f + armorRate));
                magicResist = (int) (magicResist * (1f + magicResistRate));
                dodge = (int) (dodge * (1f + dodgeRate));
                block = (int) (block * (1f + blockRate));
                actionSpeed = (int) (actionSpeed * (1f + actionSpeedRate));
                hpRestore = (int) (hpRestore * (1f + hpRestoreRate));
                actionValuesMax = (int) (actionValuesMax * (1f + actionValuesMaxRate));
                reduceBeCriteRate = (int) (reduceBeCriteRate * (1f + reduceBeCriteRateRate));
                HP = (int) (HP * (1f + HPRate));
                skillRate = (int) (skillRate * (1f + skillRateRate));
                attackNumberUp = (int) (attackNumberUp * (1f + attackNumberRate));
                attackRangeUp = (int) (attackRangeUp * (1f + attackRangeRate));

                //SimpleLog.logd(TAG,this.name+" after:numberUp = "+this.attackNumberUp);
            }
        }
    }

    /**
     * 加持 基础 被动 buff效果
     * 比如增加 力量，智力，敏捷，精神，体质，魅力，运气 这七个
     */
    private void addBasisBuff() {
        SimpleLog.logd(TAG,this.name+" addBasisBuff()");
        if (buffPassive != null && buffPassive.size() > 0) {

            // 处理被动技能的浮动部分加成
            float strengthRate = 0;
            float intellectRate = 0;
            float dexterityRate = 0;
            float physiqueRate = 0;
            float spiritRate = 0;
            float fascinationRate = 0;
            float luckRate = 0;

            ShowUtils.showArrayLists(TAG+" show buffPassive:",buffPassive);

            // 先处理被动技能的固定加成部分
            for (BuffBattle buff : buffPassive) {
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

            // 最后再把百分比的计算到属性中
            strength = (int) (strength * (1f + strengthRate));
            intellect = (int) (intellect * (1f + intellectRate));
            dexterity = (int) (dexterity * (1f + dexterityRate));
            physique = (int) (physique * (1f + physiqueRate));
            spirit = (int) (spirit * (1f + spiritRate));
            fascination = (int) (fascination * (1f + fascinationRate));
            luck = (int) (luck * (1f + luckRate));
        }
    }


    /**
     * 初始化
     */
    public void init(int battleId) {
        this.activeValues = 0;
        this.battleId = battleId;
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
     * @return
     */
    public float getActiveValuePencent(){
        return (float)activeValues / (float)actionValuesMax;
    }

    /**
     * 执行行动之后，需要消耗掉活跃值
     */
    public void reduceActiveValue(){
        int newValues = activeValues - actionValuesMax;
        if(newValues <= 0 ) newValues = 0;
        activeValues = newValues;
    }

    /**
     * 战斗中，隔一段时间会增加活跃值
     */
    public void increaseActiveValues(){
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

    @Override
    public String toString() {

        return "BattlePerson{" +
                "psersonId=" + psersonId +
                ", aptitude=" + aptitude +
                ", name='" + name + '\'' +
                ", sexuality=" + sexuality +
                ", strength_Raw=" + strength_Raw +
                ", intellect_Raw=" + intellect_Raw +
                ", dexterity_Raw=" + dexterity_Raw +
                ", physique_Raw=" + physique_Raw +
                ", spirit_Raw=" + spirit_Raw +
                ", luck_Raw=" + luck_Raw +
                ", fascination_Raw=" + fascination_Raw +
                ", strength=" + strength +
                ", intellect=" + intellect +
                ", dexterity=" + dexterity +
                ", physique=" + physique +
                ", spirit=" + spirit +
                ", luck=" + luck +
                ", skillStrings='" + skillStrings + '\'' +
                ", skillArrays=" + skillArrays +
                ", level=" + level +
                ", HP=" + HP +
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
                ", block=" + block +
                ", actionSpeed=" + actionSpeed +
                ", hpRestore=" + hpRestore +
                ", fascination=" + fascination +
                ", battleId=" + battleId +
                ", activeValues=" + activeValues +
                ", camp=" + camp +
                ", isCounsellor=" + isCounsellor +
                ", isLeader=" + isLeader +
                '}';
    }

    @Override
    public void showSkill() {
        SimpleLog.logd(TAG,"===showSkill()===");
        if(buffPassive == null){
            SimpleLog.loge(TAG,"buffPassive == null");
        }else{
            if(buffPassive.size() == 0){
                SimpleLog.loge(TAG,"buffPassive size == 0");
            }
        }
        if(skillActivesLists == null){
            SimpleLog.loge(TAG,"skillActivesLists == null");
        }else{
            if(skillActivesLists.size() == 0){
                SimpleLog.loge(TAG,"skillActivesLists size == 0");
            }
        }
        ShowUtils.showArrayLists(TAG,buffPassive);
        ShowUtils.showArrayLists(TAG,skillActivesLists);
    }
}
