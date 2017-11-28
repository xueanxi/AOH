package com.game.xianxue.ashesofhistory.game.model.buff;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Buff;
import com.game.xianxue.ashesofhistory.utils.TextUtils;

import org.w3c.dom.Text;

/**
 * Buff 在战斗时的实体类
 */

public class BuffBattle extends BuffBase implements Interface_Buff {
    private static final String TAG = "BuffBattle";

    private int level = BUFF_LEVEL_LIMIT_MINI;      // buff的等级
    private int remainTime = 0;                     // buff的剩余时间
    private int duration = 0;                       // buff持续了多长时间,对于持续性buff来说，需要统计持续了多长时间

    public BuffBattle(BuffBase base) {
        this.buffId = base.buffId;                  // buffid
        this.name = base.name;                      // buff名字
        this.introduce = base.introduce;            // buff说明
        this.buff_nature = base.buff_nature;        // buff的性质   (0:被动buff 1:主动buff)
        this.buff_type = base.buff_type;            // buff类型     (0：只触发一次 1：时间内每回合触发一次)
        this.time = base.time;                      // buff持续时间
        this.range = base.range;                    // buff的影响范围
        this.damage_type = base.damage_type;        // buff如果有伤害，则伤害类型为
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

    public BuffBattle(BuffBase base, int level) {
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
     * 设置Buff级别的时候
     * <p>
     * 需要根据 buff当前的等级，分别计算以下四个值
     * 1.buff_constant ：根据level_up_constant来计算新的固定部分效果
     * 2.buff_fluctuate：根据level_up_fluctuate来计算新的浮动部分效果
     * 3.time          ：根据level_up_time来计算新的持续时间
     * 4.range         ：根据level_up_range来计算新的距离
     * <p>
     * level_up_constant level_up_fluctuate
     * 升级当前 buff 的能力  buff_constant、buff_fluctuate
     *
     * @param level
     */
    public void setLevel(int level) {
        if (level < BUFF_LEVEL_LIMIT_MINI) {
            level = BUFF_LEVEL_LIMIT_MINI;
        } else if (level > BUFF_LEVEL_LIMIT_MAX) {
            level = BUFF_LEVEL_LIMIT_MAX;
        }

        if (buff_effect.length == buff_constant.length
                && buff_effect.length == buff_fluctuate.length
                && buff_effect.length == level_up_constant.length
                && buff_effect.length == level_up_fluctuate.length) {
            this.level = level;
            for (int i = 0; i < buff_effect.length; i++) {
                buff_constant[i] = (float) (buff_constant[i] + (float) (level - 1) * level_up_constant[i]);
                buff_fluctuate[i] = (float) (buff_fluctuate[i] + (float) (level - 1) * level_up_fluctuate[i]);
            }
            time = (int) (time + time * (level - 1) * level_up_time);
            range = (int) (range + range * (level - 1) * level_up_range);
        } else {
            SimpleLog.loge(TAG, this.getName() + " buff setLevel():的时候出现错误。");
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

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public int getDuration() {
        return duration;
    }

    /**
     * @param duration
     */
    public void setDuration(int duration) {
        // 只有持续叠加型的buff才需要设置 持续时间
        if (this.buff_type != BUFF_TYPE_LAST) return;
        if (duration < 0) duration = 0;
        this.duration = duration;

        // 重置buff_constant buff_fluctuate 方便下面重新计算
        this.buff_constant = TextUtils.getFloatArrayFromString(sbuff_constant);
        this.buff_fluctuate = TextUtils.getFloatArrayFromString(sbuff_fluctuate);

        if (buff_effect.length == buff_constant.length
                && buff_effect.length == buff_fluctuate.length
                && buff_effect.length == level_up_constant.length
                && buff_effect.length == level_up_fluctuate.length) {

            for (int i = 0; i < buff_effect.length; i++) {
                buff_constant[i] = buff_constant[i] * (this.duration);
                buff_fluctuate[i] = buff_fluctuate[i] * (this.duration);
            }
        } else {
            SimpleLog.loge(TAG, this.getName() + " buff setDuration():的时候出现错误。");
        }

        SimpleLog.logd(TAG,this.getName()+ "持续时间为："+duration + "当前固定部分为:"+buff_constant[0]+" 浮动部分为:"+buff_fluctuate[0]);
    }

    @Override
    public String toString() {
        return "BuffBattle{" +
                "startLevel=" + level +
                "} " + super.toString();
    }
}
