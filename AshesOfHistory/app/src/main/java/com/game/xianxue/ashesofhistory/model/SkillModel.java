package com.game.xianxue.ashesofhistory.model;

/**
 * 技能模型
 */
public class SkillModel {

    public static final int SKILL_TYPE_DAMGE = 1;    // 技能类型为伤害类型
    public static final int SKILL_TYPE_Restore = 2;  // 技能类型为恢复类
    public static final int SKILL_TYPE_ASSIST = 3;   // 技能伤害为辅助

    public static final int DAMGE_TYPE_PHYSICS = 1; // 伤害为物理伤害
    public static final int DAMGE_TYPE_MAGIC = 2;   // 伤害为魔法伤害
    public static final int DAMGE_TYPE_REAL = 3;    // 伤害为真实伤害

    int level = 1;// 技能等级
    int name;//技能名字
    int id; // 技能id
    int introduce; // 技能说明

    int damage; //技能伤害
    int skillType; // 技能类型（分为攻击技能，辅助技能，恢复技能）
    int damgeType;//伤害类型

    int triggerProbability;//技能触发概率
    boolean isLocked; //是否锁定，当人物没有达到技能解锁的时候，该技能不能使用
}
