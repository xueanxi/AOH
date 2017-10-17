package com.game.xianxue.ashesofhistory.game.model.person;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.database.BuffDataManager;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBase;
import com.game.xianxue.ashesofhistory.game.model.buff.BuffBattle;
import com.game.xianxue.ashesofhistory.game.skill.SkillBase;
import com.game.xianxue.ashesofhistory.game.skill.SkillBattle;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Buff;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Person;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Skill;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import java.util.ArrayList;

/**
 * 人物普通情况下的模型
 * 包含等级 经验 技能 面板属性 装备等
 */
public class NormalPerson extends BasePerson implements Interface_Buff, Interface_Person, Interface_Skill {
    private static final String TAG = "NormalPerson";

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
    public int spirit;         // 精神  （影响 大量魔抗，少量魔法伤害，少量暴击率，少量闪避概率，少量格档概率、少量生命恢复）
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

    ArrayList<SkillBase> skillArrays = null;            // 当前拥有的技能，包括所有技能
    ArrayList<SkillBattle> skillActivesLists = null;    // 战斗中可以主动释放的技能
    ArrayList<BuffBattle> buffActive = null;            // 主动加持的buff(指战斗中，使用技能加持的)
    ArrayList<BuffBattle> buffPassive = null;           // 被动加持的buff（被动技能加持的）

    /**
     * 面板属性
     */
    public int HP_MAX;             // 最大生命值
    public int experiencePoint;    // 经验值
    public int physicDamage;       // 物理伤害
    public int magicDamage;        // 魔法伤害
    public int realDamage;         // 真实伤害
    public int physicsPenetrate;   // 物理穿透
    public int magicPenetrate;     // 魔法穿透
    public int accuracy;           // 命中值
    public int criteRate;          // 暴击值
    public int reduceBeCriteRate;  // 减少被暴击的值
    public float criteDamage;      // 暴击伤害,暴击倍数（发生暴击时，产生多少倍的伤害）
    public int armor;              // 护甲（物抗）
    public int magicResist;        // 魔抗
    public int dodge;              // 闪避（闪避成功承受1%的物理伤害 或者 承受30%的魔法伤害）
    public int block;              // 格档几率（格档成功只承受30%物理伤害 或者 承受70%魔法伤害）
    public int hpRestore;          // 生命恢复
    public int actionSpeed;        // 行动速度，行动速度越快,积累行动值的速度越快，当积累的行动值到达最大行动值就可以发动进攻
    public int actionValuesMax;    // 最大行动值，这个值越小，每次进攻需要的行动值越少，一般情况下默认为 DEFAULT_ACTIVE_VALUES_MAX,可以通过坐骑或者技能缩短
    public float skillRate;        // 额外技能触发几率（默认情况下一次行动中 普通攻击概率为50% 技能发动概率为50%，Ex:当skillRate=0.1时,技能概率为0.5+0.1=0.6)
    public int attackRangeUp;      // 攻击范围增加(这个值只能从 技能和装备 获得提升)
    public int attackNumberUp;     // 攻击的人数增加(这个值只能从 技能和装备 获得提升)

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
        this.skillStrings = basePerson.skillStrings;

        // 初始化技能
        setLevel(1);

        // 刷新属性
        updateAttribute();
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
        this.skillStrings = basePerson.skillStrings;

        SimpleLog.logd(TAG, "NormalPerson(): skillStrings=" + this.skillStrings);

        // 初始化技能
        setLevel(level);
        initSkill();

