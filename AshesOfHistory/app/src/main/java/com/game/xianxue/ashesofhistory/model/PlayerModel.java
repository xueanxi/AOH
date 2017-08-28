package com.game.xianxue.ashesofhistory.model;

import java.util.ArrayList;

/**
 * 角色基础模型
 */
public class PlayerModel {
    private static final String TAG = "PlayerModel";

    // 基础资料
    private int character_id;         // id唯一标识这个武将的标志
    private float aptitude; // 资质（影响每次升级 基础属性的增加数量 资质取值为1～5 1为庸碌无为 5为天神下凡）
    private String name;    // 名字
    private int sexuality;  // 男1 女0

    /**
     * 基础属性
     * 每升一级会获得五项基础属性的提升
     * 提升的公式为：
     * newValues = oldVaules + ( Values_Initial*0.5 + aptitude*1 )
     * 文字描述： 新属性 = 旧属性 + （ 0.5乘以 该属性的初始属性 + 资质 乘以 1）
     */
    // 以下5个属性，会随等级提高
    private int strength;       // 力量  （影响 少量HP、大量物理伤害、少量护甲、躲闪概率、少量生命恢复）
    private int intellect;      // 智力  （影响 大量魔法伤害、少量魔抗）
    private int dexterity;      // 敏捷  （影响 速度、中量物理伤害、真实伤害、大量命中率、大量闪避、大量速度、中量暴击率）
    private int physique;       // 体魄  （影响 大量HP、大量护甲、少量魔抗、大量生命恢复、少量魅力、少量格档）
    private int spirit;         // 精神  （影响 大量魔抗，少量魔法伤害，少量暴击率，少量闪避概率，少量格档概率、少量生命恢复、少量魔法伤害）

    /**
     * 初始属性 即0级时的属性，这个属性衡量了一个人物的天赋
     * 初始属性 和 资质 共同影响 升级后提升属性的值
     */
    private int strength_Initial;       //初始力量
    private int intellect_Initial;      //初始智力
    private int dexterity_Initial;      //初始敏捷
    private int physique_Initial;       //初始体魄
    private int spirit_Initial;         //初始精神

    private int luck_Initial;           //初始运气
    private int fascination_Initial;    //初始魅力


    //运气为固定属性，不会随等级提高
    private int luck;//运气       （影响 暴击率、被暴击率、格档概率、闪避概率、命中率、优秀装备和物品的爆率）

    /**
     * 技能
     */
    String skillLists = null;
    ArrayList<SkillModel> skillArrays = null;
    private int level = 0;//等级

    /**
     * 战斗属性
     */
    private int HP;                 // 生命值
    private int experiencePoint;    // 经验值
    private int physicDamage;       // 物理伤害
    private int magicDamage;        // 魔法伤害
    private int realDamage;         // 真实伤害
    private int physicsPenetrate;   // 物理穿透
    private int magicPenetrate;     // 魔法穿透
    private int accuracy;           // 命中率
    private int criteRate;          // 暴击率
    private int reduceBeCriteRate;  // 减少被暴击率
    private int criteDamage;        // 暴击伤害
    private int armor;              // 护甲（物抗）
    private int magicResist;        // 魔抗
    private int dodge;              // 闪避（闪避成功承受1%的物理伤害 或者 承受30%的魔法伤害）
    private int block;              // 格档几率（格档成功只承受30%物理伤害 或者 承受70%魔法伤害）
    private int speed;              // 速度
    private int hpRestore;          // 生命恢复
    private int fascination;        // 魅力（影响 收服武将的概率、商品购买和出售的价格、增加对异性的伤害、增加对异性的闪避）


    // 战斗属性，只有在战斗时才会有的属性
    private int activeValues;       // 行动值，当行动值满了之后，就可以行动。
    private int battleId;           // 一场战斗中分配的id
    private int camp;               // 阵营

    public PlayerModel() {
    }


    public PlayerModel(int strength, int intellect, int dexterity, int physique, int spirit, int fascination, int luck, boolean isBoy) {
        this.strength = strength;        // 力量
        this.intellect = intellect;      // 智力
        this.dexterity = dexterity;      // 敏捷
        this.physique = physique;        // 体魄
        this.spirit = spirit;            // 精神
        this.fascination = fascination;
        this.luck = luck;

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
        speed = calculateSpeed();                               // 速度
        hpRestore = calculateHpRestore();                       // 发起进攻时，生命恢复
        sexuality = isBoy ? 1 : 0; // 男1 女0
    }

    private int calculateHp() {
        return strength * 3 + physique * 6 + spirit * 1;
    }

    private int calculatePhysicDamage() {
        return (int) (strength * 1.5f + dexterity * 0.5f);
    }

    private int calculateMagicDamage() {
        return (int) (intellect * 2f + spirit * 1f);
    }

    private int calculateRealDamage() {
        return (int) (dexterity * 0.4f + intellect * 0.2f);
    }

    private int calculatePhysicsPenetrate() {
        return 0;
    }

    private int calculateMagicPenetrate() {
        return 0;
    }

