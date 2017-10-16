package com.game.xianxue.ashesofhistory.interfaces;

import com.game.xianxue.ashesofhistory.game.model.TeamModel;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;

/**
 * 技能的接口
 * Created by anxi.xue on 8/30/17.
 */

public interface Interface_Skill {

    // =======================技能属性 通用 部分===========================
    // 技能的等级
    int SKILL_LIMIT_MINI_LEVEL = 1;     //技能的等级下限
    int SKILL_LIMIT_MAX_LEVEL = 5;      //技能的等级上限

    // 技能的类型
    int SKILL_TYPE_DAMGE = 0;           // 技能类型为攻击类型
    int SKILL_TYPE_RECOVER = 1;         // 技能类型为恢复类
    int SKILL_TYPE_ASSIST = 2;          // 技能伤害为辅助

    // 技能的性质
    int SKILL_NATURE_ACTIVE = 0;        // 技能性质为主动释放类型
    int SKILL_NATURE_PASSIVITY = 1;     // 技能性质为被动

    // 技能的命中率
    float SKILL_ACCURACY_RATE_MUST_SUCCESS = -1f;  // 技能命中率为必中

    // 技能的持续时间
    int SKILL_LAST_TIME_UNLIMIT = -1;   // 无线持续时间，维持到战斗结束

    // 技能的作用范围
    int SKILL_RANGE_AOE = -1;           // -1表示，技能范围是全场
    int SKILL_RANGE_SELF = 0;              // 0表示只能对自己释放

    // 技能的作用人数
    int SKILL_EFFECT_NUMBER_ALL = 0;      // 技能作用人数为无限

    // 技能触发效果阵营
    int SKILL_CAMP_ENEMY = 0;              // 技能目标：敌人
    int SKILL_CAMP_FRIEND = 1;             // 技能目标：友军

    // 技能触发效果目标
    int SKILL_TARGET_RANDOM = 0;           // 触发目标为攻击范围内的随机目标
    int SKILL_TARGET_MINI_HP = 1;          // 触发目标为攻击范围内生命值最低的目标
    int SKILL_TARGET_MAX_HP = 2;           // 触发目标为攻击范围内生命值最高的目标
    int SKILL_TARGET_DISTANCE_NEAR = 3;    // 触发目标为攻击范围内最近的目标
    int SKILL_TARGET_DISTANCE_FAR = 4;     // 触发目标为攻击范围内最远的目标

    // =======================攻击技能 部分===========================
    int SKILL_DAMAGE_TYPE_PHYSICS = 0;         // 伤害为物理伤害
    int SKILL_DAMAGE_TYPE_MAGIC = 1;           // 伤害为魔法伤害
    int SKILL_DAMAGE_TYPE_REAL = 2;            // 伤害为真实伤害
    int SKILL_DAMAGE_TYPE_PHYSICS_PERCENT = 3; // 当前生命百分比物理伤害
    int SKILL_DAMAGE_TYPE_MAGIC_PERCENT = 4;   // 当前生百分比魔法伤害
    int SKILL_DAMAGE_TYPE_REAL_PERCENT = 5;    // 当前生百分比真实伤害
    int SKILL_DAMAGE_TYPE_PHYSICS_PERCENT_MAX = 6; // 最大生命百分比物理伤害
    int SKILL_DAMAGE_TYPE_MAGIC_PERCENT_MAX = 7;   // 最大生百分比魔法伤害
    int SKILL_DAMAGE_TYPE_REAL_PERCENT_MAX = 8;    // 最大生百分比真实伤害

    // =======================恢复技能 部分===========================


/*    *//**
     * 技能辅助
     *
     * @param personRelease 释放技能的人
     * @param teamReceive   承受这个技能的队伍
     *//*
    void assist(BattlePerson personRelease, TeamModel teamReceive);
    *//**
     * 技能恢复
     *
     * @param personRelease 释放技能的人
     * @param teamReceive   承受这个技能的队伍
     *//*
    void recover(BattlePerson personRelease, TeamModel teamReceive);

    *//**
     * 技能攻击
     *
     * @param personRelease 释放技能的人
     * @param teamReceive   承受这个技能的队伍
     *//*
    void attack(BattlePerson personRelease, TeamModel teamReceive);*/
}
