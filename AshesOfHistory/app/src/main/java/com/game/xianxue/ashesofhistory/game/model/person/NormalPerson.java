package com.game.xianxue.ashesofhistory.game.model.person;

import com.game.xianxue.ashesofhistory.game.skill.SkillBase;

import java.util.ArrayList;

/**
 * 人物普通情况下的模型
 * 包含等级 经验 技能 属性 装备等
 */
public class NormalPerson extends BasePerson {
    private static final String TAG = "NormalPerson";

    public static final int LEVEL_MAX = 25;        // 最高等级
    public static final int LEVEL_MINI = 0;        // 最低等级
    public static final int DEFAULT_ACTIVE_VALUES_MAX = 100;   //最大行动值，当行动值到达最大，就可以发动进攻

    /**
     * 基础属性
     * 每升一级会获得五项基础属性的提升（基础属性由 1级时的基础属性值 等级 天赋 决定）
     * 提升的公式为：
     * newValues = oldVaules + ( Values_Raw * 0.5 + aptitude*1 )
     * 文字描述： 新属性 = 旧属性 + （ 0.5乘以 该属性的初始属性 + 资质 乘以 1）
     */
    // 以下5个属性，会随等级提高
    public int strength;       // 力量  （影响 少量HP、大量物理伤害、少量护甲、躲闪概率、少量生命恢复）
    public int intellect;      // 智力  （影响 大量魔法伤害、少量魔抗）
    public int dexterity;      // 敏捷  （影响 速度、中量物理伤害、真实伤害、大量命中率、大量闪避、大量速度、中量暴击率）
    public int physique;       // 体魄  （影响 大量HP、大量护甲、少量魔抗、大量生命恢复、少量魅力、少量格档）
    public int spirit;         // 精神  （影响 大量魔抗，少量魔法伤害，少量暴击率，少量闪避概率，少量格档概率、少量生命恢复、少量魔法伤害）
    //以下2个属性，不会随等级提高
    public int luck;           // 运气  （影响 暴击率、被暴击率、格档概率、闪避概率、命中率、优秀装备和物品的爆率）
    public int fascination;    // 魅力  （影响 收服武将的概率、商品购买和出售的价格、增加对异性的伤害、增加对异性的闪避）

    public int database_id = -1;    // 保存在数据库中的id
    public int level = 1;           // 等级

    // 武器装备
    public int weaponId = -1;       // 武器id
    public int equipId = -1;        // 装备id
    public int treasureId = -1;     // 宝物id
    public int riderId = -1;        // 坐骑id

    // 经验
    public int experience ;         // 拥有经验
    public int experience_max ;     // 升级总经验

    ArrayList<SkillBase> skillArrays = null;       // 当前拥有的技能，包括天赋技能 和 其他途径获得的技能

    /**
     * 面板属性
     */
    public int HP;                 // 生命值
    public int experiencePoint;    // 经验值
    public int physicDamage;       // 物理伤害
    public int magicDamage;        // 魔法伤害
    public int realDamage;         // 真实伤害
    public int physicsPenetrate;   // 物理穿透
    public int magicPenetrate;     // 魔法穿透
    public int accuracy;           // 命中率
    public int criteRate;          // 暴击率
    public int reduceBeCriteRate;  // 减少被暴击率
    public int criteDamage;        // 暴击伤害
    public int armor;              // 护甲（物抗）
    public int magicResist;        // 魔抗
    public int dodge;              // 闪避（闪避成功承受1%的物理伤害 或者 承受30%的魔法伤害）
    public int block;              // 格档几率（格档成功只承受30%物理伤害 或者 承受70%魔法伤害）
    public int hpRestore;          // 生命恢复
    public int actionSpeed;        // 行动速度，行动速度越快,积累行动值的速度越快，当积累的行动值到达最大行动值就可以发动进攻
    public int actionValuesMax;    // 最大行动值，这个值越小，每次进攻需要的行动值越少，一般情况下默认为 DEFAULT_ACTIVE_VALUES_MAX,可以通过坐骑或者技能缩短

    public NormalPerson() {

    }

