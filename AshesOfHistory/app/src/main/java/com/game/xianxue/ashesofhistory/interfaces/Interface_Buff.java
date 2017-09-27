package com.game.xianxue.ashesofhistory.interfaces;

import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;

/**
 * Buff接口
 * Created by anxi.xue on 8/30/17.
 */

public interface Interface_Buff {

    // Buff 的效果
    public final int BUFF_STRENGTH = 1;         // 辅助效果：力量
    public final int BUFF_INTELLECT = 2;        // 辅助效果：智力
    public final int BUFF_DEXTERITY = 3;        // 辅助效果：敏捷
    public final int BUFF_PHYSIQUE = 4;         // 辅助效果：体制
    public final int BUFF_SPIRIT = 5;           // 辅助效果：精神
    public final int BUFF_FASCINATION = 6;      // 辅助效果：魅力
    public final int BUFF_LUCK = 7;             // 辅助效果：幸运

    public final int BUFF_PHYSICDAMAGE = 8;     // 辅助效果：物理伤害
    public final int BUFF_MAGICDAMAGE = 9;      // 辅助效果：魔法伤害
    public final int BUFF_REALDAMAGE = 10;      // 辅助效果：真实伤害
    public final int BUFF_PHYSICSPENETRATE = 11;// 辅助效果：物理穿透
    public final int BUFF_MAGICPENETRATE = 12;  // 辅助效果：魔法穿透
    public final int BUFF_ACCURACY = 13;        // 辅助效果：命中率
    public final int BUFF_CRITERATE = 14;       // 辅助效果：暴击率
    public final int BUFF_CRITEDAMAGE = 15;     // 辅助效果：暴击伤害
    public final int BUFF_ARMOR = 16;           // 辅助效果：护甲（物抗）
    public final int BUFF_MAGICRESIST = 17;     // 辅助效果：魔抗
    public final int BUFF_DODGE = 18;           // 辅助效果：闪避值（闪避成功承受10%的物理伤害 或者 承受30%的魔法伤害）
    public final int BUFF_BLOCK = 19;           // 辅助效果：格档值（格档成功只承受30%物理伤害 或者 承受70%魔法伤害）
    public final int BUFF_ACTIONSPEED = 20;     // 辅助效果：速度
    public final int BUFF_HPRESTORE = 21;       // 辅助效果：生命恢复。发起进攻时，生命恢复
    public final int BUFF_ACTIVEVALUE = 22;     // 辅助效果：行动值。执行一次行动，需要的行动值（越少越好）
    public final int BUFF_REDUCEBECRITERATE = 23;// 辅助效果：抗暴击
    public final int BUFF_HP = 24;              // 辅助效果：生命值

    // Buff的类型
    public int BUFF_TYPE_NONE = 0;              // 无BUFF
    public int BUFF_TYPE_PROMOTE = 1;           // buff类型为提升属性
    public int BUFF_TYPE_REDUCE = 2;            // buff类型为降低属性
    public int BUFF_TYPE_RECOVER = 3;           // buff类型为恢复体力
    public int BUFF_TYPE_DAMAGE = 3;            // buff类型为持续伤害


    public int BUFF_LEVEL_LIMIT_MAX = 5;       // buff最高等级限制
    public int BUFF_LEVEL_LIMIT_MINI = 1;       // buff最低等级限制

    void start(BasePerson person);

    void stop(BasePerson person);
}