        // 刷新属性
        updateAttribute();
    }

    /**
     * 进行技能的初始化，包括以下操作
     * 1：从人物的技能字符串里面解析出技能，添加到 skillAllLists
     * 2：把被动技能挑选出来，添加到 buffPassive
     * 3：把主动技能挑选出来，添加到 skillActivesLists
     */
    protected void initSkill() {
        parseSkillList();

        //ShowUtils.showArrayLists(TAG+"天赋技能列表:",skillAllLists);

        SkillBean skillBean = null;
        BuffBase buffBase = null;
        BuffBattle buffBattle = null;
        buffPassive = new ArrayList<BuffBattle>();
        skillActivesLists = new ArrayList<SkillBattle>();

        for (int i = 0; i < this.skillAllLists.size(); i++) {
            skillBean = skillAllLists.get(i);

            // 如果人物等级小于技能的解锁等级，则此技能还不能使用
            if (this.level < skillBean.getUnLockLevel()) continue;

            SimpleLog.logd(TAG, this.getName() + " Lv." + this.level + skillBean.getSkill().getName() + "的解锁等级为:" + skillBean.getUnLockLevel());
            // 把技能根据 主动技能和被动技能分类 初始化到变量里面
            if (skillBean.getSkill() == null) continue;
            if (SKILL_NATURE_ACTIVE == skillBean.getSkill().getSkillNature()) {
                // 处理主动技能
                SkillBase skillBase = skillBean.getSkill();
                if (skillBase == null) continue;
                SkillBattle skillBattle = new SkillBattle(skillBase, skillBean.getCurrentSkillLevel(this.level));
                skillActivesLists.add(skillBattle);
            } else {
                // 处理被动技能
                int buffId = skillBean.getSkill().getAssistEffect();
                if (buffId == 0) continue;
                buffBase = BuffDataManager.getBuffFromDataBaseById(buffId);
                if (buffBase == null) continue;
                buffBattle = new BuffBattle(buffBase, skillBean.getCurrentSkillLevel(this.level));
                buffPassive.add(buffBattle);
            }
        }

        ShowUtils.showArrayLists(TAG + "主动技能列表:", skillActivesLists);
        ShowUtils.showArrayLists(TAG + "被动技能列表:", buffPassive);
    }

    /**
     * 刷新属性：在设置等级，穿了装备等操作之后需要刷新属性
     * <p>
     * 刷新的步骤是：
     * 0.根据当前等级，计算人物当前的基础属性
     * 1.计算buff,装备等 增加基础属性的固定增加
     * 2.计算buff,装备等 基础属性浮动增加
     * 3.通过0和1和2条 计算最终的基础属性
     * <p>
     * 4.由基础属性计算出面板属性
     * 5.计算buff,装备等面板属性的固定增加
     * 6.计算buff,装备等面板属性的浮动增加
     * 7.通过4和5和6条 计算最终的面板属性
     */
    public void updateAttribute() {
        updateBasicAttribute();

        // TODO: 8/29/17 处理影响 基础属性 的效果比如增加力量的装备等等
        // TODO： 处理基础属性 包括 被动技能中涉及基础属性的部分、武器装备中涉及基础属性的部分、阵型中涉及基础属性的部分
        addBasisBuff();

        calcultePanelAttribute();

        // TODO: 8/29/17 处理 面板属性 的效果
        addPanelBuff();
    }

    /**
     * 计算面板属性
     */
    protected void calcultePanelAttribute() {
        HP_MAX = calculateHp();                                 // 最大生命值
        experiencePoint = 0;                                    // 经验值
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
        dodge = calculateDodge();                               // 闪避值（闪避成功承受10%的物理伤害 或者 承受30%的魔法伤害）
        block = calculateBlock();                               // 格档值（格档成功只承受30%物理伤害 或者 承受70%魔法伤害）
        actionSpeed = calculateSpeed();                         // 速度
        hpRestore = calculateHpRestore();                       // 发起进攻时，生命恢复
        actionValuesMax = calculateMaxActiveValues();           // 执行一次行动，需要的行动值（越少越好）
    }

    /**
     * 刷新基础属性
     */
    protected void updateBasicAttribute() {
        strength = getNewAttribute(strength_Raw, level);
        intellect = getNewAttribute(intellect_Raw, level);
        dexterity = getNewAttribute(dexterity_Raw, level);
        physique = getNewAttribute(physique_Raw, level);
        spirit = getNewAttribute(spirit_Raw, level);
        luck = luck_Raw;
        fascination = fascination_Raw;
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
            float HPMAXRate = 0;
            float skillRateRate = 0;
            float attackNumberRate = 0;
            float attackRangeRate = 0;

            // 遍历buff数组，把buff的加成加到属性里面
            for (BuffBattle buff : buffPassive) {

                if(BUFF_TYPE_LAST == buff.getBuff_type()) continue;// 如果是战斗中持续触发的buff，则不进行处理

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
                        }
                    }
                }

                // buff 中浮动的属性，在最后一起计算
                strength = (int) (strength * (1f + physicDamageRate));
                magicDamage = (int) (magicDamage * (1f + magicDamageRate));
                realDamage = (int) (realDamage * (1f + realDamageRate));
                physicsPenetrate = (int) (physicsPenetrate * (1f + physicsPenetrateRate));
                magicPenetrate = (int) (magicPenetrate * (1f + magicPenetrateRate));
                accuracy = (int) (accuracy * (1f + accuracyRate));
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
                HP_MAX = (int) (HP_MAX * (1f + HPMAXRate));
                skillRate = skillRate * (1f + skillRateRate);
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
    protected void addBasisBuff() {
        SimpleLog.logd(TAG, this.name + " addBasisBuff()");
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

                if(BUFF_TYPE_LAST == buff.getBuff_type()) continue;// 如果是战斗中持续触发的buff，则不进行处理

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
     * 设置等级的时候，同时初始化属性
     *
     * @param level
     */
    public void setLevelAndUpdate(int level) {
        if (level <= PERSON_LEVEL_MINI) {
            this.level = PERSON_LEVEL_MINI;
        } else if (level <= PERSON_LEVEL_MAX) {
            this.level = level;
        } else {
            this.level = PERSON_LEVEL_MAX;
        }
        updateAttribute();
    }

    /**
     * 设置等级的时候，同时初始化属性
     *
     * @param level
     */
    public void setLevel(int level) {
        if (level < PERSON_LEVEL_MINI) {
            this.level = PERSON_LEVEL_MINI;
        } else if (level > PERSON_LEVEL_MAX) {
            this.level = PERSON_LEVEL_MAX;
        } else {
            this.level = level;
        }
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

    protected int calculateHp() {
        return strength * 3 + physique * 6 + spirit * 1;
    }

    protected int calculateHpRestore() {
        return (int)(strength*0.15f + physique * 0.3f + spirit*0.1);
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
        // TODO: 10/16/17 增加物理穿透率的计算
        return (int) (strength * 0.2f + dexterity * 0.3f + intellect * 0.2f);
    }

    protected int calculateMagicPenetrate() {
        // TODO: 10/16/17 增加魔法穿透率的计算
        int result = (int) (intellect * 0.4f + dexterity * 0.1f + spirit * 0.2f);
        return result;
    }

    protected int calculateAccuracy() {
        return strength * 1 + intellect * 3 + dexterity * 4 + physique * 1 + luck;
    }

    protected int calculateCriteRate() {
        return strength + dexterity * 2 + intellect + luck * 2;
    }

    protected int calculatereduceBeCriteRate() {
        return strength * 3 + dexterity * 5 + intellect*2 +spirit+ luck * 5;
    }

    protected float calculateCriteDamege() {
        return  2f;
    }

    protected int calculateArmor() {
        return (int) (strength * 1f + physique * 1);
    }

    protected int calculateMagicResist() {
        return (int) (intellect * 0.5f + physique * 0.5f + spirit * 1f);
    }

    protected int calculateDodge() {
        return dexterity * 2 + intellect * 2 + luck;
    }

    protected int calculateBlock() {
        return strength * 3 + physique * 2;
    }

    protected int calculateSpeed() {
        return dexterity * 3 + physique + spirit;
    }



    protected int calculateMaxActiveValues() {
        return 1000;
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

    public int getHP_MAX() {
        return HP_MAX;
    }

    public void setHP_MAX(int HP_MAX) {
        this.HP_MAX = HP_MAX;
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

    public float getCriteDamage() {
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


    public String getSkillStrings() {
        return skillStrings;
    }

    public void setSkillStrings(String skillStrings) {
        this.skillStrings = skillStrings;
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
        if (skillStrings == null || skillStrings.length() < 1) {
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

    public int getDatabase_id() {
        return database_id;
    }

    public void setDatabase_id(int database_id) {
        this.database_id = database_id;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(int weaponId) {
        this.weaponId = weaponId;
    }

    public int getEquipId() {
        return equipId;
    }

    public void setEquipId(int equipId) {
        this.equipId = equipId;
    }

    public int getTreasureId() {
        return treasureId;
    }

    public void setTreasureId(int treasureId) {
        this.treasureId = treasureId;
    }

    public int getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience_max() {
        return experience_max;
    }

    public void setExperience_max(int experience_max) {
        this.experience_max = experience_max;
    }

    public ArrayList<SkillBattle> getSkillActivesLists() {
        return skillActivesLists;
    }

    public void setSkillActivesLists(ArrayList<SkillBattle> skillActivesLists) {
        this.skillActivesLists = skillActivesLists;
    }

    public float getSkillRate() {
        return skillRate;
    }

    public void setSkillRate(float skillRate) {
        this.skillRate = skillRate;
    }

    public int getAttackRangeUp() {
        return attackRangeUp;
    }

    public void setAttackRangeUp(int attackRangeUp) {
        this.attackRangeUp = attackRangeUp;
    }

    public int getAttackNumberUp() {
        return attackNumberUp;
    }

    public void setAttackNumberUp(int attackNumberUp) {
        this.attackNumberUp = attackNumberUp;
    }

    @Override
    public String toString() {
        return "NormalPerson{" +
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
                ", block=" + block +
                ", actionSpeed=" + actionSpeed +
                ", hpRestore=" + hpRestore +
                ", fascination=" + fascination +
                '}';
    }

    public String display() {
        int buffPassiveSize = -1;
        int skillActiveSize = -1;
        if (buffPassive != null) {
            buffPassiveSize = buffPassive.size();
        }
        if (skillActivesLists != null) {
            skillActiveSize = skillActivesLists.size();
        }

        return "Player{" +
                ", name='" + name + '\'' +
                ", strength=" + strength +
                ", intellect=" + intellect +
                ", dexterity=" + dexterity +
                ", physique=" + physique +
                ", spirit=" + spirit +
                ", luck=" + luck +
                ", fascination=" + fascination +
                ", skillStrings='" + skillStrings + '\'' +
                ", skillArrays=" + skillArrays +
                ", startLevel=" + level +
                ", skillActive size =" + skillActiveSize +
                ", buffPassive size =" + buffPassiveSize +
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
                ", block=" + block +
                ", actionSpeed=" + actionSpeed +
                ", hpRestore=" + hpRestore +
                '}';
    }

    public void showSkill() {
        ShowUtils.showArrayLists(TAG, buffPassive);
        ShowUtils.showArrayLists(TAG, skillActivesLists);
    }
}
