package com.game.xianxue.ashesofhistory.game.skill;

/**
 * 技能最基础的类
 * 这个类是保存从数据库取出来的数据
 */
public class SkillBase{
    protected int skillId;                             // 技能id,唯一标识一个技能
    protected String name;                             // 技能名字
    protected String introduce;                        // 技能说明
    protected int skillNature;                         // 技能的性质(0主动 1：被动)
    protected int skillType;                           // 技能类型分为攻击技能，辅助技能，恢复技能）
    protected float accuracyRate;                      // 技能命中率(技能命中率 = 人物命中率 * 技能命中率 Ex：-1 则表示必中)
    protected float criteRate;                         // 技能暴击率(技能触发暴击的概率 = 人物暴击率 * 技能暴击率 Ex：-1表示必暴击)
    protected float effectRate;                        // 效果触发概率(当有技能特效时，技能触发的概率 0~1,无视对方的闪避）
    protected int cdTime;                              // 技能的冷却回合数
    protected int range;                               // 技能的影响范围(-1~9 其中-1表示范围是全场,0表示只能对自己释放）
    protected int effectNumber;                        // 技能的作用数量(0～10 :0的意思对全场AOE)
    protected int effectCamp;                          // 技能的目标阵营(0对敌人 1对友方)
    protected int effectTarget;                        // 技能选择作用在什么的人物上面(0范围内随机目标 1血最少 2血最多 3最近 4最远)
    protected int damageType;                          // 技能伤害类型(0物理 1魔法 2真实 3百分比物理 4百分比魔法 5百分比真实)
    protected float damageConstant = 0;                // 技能伤害 固定部分(一个技能的伤害为 技能固定部分 + 人物能力*浮动部分)
    protected float damageFluctuate = 0;               // 技能伤害 浮动部分(一个技能的伤害为 技能固定部分 + 人物能力*浮动部分)
    protected float damagePenetrate;                   // 伤害穿透率
    protected int assistEffect;                        // 技能的额外效果，指向buff的id

    // 技能升级相关
    protected float levelUpConstant;                   // 技能固定伤害部分升级提升
    protected float levelUpFluctuate;                  // 技能浮动伤害部分升级提升
    protected float levelUpRange;                      // 技能作用范围升级提升
    protected float levelUpNumber;                     // 技能作用人数升级提升
    protected float levelUpEffectRate;                 // 技能辅助效果触发几率升级提升
    protected float levelUpPenetrate;                  // 技能穿透升级提升
    protected float levelUpCDTime;                     // 技能冷却时间升级提升

    public SkillBase(){}

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSkillType() {
        return skillType;
    }

    public void setSkillType(int skillType) {
        this.skillType = skillType;
    }

    public int getSkillNature() {
        return skillNature;
    }

    public void setSkillNature(int skillNature) {
        this.skillNature = skillNature;
    }

    public float getAccuracyRate() {
        return accuracyRate;
    }

    public void setAccuracyRate(float accuracyRate) {
        this.accuracyRate = accuracyRate;
    }

    public float getEffectRate() {
        return effectRate;
    }

    public void setEffectRate(float effectRate) {
        this.effectRate = effectRate;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getCdTime() {
        return cdTime;
    }

    public void setCdTime(int cdTime) {
        this.cdTime = cdTime;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getEffectNumber() {
        return effectNumber;
    }

    public void setEffectNumber(int effectNumber) {
        this.effectNumber = effectNumber;
    }

    public int getEffectCamp() {
        return effectCamp;
    }

    public void setEffectCamp(int effectCamp) {
        this.effectCamp = effectCamp;
    }

    public int getEffectTarget() {
        return effectTarget;
    }

    public void setEffectTarget(int effectTarget) {
        this.effectTarget = effectTarget;
    }

    public int getDamageType() {
        return damageType;
    }

    public void setDamageType(int damageType) {
        this.damageType = damageType;
    }

    public float getDamageConstant() {
        return damageConstant;
    }

    public void setDamageConstant(float damageConstant) {
        this.damageConstant = damageConstant;
    }

    public float getDamageFluctuate() {
        return damageFluctuate;
    }

    public void setDamageFluctuate(float damageFluctuate) {
        this.damageFluctuate = damageFluctuate;
    }

    public int getAssistEffect() {
        return assistEffect;
    }

    public void setAssistEffect(int assistEffect) {
        this.assistEffect = assistEffect;
    }

    public float getCriteRate() {
        return criteRate;
    }

    public void setCriteRate(float criteRate) {
        this.criteRate = criteRate;
    }

    public float getDamagePenetrate() {
        return damagePenetrate;
    }

    public void setDamagePenetrate(float damagePenetrate) {
        this.damagePenetrate = damagePenetrate;
    }

    public float getLevelUpConstant() {
        return levelUpConstant;
    }

    public void setLevelUpConstant(float levelUpConstant) {
        this.levelUpConstant = levelUpConstant;
    }

    public float getLevelUpFluctuate() {
        return levelUpFluctuate;
    }

    public void setLevelUpFluctuate(float levelUpFluctuate) {
        this.levelUpFluctuate = levelUpFluctuate;
    }

    public float getLevelUpRange() {
        return levelUpRange;
    }

    public void setLevelUpRange(float levelUpRange) {
        this.levelUpRange = levelUpRange;
    }

    public float getLevelUpNumber() {
        return levelUpNumber;
    }

    public void setLevelUpNumber(float levelUpNumber) {
        this.levelUpNumber = levelUpNumber;
    }

    public float getLevelUpEffectRate() {
        return levelUpEffectRate;
    }

    public void setLevelUpEffectRate(float levelUpEffectRate) {
        this.levelUpEffectRate = levelUpEffectRate;
    }

    public float getLevelUpPenetrate() {
        return levelUpPenetrate;
    }

    public void setLevelUpPenetrate(float levelUpPenetrate) {
        this.levelUpPenetrate = levelUpPenetrate;
    }

    public float getLevelUpCDTime() {
        return levelUpCDTime;
    }

    public void setLevelUpCDTime(float levelUpCDTime) {
        this.levelUpCDTime = levelUpCDTime;
    }

    @Override
    public String toString() {
        return "SkillBase{" +
                "skillId=" + skillId +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", skillNature=" + skillNature +
                ", skillType=" + skillType +
                ", accuracyRate=" + accuracyRate +
                ", criteRate=" + criteRate +
                ", effectRate=" + effectRate +
                ", cdTime=" + cdTime +
                ", range=" + range +
                ", effectNumber=" + effectNumber +
                ", effectCamp=" + effectCamp +
                ", effectTarget=" + effectTarget +
                ", damagePenetrate=" + damagePenetrate +
                ", damageType=" + damageType +
                ", damageConstant=" + damageConstant +
                ", damageFluctuate=" + damageFluctuate +
                ", assistEffect='" + assistEffect + '\'' +
                ", levelUpConstant=" + levelUpConstant +
                ", levelUpFluctuate=" + levelUpFluctuate +
                ", levelUpRange=" + levelUpRange +
                ", levelUpNumber=" + levelUpNumber +
                ", levelUpEffectRate=" + levelUpEffectRate +
                ", levelUpPenetrate=" + levelUpPenetrate +
                ", levelUpCDTime=" + levelUpCDTime +
                '}';
    }
}
