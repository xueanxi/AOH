package com.game.xianxue.ashesofhistory.model;


import com.game.xianxue.ashesofhistory.utils.RandomUtils;

/**
 * 伤害计算模型
 */
public class DamgeModel {

    /**
     * 传入攻击力，抗性，穿透。
     * 返回造成的伤害
     *
     * @param PhysicDamage
     * @param Armor
     * @param Penetrate
     */
    public static int attack(float PhysicDamage, float Armor, float Penetrate) {
        Armor -= Penetrate;
        float result = PhysicDamage * (1f - (Armor / (Armor + 200f)));
        result = result * RandomUtils.getRandombetween(0.95f, 1.05f);// 造成的伤害随机波动
        return ((int) (Math.ceil(result)));
    }

    /**
     * 传入攻击力，抗性，百分比穿透。
     * 返回造成的伤害
     *
     * @param PhysicDamage
     * @param Armor
     * @param Penetrate
     */
    public static int attackWithPercent(float PhysicDamage, float Armor, float Penetrate) {
        Armor = Armor * (1f - Penetrate);
        float result = PhysicDamage * (1f - (Armor / (Armor + 200f)));
        result = result * RandomUtils.getRandombetween(0.95f, 1.05f);// 造成的伤害随机波动
        return ((int) (Math.ceil(result)));
    }

    /**
     * 传入命中值，和格档值
     * 返回 格档失败的概率，也就是进攻方，攻击成功的概率
     *
     * @param Accuracy
     * @param Block
     */
    public static float block(float Accuracy, float Block) {
        return Accuracy / (Accuracy + Block);
    }

    /**
     * 传入命中值，和躲闪值
     * 返回 躲闪失败的概率，也就是进攻方，攻击成功的概率
     *
     * @param Accuracy
     * @param Dodge
     */
    public static float dodge(float Accuracy, float Dodge) {
        return Accuracy / (Accuracy + Dodge);
    }
}