    private int calculateAccuracy() {
        return strength + intellect * 2 + dexterity * 3 + spirit * 1 + physique + (int) (luck * 0.5);
    }

    private int calculateCriteRate() {
        return dexterity * 1 + (int) (luck * 0.5f);
    }

    private int calculateCriteDamege() {
        return (int) (200 + (physique + spirit) * 0.1);
    }

    private int calculateArmor() {
        return strength + physique * 3;
    }

    private int calculateMagicResist() {
        return intellect * 2 + physique + spirit * 3;
    }

    private int calculateDodge() {
        return dexterity * 2 + spirit + (int) (luck * 0.5f);
    }

    private int calculateBlock() {
        return strength * 3 + physique * 2;
    }

    private int calculateSpeed() {
        return dexterity * 3 + physique + spirit;
    }

    private int calculateHpRestore() {
        return strength + physique * 3 + spirit;
    }


    /**
     * 设置等级的时候，同时初始化属性
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
        strength = getNewAttribute(strength_Initial, level);
        intellect = getNewAttribute(intellect_Initial, level);
        dexterity = getNewAttribute(dexterity_Initial, level);
        physique = getNewAttribute(physique_Initial, level);
        spirit = getNewAttribute(spirit_Initial, level);

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
        speed = calculateSpeed();                               // 速度
        hpRestore = calculateHpRestore();                       // 发起进攻时，生命恢复
    }

    /**
     * 通过等级和初始属性，获得升级之后的属性
     *
     * @param Initial
     * @param level
     * @return
     */
    private int getNewAttribute(int Initial, int level) {
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public int getStrength_Initial() {
        return strength_Initial;
    }

    public void setStrength_Initial(int strength_Initial) {
        this.strength_Initial = strength_Initial;
    }

    public int getIntellect_Initial() {
        return intellect_Initial;
    }

    public void setIntellect_Initial(int intellect_Initial) {
        this.intellect_Initial = intellect_Initial;
    }

    public int getDexterity_Initial() {
        return dexterity_Initial;
    }

    public void setDexterity_Initial(int dexterity_Initial) {
        this.dexterity_Initial = dexterity_Initial;
    }

    public int getPhysique_Initial() {
        return physique_Initial;
    }

    public void setPhysique_Initial(int physique_Initial) {
        this.physique_Initial = physique_Initial;
    }

    public int getSpirit_Initial() {
        return spirit_Initial;
    }

    public void setSpirit_Initial(int spirit_Initial) {
        this.spirit_Initial = spirit_Initial;
    }

    public int getCamp() {
        return camp;
    }

    public void setCamp(int camp) {
        this.camp = camp;
    }

    public int getLuck_Initial() {
        return luck_Initial;
    }

    public void setLuck_Initial(int luck_Initial) {
        this.luck_Initial = luck_Initial;
    }

    public int getFascination_Initial() {
        return fascination_Initial;
    }

    public void setFascination_Initial(int fascination_Initial) {
        this.fascination_Initial = fascination_Initial;
    }

    public int getLevel() {
        return level;
    }

    public int getCharacter_id() {
        return character_id;
    }

    public void setCharacter_id(int character_id) {
        this.character_id = character_id;
    }

    public ArrayList<SkillModel> getSkillArrays() {
        if (skillLists == null || skillLists.length() < 1){
            return null;
        }else{

        }

            return skillArrays;
    }

    public void setSkillArrays(ArrayList<SkillModel> skillArrays) {
        this.skillArrays = skillArrays;
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
                "aptitude=" + aptitude +
                ", name='" + name + '\'' +
                ", sexuality=" + sexuality +
                ", strength_Initial=" + strength_Initial +
                ", intellect_Initial=" + intellect_Initial +
                ", dexterity_Initial=" + dexterity_Initial +
                ", physique_Initial=" + physique_Initial +
                ", spirit_Initial=" + spirit_Initial +
                ", luck_Initial=" + luck_Initial +
                ", fascination_Initial=" + fascination_Initial +
                ", skillLists='" + skillLists + '\'' +
                '}';
    }

    // 数据库列表
    public static class Column {
        //游戏所有人物的表名
        public static final String tableName_initial = "character_initial";
        public static final String id = "_id";
        public static final String character_id = "character_id";
        public static final String name = "name";   // 中文名
        public static final String name2 = "name2"; // 拼音名
        public static final String sexuality = "sexuality";
        public static final String aptitude = "aptitude";
        public static final String strength_Initial = "strength_Initial";
        public static final String intellect_Initial = "intellect_Initial";
        public static final String dexterity_Initial = "dexterity_Initial";
        public static final String physique_Initial = "physique_Initial";
        public static final String spirit_Initial = "spirit_Initial";
        public static final String luck_Initial = "luck_Initial";
        public static final String fascination_Initial = "fascination_Initial";
        public static final String skill_lists = "skill_lists";

        //用户已经拥有的武将，对比上面的表格，会额外多一些属性
        public static final String tableName_player = "character_player";
        public static final String level = "level";
        public static final String equipId = "equipId";         // 装备了什么东西
        public static final String treasureId = "treasureId";   // 携带了什么宝物
        public static final String experience = "experience";   // 拥有经验
    }
}
