package com.game.xianxue.ashesofhistory.game.skill;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.TeamModel;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Skill;

/**
 * 技能的实体类 继承自 SkillBase 所有拥有所有技能的变量
 * 同时拥有一个 SkillBase 成员变量用于保存最原始的数据，方便恢复
 */

public class SkillBattle extends SkillBase implements Interface_Skill {
    private static final String TAG = "SkillBattle";
    private SkillBase base;                             // 技能的初始数据
    private int level = SKILL_LIMIT_MINI_LEVEL;         // 技能的等级
    private int recoverTime;                            // 技能恢复冷却还需要多少时间

    /**
     * 构造方法一
     * @param base
     */
    public SkillBattle(SkillBase base) {
        this.base = base;

        this.skillId = base.skillId;                    // 技能id,唯一标识一个技能
        this.name = base.name;                          // 技能名字
        this.introduce = base.introduce;                // 技能说明
        this.skillNature = base.skillNature;            // 技能的性质(分为0:主动技能和 1:被动技能)
        this.skillType = base.skillType;                // 技能类型(分为0:攻击 1:恢复 2:辅助)
        this.accuracyRate = base.accuracyRate;          // 技能命中率(技能命中率 = 人物命中率 * 技能命中率 Ex：-1 则表示必中)
        this.criteRate = base.criteRate;                // 技能暴击率(技能触发暴击的概率 = 人物暴击率 * 技能暴击率 Ex：-1表示必暴击)
        this.effectRate = base.effectRate;              // 效果触发概率(当有技能特效时，技能触发的概率 0~1,无视对方的闪避）
        this.cdTime = base.cdTime;                      // 技能的冷却回合数
        this.range = base.range;                        // 技能的影响范围(0～9 :0的意思是只能对自己释放)
        this.effectNumber = base.effectNumber;          // 技能的作用数量(0～10 :0的意思对全场AOE)
        this.effectCamp = base.effectCamp;              // 技能的目标阵营(0对敌人 1对友方)
        this.effectTarget = base.effectTarget;          // 技能选择作用在什么的人物上面(0范围内随机目标 1血最少 2血最多 3最近 4最远)
        this.damagePenetrate = base.damagePenetrate;    // 伤害穿透率
        this.damageType = base.damageType;              // 技能伤害类型(0物理 1魔法 2真实 3百分比物理 4百分比魔法 5百分比真实)
        this.damageConstant = base.damageConstant;      // 技能伤害 固定部分(一个技能的伤害为 技能固定部分+武将能力*浮动部分)
        this.damageFluctuate = base.damageFluctuate;    // 技能伤害 浮动部分(一个技能的伤害为 技能固定部分+武将能力*浮动部分)
        this.assistEffect = base.assistEffect;          // 技能的额外效果，指向buff的id
        // 技能升级相关
        this.levelUpConstant = base.levelUpConstant;    //技能固定伤害部分升级提升
        this.levelUpFluctuate = base.levelUpFluctuate;  //技能浮动伤害部分升级提升
        this.levelUpRange = base.levelUpRange;          //技能作用范围升级提升
        this.levelUpNumber = base.levelUpNumber;        //技能作用人数升级提升
        this.levelUpEffectRate = base.levelUpEffectRate;//技能辅助效果触发几率升级提升
        this.levelUpPenetrate = base.levelUpPenetrate;  //技能穿透升级提升
        this.levelUpCDTime = base.levelUpCDTime;        //技能冷却时间升级提升

        setLevel(1);
    }

