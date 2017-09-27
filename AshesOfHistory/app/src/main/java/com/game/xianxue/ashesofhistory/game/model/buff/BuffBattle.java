package com.game.xianxue.ashesofhistory.game.model.buff;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.interfaces.Interface_Buff;

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
        this.buff_type = base.buff_type;            // buff类型    （分为攻击buff，辅助buff，恢复buff）
        this.buff_constant = base.buff_constant;    // buff的固定部分
        this.buff_fluctuate = base.buff_fluctuate;  // buff的浮动部分
        this.time = base.time;                      // buff持续时间
        this.range = base.range;                    // buff的影响范围
        this.level_up_constant = base.level_up_constant;        // 每升一级固定部分的提升
        this.level_up_fluctuate = base.level_up_fluctuate;      // 每升一级浮动部分的提升
        this.level_up_range = base.level_up_range;              // 每升一级浮动部分的提升
        this.level_up_time = base.level_up_time;                // 每升一级浮动部分的提升
    }

    public int getLevel() {
        return level;
    }

    /**
     * 设置Buff级别的时候，需要根据 level、 level_up_constant、 level_up_fluctuate 升级当前 buff 的能力  buff_constant、buff_fluctuate
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
        buff_constant = (int) (buff_constant + (level - 1) * level_up_constant);
        buff_fluctuate = (float) (buff_fluctuate + (float) (level - 1) * level_up_fluctuate);
        time = (int) (time + time * (level - 1) * level_up_time);
        range = (int) (range + range * (level - 1) * level_up_range);
    }

    @Override
    public void start(BasePerson person) {
        /*if(this.buff_effect == 0) {
            SimpleLog.loge(TAG,"Error!!! start() : buff_effect == 0");
            return;
        }
        // 小于等于 BUFF_LUCK 表示 为基础buff
        if (this.buff_effect <= BUFF_LUCK) {

        } else {

        }*/
    }

    @Override
    public void stop(BasePerson person) {

    }
}
