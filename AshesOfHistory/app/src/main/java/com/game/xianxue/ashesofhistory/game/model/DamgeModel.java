package com.game.xianxue.ashesofhistory.game.model;


import com.game.xianxue.ashesofhistory.Log.BattleLog;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.utils.RandomUtils;

import java.io.Serializable;

import com.game.xianxue.ashesofhistory.interfaces.Interface_Skill;


/**
 * 伤害模型
 */
public class DamgeModel implements Interface_Skill {

    /**
     * 通过传入的参数，计算后，返回造成的伤害结果
     *
     * @param damage            本次攻击的原始伤害
     * @param penetrateConstant 本次攻击者的穿透值
     * @param penetratePercent  本次攻击技能的穿透率
     * @param resist            本次被攻击者的抗性(物理伤害传入护甲，魔法伤害传入魔抗)
     * @param isFloat           是否计算伤害 浮动
     * @return
     */
    public static int getDamageResult(int damage, float penetrateConstant, float penetratePercent, float resist, boolean isFloat) {
        int result = 0;                    // 最终伤害
        int resistAfterPenetrate = 0;      // 计算穿甲之后的抗性
        // 计算穿甲之后的抗性
        resistAfterPenetrate = (int) ((resist - penetrateConstant) * (1f - penetratePercent));
        if (resistAfterPenetrate < 0) resistAfterPenetrate = 0;

        BattleLog.log("伤害："+damage + "护甲:"+resist + " 人物穿透："+penetrateConstant+" 技能穿透:"+penetratePercent+" ");

        // TODO: 2017/10/15  这里的伤害护甲计算公式不应该那么简单的相减，后期再来完善，可以使用对数函数来计算。
        result = damage - resistAfterPenetrate;
        BattleLog.log("结果： 最终伤害为"+result);

        if (isFloat) {
            float floatRate = RandomUtils.getRandomNumberbetween(0.9f, 1.1f);
            result = (int) (result * floatRate);
        }
        return result;
    }

    /**
     * 获得真实伤害的结果
     *
     * @param damage
     * @return
     */
    public static int getRealDamageResult(int damage) {
        int result = damage;
        return result;
    }

    /**
     * 计算当前生命百分比伤害
     * @param type                  技能伤害类型
     * @param damagePercent         技能伤害百分比
     * @param penetrateConstant     进攻者的固定穿甲
     * @param HP_Current                    被攻击者剩余的HP
     * @param HP_MAX                 被攻击者的生命上限
     * @param resist                被攻击者的抗性
     * @return
     */
    public static int getPercentDamageResult(int type, float damagePercent, float penetrateConstant, float penetratePercent
            , int HP_Current,int HP_MAX,int resist) {

        int damage = 0;
        int result = 0;
        int resistAfterPenetrate = 0;      // 计算穿甲之后的抗性

        switch (type) {
            case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT:
                // 技能伤害为当前生命百分比物理伤害
                damage = (int)(HP_Current * damagePercent);

                // 计算穿甲之后的抗性
                resistAfterPenetrate = (int) ((resist - penetrateConstant) * (1f - penetratePercent));
                if (resistAfterPenetrate < 0) resistAfterPenetrate = 0;

                // TODO: 2017/10/15  这里的伤害护甲计算公式不应该那么简单的相减，后期再来完善，可以使用对数函数来计算。
                result = damage - resistAfterPenetrate;

                break;
            case SKILL_DAMAGE_TYPE_MAGIC_PERCENT:
                // 技能伤害为当前生命百分比魔法伤害
                damage = (int)(HP_Current * damagePercent);
                // 计算穿甲之后的抗性
                resistAfterPenetrate = (int) ((resist - penetrateConstant) * (1f - penetratePercent));
                if (resistAfterPenetrate < 0) resistAfterPenetrate = 0;

                // TODO: 2017/10/15  这里的伤害护甲计算公式不应该那么简单的相减，后期再来完善，可以使用对数函数来计算。
                result = damage - resistAfterPenetrate;
                break;
            case SKILL_DAMAGE_TYPE_REAL_PERCENT:
                // 技能伤害为当前生命百分比真实伤害
                result = (int)(HP_Current * damagePercent);
                break;

            case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT_MAX:
                // 技能伤害为最大生命百分比物理伤害
                damage = (int)(HP_MAX * damagePercent);

                // 计算穿甲之后的抗性
                resistAfterPenetrate = (int) ((resist - penetrateConstant) * (1f - penetratePercent));
                if (resistAfterPenetrate < 0) resistAfterPenetrate = 0;
                // TODO: 2017/10/15  这里的伤害护甲计算公式不应该那么简单的相减，后期再来完善，可以使用对数函数来计算。
                result = damage - resistAfterPenetrate;
                break;
            case SKILL_DAMAGE_TYPE_MAGIC_PERCENT_MAX:
                // 技能伤害为最大生命百分比魔法伤害

                damage = (int)(HP_MAX * damagePercent);

                // 计算穿甲之后的抗性
                resistAfterPenetrate = (int) ((resist - penetrateConstant) * (1f - penetratePercent));
                if (resistAfterPenetrate < 0) resistAfterPenetrate = 0;
                // TODO: 2017/10/15  这里的伤害护甲计算公式不应该那么简单的相减，后期再来完善，可以使用对数函数来计算。
                result = damage - resistAfterPenetrate;
                break;
            case SKILL_DAMAGE_TYPE_REAL_PERCENT_MAX:
                // 技能伤害为当前生命百分比真实伤害
                result = (int)(HP_MAX * damagePercent);
                break;
        }

        return result;
    }

    private void aaa(){

    }
}
