package com.game.xianxue.ashesofhistory.game.model.buff;

import com.game.xianxue.ashesofhistory.interfaces.Interface_Buff;
import com.game.xianxue.ashesofhistory.utils.TextUtils;

import org.w3c.dom.Text;

/**
 * Buff 在战斗时的实体类
 */

public class BuffBattle extends BuffBase implements Interface_Buff {
    private static final String TAG = "=BuffBattle";

    private int level = BUFF_LEVEL_LIMIT_MINI;      // buff的等级

    public BuffBattle(BuffBase base) {
        this.buffId = base.buffId;                  // buffid
        this.name = base.name;                      // buff名字
        this.introduce = base.introduce;            // buff说明
        this.buff_nature = base.buff_nature;        // buff的性质   (0:被动buff 1:主动buff)
        this.buff_type = base.buff_type;            // buff类型     (0：只触发一次 1：时间内每回合触发一次)
        this.time = base.time;                      // buff持续时间
        this.range = base.range;                    // buff的影响范围
        this.level_up_range = base.level_up_range;              // 每升一级浮动部分的提升
        this.level_up_time = base.level_up_time;                // 每升一级浮动部分的提升

        this.sbuff_effect = base.sbuff_effect;              // 字符串:buff的效果
        this.sbuff_constant = base.sbuff_constant;          // 字符串:buff的固定部分
        this.sbuff_fluctuate = base.sbuff_fluctuate;        // 字符串:buff的浮动部分
        this.slevel_up_constant = base.slevel_up_constant;  // 字符串:每升一级固定部分的提升
        this.slevel_up_fluctuate = base.slevel_up_fluctuate;// 字符串:每升一级浮动部分的提升

        // 把 String 转化为 int[] / float[]
        this.buff_effect = TextUtils.getIntArrayFromString(sbuff_effect);
        this.buff_constant = TextUtils.getFloatArrayFromString(sbuff_constant);
        this.buff_fluctuate = TextUtils.getFloatArrayFromString(sbuff_fluctuate);
        this.level_up_constant = TextUtils.getFloatArrayFromString(slevel_up_constant);
        this.level_up_fluctuate = TextUtils.getFloatArrayFromString(slevel_up_fluctuate);
    }

    public BuffBattle(BuffBase base,int level) {
        this.buffId = base.buffId;                  // buffid
        this.name = base.name;                      // buff名字
        this.introduce = base.introduce;            // buff说明
        this.buff_nature = base.buff_nature;        // buff的性质   (0:被动buff 1:主动buff)
        this.buff_type = base.buff_type;            // buff类型    （分为攻击buff，辅助buff，恢复buff）
        this.time = base.time;                      // buff持续时间
        this.range = base.range;                    // buff的影响范围
        this.level_up_range = base.level_up_range;              // 每升一级浮动部分的提升
        this.level_up_time = base.level_up_time;                // 每升一级浮动部分的提升

        this.sbuff_effect = base.sbuff_effect;        // buff的效果
        this.sbuff_constant = base.sbuff_constant;    // buff的固定部分
        this.sbuff_fluctuate = base.sbuff_fluctuate;  // buff的浮动部分
        this.slevel_up_constant = base.slevel_up_constant;        // 每升一级固定部分的提升
        this.slevel_up_fluctuate = base.slevel_up_fluctuate;      // 每升一级浮动部分的提升

        // 把 String 转化为 int[] / float[]
        this.buff_effect = TextUtils.getIntArrayFromString(sbuff_effect);
        this.buff_constant = TextUtils.getFloatArrayFromString(sbuff_constant);
        this.buff_fluctuate = TextUtils.getFloatArrayFromString(sbuff_fluctuate);
        this.level_up_constant = TextUtils.getFloatArrayFromString(slevel_up_constant);
        this.level_up_fluctuate = TextUtils.getFloatArrayFromString(slevel_up_fluctuate);

        setLevel(level);
    }

    public int getLevel() {
        return level;
    }

    /**
     * 设置Buff级别的时候，需要根据 startLevel、 level_up_constant、 level_up_fluctuate 升级当前 buff 的能力  buff_constant、buff_fluctuate
     *
     * @param level
     */
    public void setLevel(int level) {
        if (level < BUFF_LEVEL_LIMIT_MINI) {
            level = BUFF_LEVEL_LIMIT_MINI;
        } else if (level > BUFF_LEVEL_LIMIT_MAX) {
            level = BUFF_LEVEL_LIMIT_MAX;
        }

        if(buff_effect.length == buff_constant.length
                && buff_effect.length == buff_fluctuate.length
                && buff_effect.length == level_up_constant.length
                && buff_effect.length == level_up_fluctuate.length){
            this.level = level;
            for(int i = 0;i<buff_effect.length;i++){
                buff_constant[i] = (float) (buff_constant[i] + (float)(level - 1) * level_up_constant[i]);
                buff_fluctuate[i] = (float) (buff_fluctuate[i] + (float) (level - 1) * level_up_fluctuate[i]);
            }
            time = (int) (time + time * (level - 1) * level_up_time);
            range = (int) (range + range * (level - 1) * level_up_range);
        }
    }

    /**
     * 这个buff的效果是否是影响基础属性的
     *
     * @return
     */
    public static boolean isBisisBuff(int effect) {
        if (effect >= BUFF_STRENGTH && effect <= BUFF_LUCK) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "BuffBattle{" +
                "startLevel=" + level +
                "} " + super.toString();
    }
}
