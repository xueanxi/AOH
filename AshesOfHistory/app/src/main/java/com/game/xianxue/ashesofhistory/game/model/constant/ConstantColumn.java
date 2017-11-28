package com.game.xianxue.ashesofhistory.game.model.constant;

/**
 * 列属性
 * Created by anxi.xue on 8/29/17.
 */

public class ConstantColumn {

    /**
     * 所有人物的最基本的数据,各种人物数据都是基于此父类来实现的
     */
    public static class BasePersonColumn {
        public static final String tableName = "basePerson";
        public static final String id = "psersonId";                                // 数据库自动生成的Id
        public static final String personId = "personId";                     // xml中定义这个人物的id
        public static final String name = "name";                             // 中文名
        public static final String name2 = "name2";                           // 拼音名
        public static final String sexuality = "sexuality";                   // 性别
        public static final String aptitude = "aptitude";                     // 资质
        public static final String strength_Raw = "strength_raw";             // 原始力量
        public static final String intellect_Raw = "intellect_raw";           // 原始智力
        public static final String dexterity_Raw = "dexterity_raw";           // 原始敏捷
        public static final String physique_Raw = "physique_raw";             // 原始体制
        public static final String spirit_Raw = "spirit_raw";                 // 原始的精神
        public static final String luck_Raw = "luck_raw";                     // 原始的气运
        public static final String fascination_Raw = "fascination_raw";       // 原始的魅力
        public static final String skill_lists_Raw = "skill_lists_raw";       // 天赋技能字符串(隨着等級提高能学习到的)
        public static final String lead_buff_id = "lead_buff_id";             // 统帅光环 0:表示没有, 其他数字：表示buff的索引
    }

    /**
     * 玩家已经拥有的人物，相比 {@link BasePersonColumn} 多了以下属性
     */
    public static class OwnPersonColumn extends BasePersonColumn {
        //用户已经拥有的武将，对比上面的表格，会额外多一些属性
        public static final String tableName = "ownPerson";

        public static final String level = "startLevel";
        public static final String weaponId = "weaponId";       // 武器id
        public static final String equipId = "equipId";         // 装备id
        public static final String treasureId = "treasureId";   // 宝物id
        public static final String riderId = "riderId";         // 坐骑id
        public static final String experience = "experience";   // 拥有经验
        public static final String skill_lists = "skill_lists"; // 当前拥有的技能
    }

    /**
     *  技能数据库表格的 列属性
     */
    public static class SkillColumn extends BasePersonColumn {
        public static final String tableName = "skills";
        public static final String id = "_id";                      // 数据库标识这个技能的id
        public static final String skillId = "skill_id";            // 唯一标识这个技能的id
        public static final String name = "name";                   // 技能名字
        public static final String introduce = "introduce";         // 技能介绍
        public static final String naturetype = "skill_nature";     // 技能的性质（主动，被动）
        public static final String skillType = "skill_type";        // 技能类型(攻击，恢复，辅助)
        public static final String accuracyRate = "accuracy_rate";  // 命中率
        public static final String criteRate = "crite_rate";        // 暴击率
        public static final String effectRate = "effect_rate";      // 效果触发几率
        public static final String cdTime = "cd_time";              // 冷却时间
        public static final String range = "range";                 // 攻击范围
        public static final String effectNumber = "effect_number";  // 作用人数
        public static final String effectCamp = "effect_camp";      // 作用阵营
        public static final String effectTarget = "effect_target";  // 作用目标
        public static final String damageType = "damage_type";      // 伤害类型(6种)
        public static final String damageConstant = "damage_constant"; // 技能固定伤害
        public static final String damageFluctuate = "damage_fluctuate"; // 技能波动伤害
        public static final String damagePenetrate = "damage_penetrate"; // 伤害穿透率
        public static final String assisteffect = "assist_effect";  //辅助效果
        public static final String attackTime = "attack_time";                  //技能攻击次数
        public static final String attackTimeDamageUp = "attack_time_damage_up"; //多次攻击的技能 每一击之后下一击 伤害提升
        public static final String levelUpConstant = "level_up_constant";       //技能固定伤害部分升级提升
        public static final String levelUpFluctuate = "level_up_fluctuate";     //技能浮动伤害部分升级提升
        public static final String levelUpRange = "level_up_range";             //技能作用范围升级提升
        public static final String levelUpNumber = "level_up_number";           //技能作用人数升级提升
        public static final String levelUpEffectRate = "level_up_effect_rate";  //技能辅助效果触发几率升级提升
        public static final String levelUpPenetrate = "level_up_penetrate";     //技能穿透升级提升
        public static final String levelUpCDTime = "level_up_cd_time";          //技能冷却升级提升
        public static final String levelUpAttackTime = "level_up_attack_time";          //技能冷却升级提升
    }

    /**
     *  Buff 数据库表格的 列属性
     */
    public static class BuffColumn{
        public static final String tableName = "buff";
        public static final String id = "_id";                          // 数据库id
        public static final String buff_id = "buff_id";                 // 唯一标识这个技能的id
        public static final String name = "name";                       // 名字
        public static final String introduce = "introduce";             // buff介绍
        public static final String buff_effect = "buff_effect";         // buff效果，即影响什么属性
        public static final String buff_type = "buff_type";             // buff的类型（攻击，恢复，辅助）
        public static final String buff_nature = "buff_nature";         // buff的性质（主动，被动）
        public static final String buff_constant = "buff_constant";     // buff固定影响
        public static final String buff_fluctuate = "buff_fluctuate";   // buff的浮动影响
        public static final String time = "time";                       // buff的持续时间
        public static final String range = "range";                     // buff的范围
        public static final String damage_type = "damage_type";         // buff如果有伤害，则伤害类型为
        public static final String level_up_constant = "level_up_constant";          // 每升一级 固定部分 提高的百分比
        public static final String level_up_fluctuate = "level_up_fluctuate";        // 每升一级 浮动部分 提高的百分比
        public static final String level_up_range = "level_up_range";                // 每升一级 作用范围提高
        public static final String level_up_time = "level_up_time";                  // 每升一级 作用时间提高
    }


    /**
     *  阵型 数据库表格的 列属性
     */
    public static class LineUpColumn{
        public static final String tableName = "lineup";
        public static final String id = "_id";                          // 数据库id
        public static final String lineup_id = "lineup_id";             // 唯一标识这个阵型的id
        public static final String name = "name";                       // 名字
        public static final String introduce = "introduce";             // 介绍
        public static final String max_person = "max_person";           // 介绍
        public static final String lineup_json = "lineup_json";         // 容纳最大人数
        public static final String unit = "unit";                       // 阵型中的每一个单位，这个不存在数据库中，只是当成常量来使用
    }
}
