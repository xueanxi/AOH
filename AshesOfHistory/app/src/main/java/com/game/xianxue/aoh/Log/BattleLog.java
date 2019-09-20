package com.game.xianxue.aoh.Log;

import android.util.Log;

/**
 * Created by user on 5/24/17.
 */
public class BattleLog {
    public static final String TAG = "BattleLog";
    private static boolean showSystemLog = false;
    private static boolean showActionValuesLog = false;  //显示行动值日志
    private static boolean showBuffLog = true;          //显示buff日志
    private static boolean showNormalAttackLog = true;  //显示普通攻击日志
    private static boolean showSkillAttackLog = true;   //显示技能攻击日志
    private static boolean showRecoverHPLog = false;     //显示血量回复日志日志
    private static boolean showStatusLog = false;     //显示状态日志
    private static boolean showProgress = true;     //显示战斗进度日志
    private static boolean showInjured = true;     //显示受伤日志
    private static boolean showMiss = true;     //显示躲闪日志
    private static boolean showCrite = true;     //显示暴击日志
    private static boolean showSkillEffect = true;     //显示暴击日志
    private static boolean showLeader = true;     //显示统帅日志
    private static boolean showLineup = true;     //显示阵型

    public static final int TAG_AV = 1;          //行动值标签
    public static final int TAG_BF = 1 << 1;       //buff标签
    public static final int TAG_NA = 1 << 2;       //普通攻击标签
    public static final int TAG_SA = 1 << 3;       //技能攻击标签
    public static final int TAG_RH = 1 << 4;       //血量回复标签
    public static final int TAG_ST = 1 << 5;       //状态标签
    public static final int TAG_PG = 1 << 6;       //战斗进度标签
    public static final int TAG_IJ = 1 << 7;       //受伤标签
    public static final int TAG_MS = 1 << 8;       //miss标签
    public static final int TAG_CR = 1 << 9;       //暴击标签
    public static final int TAG_SE = 1 << 10;       //技能特效标签
    public static final int TAG_LD = 1 << 11;       //统帅标签
    public static final int TAG_LU = 1 << 12;       //阵型标签

    // 组合Log
    public static final int TAG_A = TAG_NA | TAG_SA;//攻击标签

    private static int logMask;

    static {
        logMask = 0;
        if (showActionValuesLog) logMask |= TAG_AV;
        if (showBuffLog) logMask |= TAG_BF;
        if (showNormalAttackLog) logMask |= TAG_NA;
        if (showSkillAttackLog) logMask |= TAG_SA;
        if (showRecoverHPLog) logMask |= TAG_RH;
        if (showStatusLog) logMask |= TAG_ST;
        if (showProgress) logMask |= TAG_PG;
        if (showInjured) logMask |= TAG_IJ;
        if (showMiss) logMask |= TAG_MS;
        if (showCrite) logMask |= TAG_CR;
        if (showSkillEffect) logMask |= TAG_SE;
        if (showLeader) logMask |= TAG_LD;
        if (showLineup) logMask |= TAG_LU;
    }

    public static void log(int tag, String content) {
        if ((tag & logMask) > 0) {
            if (showSystemLog) {
                System.out.println(content);
            } else {
                Log.d(TAG, content);
            }
        }
    }
}
