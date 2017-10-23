package com.game.xianxue.ashesofhistory.game.model;


import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.game.skill.SkillBattle;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Skill;
import com.game.xianxue.ashesofhistory.utils.RandomUtils;


/**
 * 伤害模型
 */
public class DamgeModel implements Interface_Skill {
    private static final String TAG = "DamgeModel";

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

        result = (int) (damage * getResistResult(resistAfterPenetrate));

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
     *
     * @param type              技能伤害类型
     * @param damagePercent     技能伤害百分比
     * @param penetrateConstant 进攻者的固定穿甲
     * @param HP_Current        被攻击者剩余的HP
     * @param HP_MAX            被攻击者的生命上限
     * @param resist            被攻击者的抗性
     * @return
     */
    public static int getPercentDamageResult(int type, float damagePercent, float penetrateConstant, float penetratePercent
            , int HP_Current, int HP_MAX, int resist) {

        int damage = 0;
        int result = 0;
        int resistAfterPenetrate = 0;      // 计算穿甲之后的抗性

        switch (type) {

            case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT:
                // 技能伤害为当前生命百分比物理伤害
                damage = (int) (HP_Current * damagePercent);

                // 计算穿甲之后的抗性
                resistAfterPenetrate = (int) ((resist - penetrateConstant) * (1f - penetratePercent));
                if (resistAfterPenetrate < 0) resistAfterPenetrate = 0;
                result = (int) (damage * getResistResult(resistAfterPenetrate));
                break;
            case SKILL_DAMAGE_TYPE_MAGIC_PERCENT:
                // 技能伤害为当前生命百分比魔法伤害
                damage = (int) (HP_Current * damagePercent);
                // 计算穿甲之后的抗性
                resistAfterPenetrate = (int) ((resist - penetrateConstant) * (1f - penetratePercent));
                if (resistAfterPenetrate < 0) resistAfterPenetrate = 0;
                result = (int) (damage * getResistResult(resistAfterPenetrate));
                break;
            case SKILL_DAMAGE_TYPE_REAL_PERCENT:
                // 技能伤害为当前生命百分比真实伤害
                result = (int) (HP_Current * damagePercent);
                break;

            case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT_MAX:
                // 技能伤害为最大生命百分比物理伤害
                damage = (int) (HP_MAX * damagePercent);

                // 计算穿甲之后的抗性
                resistAfterPenetrate = (int) ((resist - penetrateConstant) * (1f - penetratePercent));
                if (resistAfterPenetrate < 0) resistAfterPenetrate = 0;
                result = (int) (damage * getResistResult(resistAfterPenetrate));
                break;
            case SKILL_DAMAGE_TYPE_MAGIC_PERCENT_MAX:
                // 技能伤害为最大生命百分比魔法伤害

                damage = (int) (HP_MAX * damagePercent);

                // 计算穿甲之后的抗性
                resistAfterPenetrate = (int) ((resist - penetrateConstant) * (1f - penetratePercent));
                if (resistAfterPenetrate < 0) resistAfterPenetrate = 0;
                result = (int) (damage * getResistResult(resistAfterPenetrate));
                break;
            case SKILL_DAMAGE_TYPE_REAL_PERCENT_MAX:
                // 技能伤害为当前生命百分比真实伤害
                result = (int) (HP_MAX * damagePercent);
                break;
        }

        return result;
    }

    /**
     * 此方法的作用：参数传入抗性，返回承受伤害的系数 1：为承受全部伤害 0.5：是承受一半伤害 以此类推
     * <p>
     * 此方法的实现方案：
     * 分成两条直线，当 x_mix < x < x_mid 的时候，斜率比较陡，随着x变化，承受伤害系数比较大
     * 分成两条直线，当 x_mid < x < x_max 的时候，斜率比较缓，随着x变化，承受伤害系数比较小
     * 这么做的实际意义是，当抗性比较小的时候，提升抗性可以有效提高伤害承受能力，当抗性比较高时，提高抗性的收益就比较小了
     * <p>
     * 如果想修改抗性和伤害承受的关系，可以修改 x[] 和 y[] 里面的值，利用两个点确定一条直线的方式，来改变这个系数
     *
     * @param resist
     * @return
     */
    public static float getResistResult(float resist) {
        // TODO: 10/17/17 这个抗性和承受伤害的关系是需要根据需要修改的，现在开发阶段，就暂时这样，以后根据需求进行修改
        float x[] = {0, 400, 1000};       // x = 0为起点 ，x = 1000 为终点 ，x = 400 为转折点，这个点是可以调整的
        float y[] = {1f, 0.3f, 0.05f};    // 与x对应的函数值

        float x_min = x[0];
        float x_mid = x[1];
        float x_max = x[2];

        float y_min = y[0];
        float y_mid = y[1];
        float y_max = y[2];

        float result = 0;       // 最终结果
        float k = 1;            // 直线的斜率
        if (resist > x_min && resist <= x_mid) {
            k = (y_mid - y_min) / (x_mid - x_min);
            result = k * resist + 1f;
        } else if (resist > x_mid && resist <= x_max) {
            k = (y_max - y_mid) / (x_max - x_mid);
            float b = y_max - k * x_max;
            result = k * resist + b;
        } else if (resist <= x_min) {
            result = y_min;
        } else if (resist > x_max) {
            result = y_max;
        }
        return result;
    }

