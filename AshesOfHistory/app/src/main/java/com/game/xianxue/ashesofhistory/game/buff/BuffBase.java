package com.game.xianxue.ashesofhistory.game.buff;

import com.game.xianxue.ashesofhistory.interfaces.Interface_Buff;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;

/**
 * buff实体类 模型
 */
public class BuffBase implements Interface_Buff {
    public int buffId;            // buffid
    public String name;           // buff名字
    public String introduce;      // buff说明
    public int buff_nature;       // buff的性质   (分为主动buff和被动buff)
    public int buff_type;        // buff类型    （分为攻击buff，辅助buff，恢复buff）
    public int buff_constant;     // buff的固定部分
    public float buff_fluctuate;  // buff的浮动部分
    public int time;             // buff持续时间
    public int range;             // buff的影响范围
    public int level;            // buff的等级
    public float effect_up;       // 每升一级的提升

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getEffect_up() {
        return effect_up;
    }

    public void setEffect_up(float effect_up) {
        this.effect_up = effect_up;
    }

    @Override
    public String toString() {
        return "BuffBase{" +
                "buff_id=" + buffId +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", buff_nature=" + buff_nature +
                ", buff_type=" + buff_type +
                ", buff_constant=" + buff_constant +
                ", buff_fluctuate=" + buff_fluctuate +
                ", time=" + time +
                ", range=" + range +
                ", level=" + level +
                ", effect_up=" + effect_up +
                '}';
    }

    @Override
    public void start(BattlePerson person) {

    }

    @Override
    public void stop(BattlePerson remove) {

    }


}
