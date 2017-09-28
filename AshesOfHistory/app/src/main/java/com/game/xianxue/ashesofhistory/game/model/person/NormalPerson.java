package com.game.xianxue.ashesofhistory.game.model.person;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBattle;
import com.game.xianxue.ashesofhistory.game.skill.SkillBase;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Buff;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import java.util.ArrayList;

/**
 * 人物普通情况下的模型
 * 包含等级 经验 技能 属性 装备等
 */
public class NormalPerson extends BasePerson implements Interface_Buff {
    private static final String TAG = "NormalPerson";

    public static final int LEVEL_MAX = 25;        // 最高等级
    public static final int LEVEL_MINI = 0;        // 最低等级
    public static final int DEFAULT_ACTIVE_VALUES_MAX = 100;   //最大行动值，当行动值到达最大，就可以发动进攻

    /**
     * 7个基础属性
     * 每升一级会获得五项基础属性的提升(魅力和运气不会随着升级增加),基础属性由 1级时的基础属性值 等级 天赋 决定
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
    public int experience;         // 拥有经验
    public int experience_max;     // 升级总经验

    ArrayList<SkillBase> skillArrays = null;       // 当前拥有的技能，包括天赋技能 和 其他途径获得的技能
    ArrayList<BuffBattle> buffActive = null;       // 主动加持的buff
    ArrayList<BuffBattle> buffPassive = null;      // 被动加持的buff

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
        luck = luck_Raw;
        fascination = fascination_Raw;

        // TODO: 8/29/17 处理影响 基础属性 的效果比如增加力量的装备等等
        // TODO： 处理基础属性 包括 被动技能中涉及基础属性的部分、武器装备中涉及基础属性的部分、阵型中涉及基础属性的部分
        AddBasisBuff();

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
        AddPanelBuff();

    }

    /**
     * 加持 面板 被动 buff效果
     * 处理方式：
     * 通过循环遍历所有被动buff
     * 1.buff固定部分直接加到属性里面就好
     * 2.buff的浮动部分是百分比，需要把所有加起来，再计算到属性里面
     */
    private void AddPanelBuff() {
        if (buffPassive != null && buffPassive.size() > 0) {

            // 面板屬性提高的比例
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
            }
        }
    }

    /**
     * 加持 基础 被动 buff效果
     */
    private void AddBasisBuff() {
        if (buffPassive != null && buffPassive.size() > 0) {

            // 处理被动技能的浮动部分加成
            float strengthRate = 0;
            float intellectRate = 0;
            float dexterityRate = 0;
            float physiqueRate = 0;
            float spiritRate = 0;
            float fascinationRate = 0;
            float luckRate = 0;

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
    public void setLevel(int level) {
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

    public ArrayList<BuffBattle> getBuffActive() {
        return buffActive;
    }

    public void setBuffActive(ArrayList<BuffBattle> buffActive) {
        this.buffActive = buffActive;
    }

    public ArrayList<BuffBattle> getBuffPassive() {
        return buffPassive;
    }

    public void setBuffPassive(ArrayList<BuffBattle> buffPassive) {
        this.buffPassive = buffPassive;
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

    public String display() {
        return "Player{" +
                ", name='" + name + '\'' +
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
