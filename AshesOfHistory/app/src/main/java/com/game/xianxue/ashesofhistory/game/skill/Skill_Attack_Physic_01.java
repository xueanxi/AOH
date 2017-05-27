package com.game.xianxue.ashesofhistory.game.skill;

import com.game.xianxue.ashesofhistory.model.DamgeModel;
import com.game.xianxue.ashesofhistory.model.PlayerModel;
import com.game.xianxue.ashesofhistory.model.SkillModel;
import com.game.xianxue.ashesofhistory.model.TeamModel;

/**
 * 普通攻击
 */
public class Skill_Attack_Physic_01 extends SkillModel {


    public Skill_Attack_Physic_01() {
        this.id = 0;
        skillType = SKILL_TYPE_DAMGE;
        naturetype = SKILL_NATURE_INITIATIVE;
        accuracyRate = -1f;
        triggerProbability = -1f;
        introduce = "进行一次普通攻击";
        name = "普通攻击";
        isLocked = false;
        setLevel(level);
        damgeModel = new MyDamgeMode();
    }

    /**
     * 技能伤害模型
     */
    class MyDamgeMode extends DamgeModel {

        MyDamgeMode() {
            damage1 = 0;                            // 技能伤害固定部分
            damage2 = 1.0f;                         // 技能伤害随武将浮动部分,一般是一个百分比浮点数
            damageType = DAMGE_TYPE_PHYSICS;         // 伤害类型
            time = 1;                               // 伤害持续回合，普通技能一般都是一个回合，除了那些持续类型的伤害
        }
    }

    @Override
    public void setLevel(int level) {
        if (this.level > SKILL_LIMIT_MAX_LEVEL) this.level = 5;
        if (this.level < SKILL_LIMIT_MINI_LEVEL) this.level = 1;
        damgeModel.setLevel(level);
    }
}