    /**
     * 构造方法2
     * @param base
     * @param level
     */
    public SkillBattle(SkillBase base,int level) {
        this.base = base;

        this.skillId = base.skillId;                    // 技能id,唯一标识一个技能
        this.name = base.name;                          // 技能名字
        this.introduce = base.introduce;                // 技能说明
        this.skillNature = base.skillNature;            // 技能的性质(分为0:主动技能和 1:被动技能)
        this.skillType = base.skillType;                // 技能类型(分为0:攻击 1:恢复 2:辅助)
        this.accuracyRate = base.accuracyRate;          // 技能命中率(技能命中率 = 人物命中率 * 技能命中率 Ex：-1 则表示必中)
        this.criteRate = base.criteRate;                // 技能暴击率(技能触发暴击的概率 = 人物暴击率 * 技能暴击率 Ex：-1表示必暴击)
        this.effectRate = base.effectRate;              // 效果触发概率(当有技能特效时，技能触发的概率 0~1,无视对方的闪避）
        this.cdTime = base.cdTime;                      // 技能的冷却回合数
        this.range = base.range;                        // 技能的影响范围(0～9 :0的意思是只能对自己释放)
        this.effectNumber = base.effectNumber;          // 技能的作用数量(0～10 :0的意思对全场AOE)
        this.effectCamp = base.effectCamp;              // 技能的目标阵营(0对敌人 1对友方)
        this.effectTarget = base.effectTarget;          // 技能选择作用在什么的人物上面(0范围内随机目标 1血最少 2血最多 3最近 4最远)
        this.damagePenetrate = base.damagePenetrate;    // 伤害穿透率
        this.damageType = base.damageType;              // 技能伤害类型(0物理 1魔法 2真实 3百分比物理 4百分比魔法 5百分比真实)
        this.damageConstant = base.damageConstant;      // 技能伤害 固定部分(一个技能的伤害为 技能固定部分+武将能力*浮动部分)
        this.damageFluctuate = base.damageFluctuate;    // 技能伤害 浮动部分(一个技能的伤害为 技能固定部分+武将能力*浮动部分)
        this.assistEffect = base.assistEffect;          // 技能的额外效果，指向buff的id
        // 技能升级相关
        this.levelUpConstant = base.levelUpConstant;    //技能固定伤害部分升级提升
        this.levelUpFluctuate = base.levelUpFluctuate;  //技能浮动伤害部分升级提升
        this.levelUpRange = base.levelUpRange;          //技能作用范围升级提升
        this.levelUpNumber = base.levelUpNumber;        //技能作用人数升级提升
        this.levelUpEffectRate = base.levelUpEffectRate;//技能辅助效果触发几率升级提升
        this.levelUpPenetrate = base.levelUpPenetrate;  //技能穿透升级提升
        this.levelUpCDTime = base.levelUpCDTime;        //技能冷却时间升级提升

        setLevel(level);
    }


    public int getLevel() {
        return level;
    }

    /**
     * 设置技能的等级 并且更新受技能等级影响的值
     * @param level
     */
    public void setLevel(int level) {
        if(level < SKILL_LIMIT_MINI_LEVEL){
            level = SKILL_LIMIT_MINI_LEVEL;
        } else if(level > SKILL_LIMIT_MAX_LEVEL){
            level = SKILL_LIMIT_MAX_LEVEL;
        }
        this.level = level;

        this.damageConstant = base.damageConstant + (level -1)*base.levelUpConstant;
        this.damageFluctuate = base.damageFluctuate + (level -1)*base.levelUpFluctuate;
        this.range = (int)(base.range + (level -1)*base.levelUpRange);
        this.effectNumber = (int)(base.effectNumber + (level -1)*base.levelUpNumber);
        this.cdTime = (int)(base.cdTime + (level -1)*base.levelUpCDTime);
        this.damagePenetrate = (int)(base.damagePenetrate + (level -1)*base.levelUpPenetrate);
        this.effectRate = base.effectRate + (level -1)*base.levelUpEffectRate;

        //SimpleLog.logd(TAG,"setStartLevel before:"+this.name+" number= "+effectNumber);
    }

    public int getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(int recoverTime) {
        this.recoverTime = recoverTime;
    }

    /**
     * 过去一回合之后，技能的cd时间减少1
     */
    public void passTime() {
        if(this.recoverTime >0){
            this.recoverTime--;
        }
    }

    @Override
    public String toString() {
        return "SkillBattle{" +
                " startLevel=" + level +
                ", recoverTime=" + recoverTime +
                "} " + super.toString();
    }
}
