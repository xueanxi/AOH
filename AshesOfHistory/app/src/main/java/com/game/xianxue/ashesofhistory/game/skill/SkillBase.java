package com.game.xianxue.ashesofhistory.game.skill;

import com.game.xianxue.ashesofhistory.game.interfaces.Interface_Skill;
import com.game.xianxue.ashesofhistory.model.TeamModel;
import com.game.xianxue.ashesofhistory.model.person.BattlePerson;

/**
 * 技能实体类 模型
 */
public class SkillBase implements Interface_Skill {
    public int skillId;                             // 技能id
    public String name;                             // 技能名字
    public String introduce;                        // 技能说明
    public int naturetype = SKILL_NATURE_ACTIVE;    // 技能的性质       (分为主动技能和被动技能)
    public int skillType = SKILL_TYPE_DAMGE;        // 技能类型        （分为攻击技能，辅助技能，恢复技能）
    public float accuracyRate = 1.0f;               // 技能命中率        (如果为1.0则默认使用武将本身的命中率，如果必中为2.0,其他0～1.0为普通命中率
    public float effectRate = 1.0f;                 // 技能额外效果触发概率 (如果为1.0则默认使用武将本身的命中率，如果必中为2.0,其他0～1.0为普通命中率
    public int cdTime = 0;                          // 技能的冷却回合数
    public int time = SKILL_LAST_TIME_0;            // 伤害持续回合，普通技能一般都是0个回合，除了那些持续类型的伤害
    public int range = SKILL_RANGE_1;               // 技能的影响范围
    public int effectNumber = SKILL_EFFECT_NUMBER_0;// 技能的作用数量
    public int level = SKILL_LIMIT_MINI_LEVEL;      // 当前技能等级     技能等级会影响技能的效果
    public float effectUp = 0;                      // 技能升级的时候，提升效果
    public int effectCamp = SKILL_CAMP_ENEMY;       // 技能的目标阵营
    public int effectTarget = SKILL_TARGET_RANDOM;  // 技能选择作用在什么的人物上面

    // 攻击、恢复类型
    public int damageType = DAMGE_TYPE_PHYSICS;     // 技能伤害类型
    public int damageConstant = 0;                  // 技能伤害 固定部分(一个技能的伤害为 技能固定部分+武将能力*浮动部分)
    public float damageFluctuate = 0;               // 技能伤害 浮动部分(一个技能的伤害为 技能固定部分+武将能力*浮动部分)

    // 辅助类型
    public String assisteffect = "0";   // 辅助效果

    public SkillBase(){}

    public SkillBase(int id, String name, int skillType, int naturetype, float accuracyRate, float effectRate, String introduce,
                     int cdTime, int time, int range, int effectNumber, int level, float effectUp, int damageType, int effectCamp, int effectTarget,
                     int damageConstant, float damageFluctuate, String assisteffect) {
        this.skillId = id;
        this.name = name;
        this.skillType = skillType;
        this.naturetype = naturetype;
        this.accuracyRate = accuracyRate;
        this.effectRate = effectRate;
        this.introduce = introduce;
        this.cdTime = cdTime;
        this.time = time;
        this.range = range;
        this.effectNumber = effectNumber;
        this.effectUp = effectUp;
        this.damageType = damageType;
        this.effectCamp = effectCamp;
        this.damageConstant = damageConstant;
        this.damageFluctuate = damageFluctuate;
        this.assisteffect = assisteffect;
        this.effectTarget = effectTarget;
        levelUp(level);
    }

    /**
     * 设置技能的等级
     */
    private void levelUp(int level) {
        if (level < SKILL_LIMIT_MINI_LEVEL) {
            this.level = SKILL_LIMIT_MINI_LEVEL;
        } else if (level > SKILL_LIMIT_MAX_LEVEL) {
            this.level = SKILL_LIMIT_MAX_LEVEL;
        } else {
            this.level = level;
        }

        // 提升了多少级
        int uplevel = this.level - SKILL_LIMIT_MINI_LEVEL;
        switch (this.skillType) {
            case SKILL_TYPE_DAMGE:
            case SKILL_TYPE_RECOVER:
                this.damageConstant += uplevel * effectUp;
                this.damageFluctuate += uplevel * effectUp;
                break;
            case SKILL_TYPE_ASSIST:
                assisteffect += uplevel * effectUp;
                break;
        }
    }

    @Override
    public void attack(BattlePerson personRelease, TeamModel teamReceive) {

    }

    @Override
    public void recover(BattlePerson personRelease, TeamModel teamReceive) {

    }

    @Override
    public void assist(BattlePerson personRelease, TeamModel teamReceive) {

    }

    public void doAction(BattlePerson personRelease, TeamModel teamReceive) {
        switch (skillType) {
            case SKILL_TYPE_DAMGE:
                attack(personRelease, teamReceive);
                break;
            case SKILL_TYPE_RECOVER:
                recover(personRelease, teamReceive);
                break;
            case SKILL_TYPE_ASSIST:
                assist(personRelease, teamReceive);
                break;
            default:
                break;
        }
    }


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

    public int getNaturetype() {
        return naturetype;
    }

    public void setNaturetype(int naturetype) {
        this.naturetype = naturetype;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getEffectUp() {
        return effectUp;
    }

    public void setEffectUp(float effectUp) {
        this.effectUp = effectUp;
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

    public int getDamageConstant() {
        return damageConstant;
    }

    public void setDamageConstant(int damageConstant) {
        this.damageConstant = damageConstant;
    }

    public float getDamageFluctuate() {
        return damageFluctuate;
    }

    public void setDamageFluctuate(float damageFluctuate) {
        this.damageFluctuate = damageFluctuate;
    }

    public String getAssisteffect() {
        return assisteffect;
    }

    public void setAssisteffect(String assisteffect) {
        this.assisteffect = assisteffect;
    }

    @Override
    public String toString() {
        return "SkillBase{" +
                "skillId=" + skillId +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", naturetype=" + naturetype +
                ", skillType=" + skillType +
                ", accuracyRate=" + accuracyRate +
                ", effectRate=" + effectRate +
                ", cdTime=" + cdTime +
                ", time=" + time +
                ", range=" + range +
                ", effectNumber=" + effectNumber +
                ", level=" + level +
                ", effectUp=" + effectUp +
                ", effectCamp=" + effectCamp +
                ", effectTarget=" + effectTarget +
                ", damageType=" + damageType +
                ", damageConstant=" + damageConstant +
                ", damageFluctuate=" + damageFluctuate +
                ", assisteffect='" + assisteffect + '\'' +
                '}';
    }
}
