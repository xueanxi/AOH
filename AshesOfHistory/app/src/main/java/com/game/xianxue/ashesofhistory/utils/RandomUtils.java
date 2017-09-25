package com.game.xianxue.ashesofhistory.utils;

import java.util.Random;

/**
 * Created by user on 5/24/17.
 */
public class RandomUtils {

    /**
     * 给一个发生某件事情的概率，通过随机数的规则判断，事情是否会发生，
     * 比如 命中率为0.7 ，则命中返回true ， 没有命中返回 false
     *
     * @param probability
     * @return
     */
    public static boolean isHappen(float probability) {
        Random random = new Random();
        double rNumber = random.nextDouble();
        if (rNumber < probability) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 随机拋硬币方法：
     * 百分之五十概率 返回 true，百分之五十概率 返回 false，
     *
     * @return
     */
    public static boolean flipCoin() {
        return new Random().nextBoolean();
    }

    /**
     * 返回 left ～ right之间 随机的数字
     *
     * @param left
     * @param right
     * @return
     */
    public static float getRandomNumberbetween(float left, float right) {
        int Ileft = (int) (left * 100f);
        int Iright = (int) (right * 100f);
        int range = Iright - Ileft;
        Random random = new Random();
        int randomInt = random.nextInt(range) + Ileft;
        return (randomInt / 100f);
    }

    /**
     * 参数传进来 N 个浮点数，代表各自被选中的概率。
     * 结果返回被选中的索引值。
     *
     * @param args
     * @return
     */
    public static int selectOneByProbability(float[] args) {
        int result = -1;
        float total = 0;
        for (int i = 0; i < args.length; i++) {
            total += args[i];
        }
        float random = new Random().nextFloat();
        float values = random * total;

        float temp = 0;
        for (int i = 0; i < args.length; i++) {
            if (values >= temp && values < (temp + args[i])) {
                result = i;
                break;
            } else {
                temp += args[i];
            }
        }
        return result;
    }
}