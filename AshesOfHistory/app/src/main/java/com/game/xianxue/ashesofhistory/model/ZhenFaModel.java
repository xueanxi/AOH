package com.game.xianxue.ashesofhistory.model;

/**
 * 阵法模型
 */
public class ZhenFaModel {
    final static String TAG = "ZhenFaModel";

    // 阵法类型，不同类型的阵法站位不同，加成效果不同。
    final static int TYPE_NORMAL = 1;       //普通阵法
    final static int TYPE_YIZHI = 2;        // 一字阵法
    final static int TYPE_LONGFEI = 3;      // 龙飞阵
    final static int TYPE_TIEJIA = 4;       // 铁甲阵
    final static int TYPE_YANXING = 5;      //  雁行阵
    final static int TYPE_YULIN = 6;        // 鱼鳞阵

    int type;//阵法类型
    String Name; //阵法名字
    int maxPlayer;// 阵法可以容纳的人数

    /**
     * 阵型 类型是一个整数
     * 使用 Integer.toBinaryString 转化为一个25位二进制数，把二进制数
     * 从左到右，从上到下排列，可以得到如下一个阵型，1表示可以放置武将，0表示不可以放置武将
     * 这样一个整数就可以表示一个阵型了。
     * 1 0 0 0 0
     * 0 1 0 0 0
     * 0 0 1 0 0
     * 0 1 0 0 0
     * 1 0 0 0 0
     */
    int zhenXing = 33554431;

    public ZhenFaModel(){
        initZhenFa();
    }

    void initZhenFa() {
        maxPlayer = 0;
        int zhenxing = zhenXing;
        for (int i = 0; i < 26; i++) {
            if (i == 0) continue;
            zhenxing = zhenxing >> 1;
            String str = Integer.toBinaryString(zhenxing);
            char last = str.charAt(str.length() - 1);
            if (last == '1') {
                maxPlayer++;
            } else {

            }
        }
    }
}
