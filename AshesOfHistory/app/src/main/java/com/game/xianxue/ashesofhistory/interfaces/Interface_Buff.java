package com.game.xianxue.ashesofhistory.interfaces;

import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;

/**
 * Buff接口
 * Created by anxi.xue on 8/30/17.
 */

public interface Interface_Buff {

    // ==================== Buff 的效果 start=======================
    int BUFF_STRENGTH = 1;         // 辅助效果：力量
    int BUFF_INTELLECT = 2;        // 辅助效果：智力
    int BUFF_DEXTERITY = 3;        // 辅助效果：敏捷
    int BUFF_PHYSIQUE = 4;         // 辅助效果：体制
    int BUFF_SPIRIT = 5;           // 辅助效果：精神
    int BUFF_FASCINATION = 6;      // 辅助效果：魅力
    int BUFF_LUCK = 7;             // 辅助效果：幸运

    int BUFF_PHYSICDAMAGE = 8;     // 辅助效果：物理伤害
    int BUFF_MAGICDAMAGE = 9;      // 辅助效果：魔法伤害
    int BUFF_REALDAMAGE = 10;      // 辅助效果：真实伤害
    int BUFF_PHYSICSPENETRATE = 11;// 辅助效果：物理穿透
    int BUFF_MAGICPENETRATE = 12;  // 辅助效果：魔法穿透
    int BUFF_ACCURACY = 13;        // 辅助效果：命中率
    int BUFF_CRITERATE = 14;       // 辅助效果：暴击率
    int BUFF_CRITEDAMAGE = 15;     // 辅助效果：暴击伤害
    int BUFF_ARMOR = 16;           // 辅助效果：护甲（物抗）
    int BUFF_MAGICRESIST = 17;     // 辅助效果：魔抗
    int BUFF_DODGE = 18;           // 辅助效果：闪避值（闪避成功承受10%的物理伤害 或者 承受30%的魔法伤害）
    int BUFF_BLOCK = 19;           // 辅助效果：格档值（格档成功只承受30%物理伤害 或者 承受70%魔法伤害）
    int BUFF_ACTIONSPEED = 20;     // 辅助效果：速度
    int BUFF_HPRESTORE = 21;       // 辅助效果：生命恢复。发起进攻时，生命恢复
    int BUFF_ACTIVEVALUE = 22;     // 辅助效果：行动值。执行一次行动，需要的行动值（越少越好）
    int BUFF_REDUCEBECRITERATE = 23;// 辅助效果：抗暴击
    int BUFF_HP_MAX = 24;          // 辅助效果：当前生命值
    int BUFF_SKILL_RATE = 25;      // 辅助效果:技能发动概率
    int BUFF_ATTACK_NUMBER= 26;    // 辅助效果:攻击目标数量
    int BUFF_ATTACK_RANGE = 27;    // 辅助效果:攻击范围
    // ==================== Buff 的效果 end=======================

    int BUFF_LEVEL_LIMIT_MAX = 5;       // buff最高等级限制
    int BUFF_LEVEL_LIMIT_MINI = 1;      // buff最低等级限制

    int BUFF_TIME_UNLIMITED = 0;        // buff的持续时间为无限

    int BUFF_TYPE_LAST = 1;             // 效果每回合都会触发，不断叠加
    int BUFF_TYPE_ONCE = 0;             // 只对人物加持一次
}
