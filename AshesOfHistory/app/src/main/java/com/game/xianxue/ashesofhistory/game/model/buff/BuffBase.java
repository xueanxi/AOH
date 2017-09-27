package com.game.xianxue.ashesofhistory.game.model.buff;

import com.game.xianxue.ashesofhistory.interfaces.Interface_Buff;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;

/**
 * buff实体类 模型
 */
public class BuffBase  {
    protected int buffId;                       // buffid
    protected String name;                      // buff名字
    protected String introduce;                 // buff说明
    protected int buff_nature;                  // buff的性质   (分为主动buff和被动buff)
    protected int buff_type;                    // buff类型    （分为攻击buff，辅助buff，恢复buff）
    protected int buff_constant;                // buff的固定部分
    protected float buff_fluctuate;             // buff的浮动部分
    protected int time;                         // buff持续时间
    protected int range;                        // buff的影响范围
    protected float effect_constant_up;         // 每升一级 固定部分 的提升
    protected float effect_fluctuate_up;        // 每升一级 浮动部分 的提升

    public BuffBase() {
    }

    public int getBuffId() {
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getBuff_nature() {
        return buff_nature;
    }

    public void setBuff_nature(int buff_nature) {
        this.buff_nature = buff_nature;
    }

    public int getBuff_type() {
        return buff_type;
    }

    public void setBuff_type(int buff_type) {
        this.buff_type = buff_type;
    }

    public int getBuff_constant() {
        return buff_constant;
    }

    public void setBuff_constant(int buff_constant) {
        this.buff_constant = buff_constant;
    }

    public float getBuff_fluctuate() {
        return buff_fluctuate;
    }

    public void setBuff_fluctuate(float buff_fluctuate) {
        this.buff_fluctuate = buff_fluctuate;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public float getEffect_constant_up() {
        return effect_constant_up;
    }

    public void setEffect_constant_up(float effect_constant_up) {
        this.effect_constant_up = effect_constant_up;
    }

    public float getEffect_fluctuate_up() {
        return effect_fluctuate_up;
    }

    public void setEffect_fluctuate_up(float effect_fluctuate_up) {
        this.effect_fluctuate_up = effect_fluctuate_up;
    }

    @Override
    public String toString() {
        return "BuffBase{" +
                "buffId=" + buffId +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", buff_nature=" + buff_nature +
                ", buff_type=" + buff_type +
                ", buff_constant=" + buff_constant +
                ", buff_fluctuate=" + buff_fluctuate +
                ", time=" + time +
                ", range=" + range +
                ", effect_constant_up=" + effect_constant_up +
                ", effect_fluctuate_up=" + effect_fluctuate_up +
                '}';
    }
}