    /**
     * 使用 BasePerson 对象的数据来填充 NormalPerson
     *
     * @param basePerson
     */
    public NormalPerson(BasePerson basePerson) {
        // 原始资料
        this.psersonId = basePerson.getPsersonId();                 // id唯一标识这个人物
        this.aptitude = basePerson.getAptitude();                   // 资质（影响每次升级 基础属性的增加数量 资质取值为1～5 1为庸碌无为 5为天神下凡）
        this.name = basePerson.getName();                           // 名字
        this.name2 = basePerson.getName2();                         // 名字拼音
        this.sexuality = basePerson.getSexuality();                 // 男1 女0

        // 获得原始属性
        this.strength_Raw = basePerson.getStrength_Raw();           // 原始力量
        this.intellect_Raw = basePerson.getIntellect_Raw();         // 原始智力
        this.dexterity_Raw = basePerson.getDexterity_Raw();         // 原始敏捷
        this.physique_Raw = basePerson.getPhysique_Raw();           // 原始体质
        this.spirit_Raw = basePerson.getSpirit_Raw();               // 原始精神
        this.fascination_Raw = basePerson.getFascination_Raw();     // 原始魅力
        this.luck_Raw = basePerson.getLuck_Raw();                   // 原始运气
    }

    /**
     * 把一个原始人物对象 加工成 带有面板属性的人物对象
     *
     * @param level
     * @param basePerson
     */
    public NormalPerson(BasePerson basePerson, int level) {
        // 原始资料
        this.psersonId = basePerson.getPsersonId();                 // id唯一标识这个人物
        this.aptitude = basePerson.getAptitude();                   // 资质（影响每次升级 基础属性的增加数量 资质取值为1～5 1为庸碌无为 5为天神下凡）
        this.name = basePerson.getName();                           // 名字
        this.name2 = basePerson.getName2();                         // 名字拼音
        this.sexuality = basePerson.getSexuality();                 // 男1 女0

        // 获得原始属性
        this.strength_Raw = basePerson.getStrength_Raw();           // 原始力量
        this.intellect_Raw = basePerson.getIntellect_Raw();         // 原始智力
        this.dexterity_Raw = basePerson.getDexterity_Raw();         // 原始敏捷
        this.physique_Raw = basePerson.getPhysique_Raw();           // 原始体质
        this.spirit_Raw = basePerson.getSpirit_Raw();               // 原始精神
        this.fascination_Raw = basePerson.getFascination_Raw();     // 原始魅力
        this.luck_Raw = basePerson.getLuck_Raw();                   // 原始运气

        // 设置等级，同时刷新属性
        setLevel(level);
    }

    /**
     * 刷新属性
     * 在设置等级，穿了装备等操作之后需要刷新属性
     */
    public void updateAttribute() {
        strength = getNewAttribute(strength_Raw, level);
        intellect = getNewAttribute(intellect_Raw, level);
        dexterity = getNewAttribute(dexterity_Raw, level);
        physique = getNewAttribute(physique_Raw, level);
        spirit = getNewAttribute(spirit_Raw, level);

        // TODO: 8/29/17 处理影响 基础属性 的效果比如增加力量的装备等等
        // TODO： 处理基础属性 包括 被动技能中涉及基础属性的部分、武器装备中涉及基础属性的部分、阵型中涉及基础属性的部分


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
        actionValuesMax = calculateMaxActiveValues();           // 执行一次行动，需要的行动值（越少越好）


        // TODO: 8/29/17 处理 面板属性 的效果

    }



    protected int calculateHp() {
        return strength * 3 + physique * 6 + spirit * 1;
    }

    protected int calculatePhysicDamage() {
        return (int) (strength * 1.5f + dexterity * 0.5f);
    }

    protected int calculateMagicDamage() {
        return (int) (intellect * 2f + spirit * 1f);
    }

    protected int calculateRealDamage() {
        return (int) (dexterity * 0.4f + intellect * 0.2f);
    }

    protected int calculatePhysicsPenetrate() {
        return 0;
    }

    protected int calculateMagicPenetrate() {
        return 0;
    }

    protected int calculateAccuracy() {
        return strength + intellect * 2 + dexterity * 3 + spirit * 1 + physique + (int) (luck * 0.5);
    }

    protected int calculateCriteRate() {
        return dexterity * 1 + (int) (luck * 0.5f);
    }

    protected int calculateCriteDamege() {
        return (int) (200 + (physique + spirit) * 0.1);
    }

    protected int calculateArmor() {
        return strength + physique * 3;
    }

    protected int calculateMagicResist() {
        return intellect * 2 + physique + spirit * 3;
    }

