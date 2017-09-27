package com.game.xianxue.ashesofhistory.game.model.buff;

/**
 * buff 基础模型 数据从数据库读出来
 */
public class BuffBase {

    protected int buffId;                       // buffid
    protected String name;                      // buff名字
    protected String introduce;                 // buff说明
    protected int buff_effect;                  // buff效果，即影响什么属性
    protected int buff_nature;                  // buff的性质   (分为主动buff和被动buff)
    protected int buff_type;                    // buff类型    （分为攻击buff，辅助buff，恢复buff）
    protected int buff_constant;                // buff的固定部分
    protected float buff_fluctuate;             // buff的浮动部分
    protected int time;                         // buff持续时间
    protected int range;                        // buff的影响范围
    protected float level_up_constant;          // 每升一级 固定部分 的提升
    protected float level_up_fluctuate;         // 每升一级 浮动部分 的提升
    protected float level_up_range;             // 每升一级 作用范围 的提升
    protected float level_up_time;              // 每升一级 持续时间 的提升

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

    public float getLevel_up_constant() {
        return level_up_constant;
    }

    public void setLevel_up_constant(float level_up_constant) {
        this.level_up_constant = level_up_constant;
    }

    public float getLevel_up_fluctuate() {
        return level_up_fluctuate;
    }

    public void setLevel_up_fluctuate(float level_up_fluctuate) {
        this.level_up_fluctuate = level_up_fluctuate;
    }

    public float getLevel_up_range() {
        return level_up_range;
    }

    public void setLevel_up_range(float level_up_range) {
        this.level_up_range = level_up_range;
    }

    public float getLevel_up_time() {
        return level_up_time;
    }

    public void setLevel_up_time(float level_up_time) {
        this.level_up_time = level_up_time;
    }

    public int getBuff_effect() {
        return buff_effect;
    }

    public void setBuff_effect(int buff_effect) {
        this.buff_effect = buff_effect;
    }

    @Override
    public String toString() {
        return "BuffBase{" +
                "buffId=" + buffId +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", buff_effect=" + buff_effect +
                ", buff_nature=" + buff_nature +
                ", buff_type=" + buff_type +
                ", buff_constant=" + buff_constant +
                ", buff_fluctuate=" + buff_fluctuate +
                ", time=" + time +
                ", range=" + range +
                ", level_up_constant=" + level_up_constant +
                ", level_up_fluctuate=" + level_up_fluctuate +
                ", level_up_range=" + level_up_range +
                ", level_up_time=" + level_up_time +
                '}';
    }
}
