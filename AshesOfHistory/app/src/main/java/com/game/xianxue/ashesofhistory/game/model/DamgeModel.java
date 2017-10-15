package com.game.xianxue.ashesofhistory.game.model;


import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.utils.RandomUtils;

import java.io.Serializable;

/**
 * 伤害模型
 */
public class DamgeModel {

    /**
     * 通过传入的参数，计算后，返回造成的伤害结果
     * @param damage     本次攻击的原始伤害
     * @param penetrateConstant  本次攻击者的穿透值
     * @param penetratePercent  本次攻击技能的穿透率
     * @param resist     本次被攻击者的抗性(物理伤害传入护甲，魔法伤害传入魔抗)
     * @param isFloat 是否计算伤害 浮动
     * @return
     */
    public static int getDamageResult(int damage,float penetrateConstant ,float penetratePercent,float resist,boolean isFloat) {
        int result = 0;                     // 最终伤害
        int  resistAfterPenetrate = 0;      // 计算穿甲之后的抗性

        // 计算穿甲之后的抗性
        resistAfterPenetrate = (int)((resist - penetrateConstant) * (1f - penetratePercent));
        if(resistAfterPenetrate< 0) resistAfterPenetrate = 0;

        // TODO: 2017/10/15  这里的伤害护甲计算公式不应该那么简单的相减，后期再来完善，可以使用对数函数来计算。
        result = damage - resistAfterPenetrate;

        if(isFloat){
            float floatRate = RandomUtils.getRandomNumberbetween(0.9f,1f);
            result = (int)(result*floatRate);
        }
        return result;
    }
}