    protected int calculateDodge() {
        return dexterity * 2 + spirit + (int) (luck * 0.5f);
    }

    protected int calculateBlock() {
        return strength * 3 + physique * 2;
    }

    protected int calculateSpeed() {
        return dexterity * 3 + physique + spirit;
    }

    protected int calculateHpRestore() {
        return strength + physique * 3 + spirit;
    }

    protected int calculateMaxActiveValues() {
        return 1000;
    }


    /**
     * 设置等级的时候，同时初始化属性
     *
     * @param level
     */
    protected void setLevel(int level) {
        if (level <= LEVEL_MINI) {
            this.level = LEVEL_MINI;
        } else if (level <= LEVEL_MAX) {
            this.level = level;
        } else {
            this.level = LEVEL_MAX;
        }
        updateAttribute();
    }

    /**
     * 通过等级和初始属性，获得升级之后的属性
     *
     * @param Initial
     * @param level
     * @return
     */
    protected int getNewAttribute(int Initial, int level) {
        return (int) ((float) Initial + (Initial * 0.5f + aptitude * 1f) * (float) level);
    }

    public float getAptitude() {
        return aptitude;
    }

    public void setAptitude(float aptitude) {
        this.aptitude = aptitude;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntellect() {
        return intellect;
    }

    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getPhysique() {
        return physique;
    }

    public void setPhysique(int physique) {
        this.physique = physique;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getExperiencePoint() {
        return experiencePoint;
    }

    public void setExperiencePoint(int experiencePoint) {
        this.experiencePoint = experiencePoint;
    }

    public int getPhysicDamage() {
        return physicDamage;
    }

    public void setPhysicDamage(int physicDamage) {
        this.physicDamage = physicDamage;
    }

    public int getMagicDamage() {
        return magicDamage;
    }

    public void setMagicDamage(int magicDamage) {
        this.magicDamage = magicDamage;
    }

    public int getRealDamage() {
        return realDamage;
    }

    public void setRealDamage(int realDamage) {
        this.realDamage = realDamage;
    }

    public int getPhysicsPenetrate() {
        return physicsPenetrate;
    }

    public void setPhysicsPenetrate(int physicsPenetrate) {
        this.physicsPenetrate = physicsPenetrate;
    }

    public int getMagicPenetrate() {
        return magicPenetrate;
    }

    public void setMagicPenetrate(int magicPenetrate) {
        this.magicPenetrate = magicPenetrate;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getCriteRate() {
        return criteRate;
    }

    public void setCriteRate(int criteRate) {
        this.criteRate = criteRate;
    }

    public int getReduceBeCriteRate() {
        return reduceBeCriteRate;
    }

    public void setReduceBeCriteRate(int reduceBeCriteRate) {
        this.reduceBeCriteRate = reduceBeCriteRate;
    }

    public int getCriteDamage() {
        return criteDamage;
    }

    public void setCriteDamage(int criteDamage) {
        this.criteDamage = criteDamage;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMagicResist() {
        return magicResist;
    }

    public void setMagicResist(int magicResist) {
        this.magicResist = magicResist;
    }

    public int getDodge() {
        return dodge;
    }

    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getActionSpeed() {
        return actionSpeed;
    }

    public void setActionSpeed(int actionSpeed) {
        this.actionSpeed = actionSpeed;
    }

    public int getHpRestore() {
        return hpRestore;
    }

    public void setHpRestore(int hpRestore) {
        this.hpRestore = hpRestore;
    }

    public int getFascination() {
        return fascination;
    }

    public void setFascination(int fascination) {
        this.fascination = fascination;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getSexuality() {
        return sexuality;
    }

    public void setSexuality(int sexuality) {
        this.sexuality = sexuality;
    }


    public String getSkillLists() {
        return skillLists;
    }

    public void setSkillLists(String skillLists) {
        this.skillLists = skillLists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<SkillBase> getSkillArrays() {
        if (skillLists == null || skillLists.length() < 1) {
            return null;
        } else {

        }

        return skillArrays;
    }

    public void setSkillArrays(ArrayList<SkillBase> skillArrays) {
        this.skillArrays = skillArrays;
    }

    public int getActionValuesMax() {
        return actionValuesMax;
    }

    public void setActionValuesMax(int actionValuesMax) {
        this.actionValuesMax = actionValuesMax;
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
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
                ", skillLists='" + skillLists + '\'' +
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
                '}';
    }
}
