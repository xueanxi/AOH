package com.game.xianxue.ashesofhistory.model;


import com.game.xianxue.ashesofhistory.model.person.BattlePerson;

import java.io.Serializable;

/**
 * 伤害模型
 */
public class DamgeModel implements Serializable {

    public static final int DAMGE_TYPE_PHYSICS = 1;         // 伤害为物理伤害
    public static final int DAMGE_TYPE_MAGIC = 2;           // 伤害为魔法伤害
    public static final int DAMGE_TYPE_REAL = 3;            // 伤害为真实伤害
    public static final int DAMGE_TYPE_PHYSICS_PERCENT = 4; // 百分比物理伤害
    public static final int DAMGE_TYPE_MAGIC_PERCENT = 5;   // 百分比魔法伤害
    public static final int DAMGE_TYPE_REAL_PERCENT = 6;    // 百分比真实伤害

    private int level;                         // 技能等级
    public int damage1;                        // 技能伤害固定部分
    public float damage2;                      // 技能伤害随武将浮动部分,一般是一个百分比浮点数
    public int damageType;         // 伤害类型
    public int time;               // 伤害持续回合，普通技能一般都是一个回合，除了那些持续类型的伤害

    /**
     * 一个技能如果比较特殊，需要重写这个方法来计算伤害
     * @param player
     * @return
     */
    public int getDamageResult(BattlePerson player) {
        if (DAMGE_TYPE_PHYSICS == damageType) {
            return (int) Math.ceil(player.getPhysicDamage() * damage2 + damage1);
        } else if (DAMGE_TYPE_MAGIC == damageType) {
            return (int) Math.ceil(player.getMagicDamage() * damage2 + damage1);
        } else{
            return 0;
        }
    }

    /**
     * 如果技能的效果和等级有关，需要在这里处理
     * @param level
     */
    public void setLevel(int level){

    }
}
