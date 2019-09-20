package com.game.xianxue.aoh.game.model.buff;

/**
 * buff 基础模型 数据从数据库读出来
 */
public class BuffBase {

    protected int buffId;                       // buffid
    protected String name;                      // buff名字
    protected String introduce;                 // buff说明
    protected int buff_nature;                  // buff的性质   (分为主动buff和被动buff)
    protected int superposition;                // buff是否叠加 (技能类型：0：只触发一次 1：时间内每回合触发一次)
    protected int time;                         // buff持续时间
    protected int range;                        // buff的影响范围
    protected int damage_type;                  // buff如果有伤害，则伤害类型为
    protected float level_up_range;             // 每升一级 作用范围 的提升
    protected float level_up_time;              // 每升一级 持续时间 的提升

    protected int[] buff_effect;                // buff效果，即影响什么属性
    protected float[] level_up_constant;        // 每升一级 固定部分 的提升
    protected float[] level_up_fluctuate;       // 每升一级 浮动部分 的提升
    protected float[] buff_constant;            // buff的固定部分
    protected float[] buff_fluctuate;           // buff的浮动部分

    // 以上5个数组的字符串，便于存储
    protected String sbuff_effect;              // 字符串:buff效果，即影响什么属性
    protected String slevel_up_constant;        // 字符串:每升一级 固定部分 的提升
    protected String slevel_up_fluctuate;       // 字符串:每升一级 浮动部分 的提升
    protected String sbuff_constant;            // 字符串:buff的固定部分
    protected String sbuff_fluctuate;           // 字符串:buff的浮动部分

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

    public int getSuperposition() {
        return superposition;
    }

    public void setSuperposition(int superposition) {
        this.superposition = superposition;
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

    public int[] getBuff_effect() {
        return buff_effect;
    }

    public void setBuff_effect(int[] buff_effect) {
        this.buff_effect = buff_effect;
    }

    public float[] getBuff_constant() {
        return buff_constant;
    }

    public void setBuff_constant(float[] buff_constant) {
        this.buff_constant = buff_constant;
    }

    public float[] getBuff_fluctuate() {
        return buff_fluctuate;
    }

    public void setBuff_fluctuate(float[] buff_fluctuate) {
        this.buff_fluctuate = buff_fluctuate;
    }

    public float[] getLevel_up_constant() {
        return level_up_constant;
    }

    public void setLevel_up_constant(float[] level_up_constant) {
        this.level_up_constant = level_up_constant;
    }

    public float[] getLevel_up_fluctuate() {
        return level_up_fluctuate;
    }

    public void setLevel_up_fluctuate(float[] level_up_fluctuate) {
        this.level_up_fluctuate = level_up_fluctuate;
    }

    public String getSbuff_effect() {
        return sbuff_effect;
    }

    public void setSbuff_effect(String sbuff_effect) {
        this.sbuff_effect = sbuff_effect;
    }

    public String getSlevel_up_constant() {
        return slevel_up_constant;
    }

    public void setSlevel_up_constant(String slevel_up_constant) {
        this.slevel_up_constant = slevel_up_constant;
    }

    public String getSlevel_up_fluctuate() {
        return slevel_up_fluctuate;
    }

    public void setSlevel_up_fluctuate(String slevel_up_fluctuate) {
        this.slevel_up_fluctuate = slevel_up_fluctuate;
    }

    public String getSbuff_constant() {
        return sbuff_constant;
    }

    public void setSbuff_constant(String sbuff_constant) {
        this.sbuff_constant = sbuff_constant;
    }

    public String getSbuff_fluctuate() {
        return sbuff_fluctuate;
    }

    public void setSbuff_fluctuate(String sbuff_fluctuate) {
        this.sbuff_fluctuate = sbuff_fluctuate;
    }


    public int getDamage_type() {
        return damage_type;
    }

    public void setDamage_type(int damage_type) {
        this.damage_type = damage_type;
    }

    @Override
    public String toString() {
        return "BuffBase{" +
                "buffId=" + buffId +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", buff_effect=" + sbuff_effect +
                ", buff_nature=" + buff_nature +
                ", superposition=" + superposition +
                ", buff_constant=" + sbuff_constant +
                ", buff_fluctuate=" + sbuff_fluctuate +
                ", time=" + time +
                ", range=" + range +
                ", damage_type=" + damage_type +
                ", level_up_constant=" + level_up_constant +
                ", level_up_fluctuate=" + level_up_fluctuate +
                ", level_up_range=" + level_up_range +
                ", level_up_time=" + level_up_time +
                '}';
    }
}
