package com.game.xianxue.ashesofhistory.model.constant;

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
        public static final String strength_Raw = "strength_Raw";             // 原始力量
        public static final String intellect_Raw = "intellect_Raw";           // 原始智力
        public static final String dexterity_Raw = "dexterity_Raw";           // 原始敏捷
        public static final String physique_Raw = "physique_Raw";             // 原始体制
        public static final String spirit_Raw = "spirit_Raw";                 // 原始的精神
        public static final String luck_Raw = "luck_Raw";                     // 原始的气运
        public static final String fascination_Raw = "fascination_Raw";       // 原始的魅力
        public static final String skill_lists_Raw = "skill_lists_Raw";       // 天赋技能字符串(隨着等級提高能学习到的)
    }

    /**
     * 玩家已经拥有的人物，相比 {@link BasePersonColumn} 多了以下属性
     */
    public static class OwnPersonColumn extends BasePersonColumn {
        //用户已经拥有的武将，对比上面的表格，会额外多一些属性
        public static final String tableName = "ownPerson";

        public static final String personId = "personId";
        public static final String level = "level";
        public static final String weaponId = "weaponId";       // 武器id
        public static final String equipId = "equipId";         // 装备id
        public static final String treasureId = "treasureId";   // 宝物id
        public static final String riderId = "riderId";         // 坐骑id
        public static final String experience = "experience";   // 拥有经验
        public static final String skill_lists = "skill_lists"; // 当前拥有的技能
    }
}
