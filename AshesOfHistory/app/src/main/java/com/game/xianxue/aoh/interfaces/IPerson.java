package com.game.xianxue.aoh.interfaces;

/**
 * 技能的接口
 * Created by anxi.xue on 8/30/17.
 */

public interface IPerson {
    int PERSON_LEVEL_MAX = 15;        // 最高等级
    int PERSON_LEVEL_MINI = 1;        // 最低等级

    // 行动值相关
    int DEFAULT_ACTIVE_VALUES_MAX = 3000;   //最大行动值，当行动值到达最大，就可以发动进攻
}
