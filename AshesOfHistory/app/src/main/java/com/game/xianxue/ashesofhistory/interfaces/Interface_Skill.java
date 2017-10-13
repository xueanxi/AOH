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
    float SKILL_ACCURACY_RATE_SUCCESS = 2.0f;  // 技能命中率为必中
    float SKILL_ACCURACY_RATE_FOLLOW = 1.0f;   // 技能命中率为伴随人物的命中率

    // 技能的持续时间
    int SKILL_LAST_TIME_UNLIMIT = 10;   // 无线持续时间，维持到战斗结束
    int SKILL_LAST_TIME_0 = 0;          // 像普通攻击一样的一次性攻击技能
    int SKILL_LAST_TIME_1 = 1;          // 技能命中后，会令敌人持续后续伤害1个回合
    int SKILL_LAST_TIME_2 = 2;          // 技能命中后，会令敌人持续后续伤害2个回合
    int SKILL_LAST_TIME_3 = 3;          // 技能命中后，会令敌人持续后续伤害3个回合
    int SKILL_LAST_TIME_4 = 4;          // 技能命中后，会令敌人持续后续伤害4个回合
    int SKILL_LAST_TIME_5 = 5;          // 技能命中后，会令敌人持续后续伤害5个回合

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
    public int DAMGE_TYPE_PHYSICS = 0;         // 伤害为物理伤害
    public int DAMGE_TYPE_MAGIC = 1;           // 伤害为魔法伤害
    public int DAMGE_TYPE_REAL = 2;            // 伤害为真实伤害
    public int DAMGE_TYPE_PHYSICS_PERCENT = 3; // 百分比物理伤害
    public int DAMGE_TYPE_MAGIC_PERCENT = 4;   // 百分比魔法伤害
    public int DAMGE_TYPE_REAL_PERCENT = 5;    // 百分比真实伤害

    public int ATTACK_EFFECT_CRITE = 0;              // 攻击特效：暴击     造成暴击伤害
    public int ATTACK_EFFECT_PHYSICS_PENETRATE = 1;  // 攻击特效：物理穿透 无视一定护甲
    public int ATTACK_EFFECT_MAGIC_PENETRATE = 2;    // 攻击特效：魔法穿透 无视一定魔抗
    public int ATTACK_EFFECT_RECOVER_HP = 3;         // 攻击特效：吸血     吸收一定HP


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
