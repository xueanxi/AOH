package com.game.xianxue.ashesofhistory.model;

import com.game.xianxue.ashesofhistory.model.person.BattlePerson;

import java.io.Serializable;

/**
 * 技能模型
 */
public class SkillModel implements Serializable{
    public static final int SKILL_LIMIT_MAX_LEVEL = 5;      //技能的等级上限
    public static final int SKILL_LIMIT_MINI_LEVEL = 1;     //技能的等级下限

    public static final int SKILL_TYPE_DAMGE = 1;           // 技能类型为伤害类型
    public static final int SKILL_TYPE_RECOVER = 2;         // 技能类型为恢复类
    public static final int SKILL_TYPE_ASSIST = 3;          // 技能伤害为辅助

    public static final int SKILL_NATURE_ACTIVE = 1;        // 技能性质为主动释放类型
    public static final int SKILL_NATURE_PASSIVITY = 2;     // 技能性质为被动

    public String name;                        // 技能名字
    public int id;                             // 技能id
    public int level = 1;                      // 当前技能等级，技能等级会影响技能的效果

    /**
     * 技能伤害由两个部分组成：技能伤害固定部分 + 技能伤害随武将浮动部分
     */
    public DamgeModel damgeModel;              // 技能造成的伤害

    public int skillType;                      // 技能类型（分为攻击技能，辅助技能，恢复技能）
    public int naturetype;                     // 技能的性质 (分为主动技能和被动技能)

    public float accuracyRate = -1f;           // 技能命中率，如果为-1f 则默认使用武将本身的命中率，如果不为-1f,则为技能发动成功的概率
    public float triggerProbability = -1f;     // 技能触发概率,如果为-1f,则默认为补充概率，即其他技能概率计算完了之后，剩下的触发概率都分给这个技能

    public String introduce;                   // 技能说明
    public boolean isLocked;                   // 是否锁定，当人物没有达到技能解锁的时候，该技能不能使用


    public void setLevel(int level) {

    }

    /**
     * 技能生效 单人对单人的技能
     *
     * @param sponsor 技能发起者
     * @param target  技能目标
     */
    public void getDamage(BattlePerson sponsor, BattlePerson target) {

    }

    /**
     * 技能生效 单人对多人的技能
     *
     * @param sponsor 技能发起者
     * @param target  技能目标
     */
    public void doWork(BattlePerson sponsor, TeamModel target) {

    }
}
