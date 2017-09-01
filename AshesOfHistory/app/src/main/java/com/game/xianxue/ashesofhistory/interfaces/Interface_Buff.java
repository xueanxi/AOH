package com.game.xianxue.ashesofhistory.interfaces;

import com.game.xianxue.ashesofhistory.model.person.BattlePerson;

/**
 * 战斗接口
 * Created by anxi.xue on 8/30/17.
 */

public interface Interface_Buff {

    // Buff的类型
    public int BUFF_TYPE_NONE = 0;              // 无BUFF
    public int BUFF_TYPE_PROMOTE = 1;           // buff类型为提升属性
    public int BUFF_TYPE_REDUCE = 2;            // buff类型为降低属性
    public int BUFF_TYPE_RECOVER = 3;           // buff类型为恢复体力
    public int BUFF_TYPE_DAMAGE = 3;            // buff类型为持续伤害


    public int BUFF_LEVEL_LIMIT_MAX  = 5;       // buff最高等级限制
    public int BUFF_LEVEL_LIMIT_MINI = 1;       // buff最低等级限制

    /**
     * Buff 对一个目标开始生效
     */
     void start(BattlePerson person);

    /**
     * Buff 对一个目标作用停止
     * @param remove
     */
    void stop(BattlePerson remove);
}
