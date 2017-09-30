package com.game.xianxue.ashesofhistory.game.model.person;

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

        this.level = person.level;

        setLevel(level);

        this.activeValues = 0;
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
        this.activeValues = 0;

        setLevel(person.level);
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
}
