package com.game.xianxue.aoh.utils;

import com.game.xianxue.aoh.Log.LogUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 5/24/17.
 */
public class RandomUtils {
    private static final String TAG = "RandomUtils";

    /**
     * 给一个发生某件事情的概率，通过随机数的规则判断，事情是否会发生，
     * 比如 命中率为0.7 ，则命中返回true ， 没有命中返回 false
     *
     * @param probability
     * @return
     */
    public static boolean isHappen(float probability) {
        // 如果发生的概率是0.5 则调用 nextBoolean（）
        if(probability == 0.5){
            return new Random().nextBoolean();
        }

        // 如果发生的概率不是0.5 则调用 nextDouble
        Random random = new Random();
        double rNumber = random.nextDouble();
        if (rNumber < probability) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回 left ～ right之间 随机的数字
     *
     * @param left
     * @param right
     * @return
     */
    public static float getRandomNumberbetween(float left, float right) {
        if(left == right) {
            LogUtils.e(TAG,"getRandomNumberbetween() 不要传入两个相等的参数 ！！！" );
            return left;
        }
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

    /**
     * 从 allNumber 个目标里面挑出 selectNumber 目标
     * 其中 allNumber 个目标使用索引[0,1,2,3,4....]表示
     * 返回一个被选择的目标索引数组，比如[2,4,7]
     * @param allNumber
     * @param selectNumber
     * @return
     */
    public static int[] getRandomTarget(int allNumber,int selectNumber){
        int[] result;
        // 如果 allNumber <= selectNumber 的条件下，返回所有 allNumber 的索引
        if(allNumber <= selectNumber){
            result = new int[allNumber];
            for(int i=0;i<allNumber;i++){
                result[i] = i;
            }
        }else{
            result = new int[selectNumber];
            Random random = new Random();
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int i=0;i<allNumber;i++){
                list.add(i);
            }
            int selectIndex = 0;
            for(int i=0;i<selectNumber;i++) {
                selectIndex = random.nextInt(allNumber);
                result[i] = list.get(selectIndex);
                list.remove(selectIndex);
                allNumber--;
            }
        }
        return result;
    }
}
