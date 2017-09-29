package com.game.xianxue.ashesofhistory.game.model.person;

import com.game.xianxue.ashesofhistory.game.skill.SkillBase;

/**
 * 存放人物技能相关的信息
 */

public class SkillBean {
    protected int skillID;              // 技能ID
    protected SkillBase skillBase;      // 技能
    protected int unLockLevel;          // 解锁等级
    protected int unLockLevel;          // 技能解锁之后的等级
    protected int grow;                 // 技能的成长，比如当grow等于3时,技能解锁之后，人物每升3级，技能等级提高一级
}