    public static int getDamgeInBuff(SkillBattle skill,BattlePerson actionPerson,BattlePerson beAttackPerson){
        int damage = 0;
        int damageType = skill.getDamageType();

        switch (damageType) {
            case SKILL_DAMAGE_TYPE_PHYSICS:
            case SKILL_DAMAGE_TYPE_MAGIC:
            case SKILL_DAMAGE_TYPE_REAL:
                damage = getNormalDamageInBuff(skill,actionPerson);
                break;
            case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT:
            case SKILL_DAMAGE_TYPE_MAGIC_PERCENT:
            case SKILL_DAMAGE_TYPE_REAL_PERCENT:
            case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT_MAX:
            case SKILL_DAMAGE_TYPE_MAGIC_PERCENT_MAX:
            case SKILL_DAMAGE_TYPE_REAL_PERCENT_MAX:
                damage = getPercentDamageInBuff(skill,beAttackPerson);
                break;
            default:
                SimpleLog.loge(TAG,"Error ！！！ getDamgeInBuff(): 进入了default分支 ");
                break;
        }

        return damage;
    }

    /**
     * 获得非百分比伤害技能的 最终伤害，这类型的技能伤害和释放技能的人的攻击力相关。
     * 注意：buff的伤害是无视被攻击者的抗性的
     */
    public static int getNormalDamageInBuff(SkillBattle skill,BattlePerson person) {
        int damage = 0;
        int damageType = skill.getDamageType();

        switch (damageType) {
            case SKILL_DAMAGE_TYPE_PHYSICS:
                damage = (int) (skill.getDamageConstant() + person.getPhysicDamage() * skill.getDamageFluctuate());
                break;
            case SKILL_DAMAGE_TYPE_MAGIC:
                damage = (int) (skill.getDamageConstant() + person.getMagicDamage() * skill.getDamageFluctuate());
                break;
            case SKILL_DAMAGE_TYPE_REAL:
                damage = (int) (skill.getDamageConstant() + person.getRealDamage() * skill.getDamageFluctuate());
                break;
            default:
                SimpleLog.loge(TAG,"Error ！！！ getNormalDamageInBuff(): 进入了default分支 ");
                break;
        }

        return damage;
    }

    /**
     * 获得 百分比伤害 技能的 最终伤害，这类型的技能伤害和承受此技能的人生命相关。
     * 注意：buff的伤害是无视被攻击者的抗性的
     */
    public static int getPercentDamageInBuff(SkillBattle skill,BattlePerson beAttackPerson) {
        int damage = 0;
        int damageType = skill.getDamageType();
        switch (damageType) {
            case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT:
            case SKILL_DAMAGE_TYPE_MAGIC_PERCENT:
            case SKILL_DAMAGE_TYPE_REAL_PERCENT:
                damage = (int) (skill.getDamageConstant() * beAttackPerson.getHP_Current());
                break;
            case SKILL_DAMAGE_TYPE_PHYSICS_PERCENT_MAX:
            case SKILL_DAMAGE_TYPE_MAGIC_PERCENT_MAX:
            case SKILL_DAMAGE_TYPE_REAL_PERCENT_MAX:
                damage = (int) (skill.getDamageConstant() * beAttackPerson.getHP_MAX());
                break;
            default:
                SimpleLog.loge(TAG,"Error ！！！ getPercentDamageInBuff(): 进入了default分支 ");
                break;
        }
        return damage;
    }
}
