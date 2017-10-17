package com.game.xianxue.ashesofhistory.BattleTest;

import com.game.xianxue.ashesofhistory.game.model.DamgeModel;
import com.game.xianxue.ashesofhistory.utils.MathUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DamageTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int a = 10;
        System.out.println("a = "+a);
        assertEquals(4, 2 + 2);
    }

    /**
     * 用拉格郎日插值法 计算抗性承受的伤害系数
     */
    @Test
    public void testResist(){
        int damage = 500;
        int resist = 1000;

        /**
         * 1000 0.9
         * 800  0.8
         * 500  0.6
         * 200  0.4
         * 0    0
         */
        double x[] = new double[6];
        double y[] = new double[6];
        double x0;

        x[0] = 0;
        x[1] = 200;
        x[2] = 400;
        x[3] = 800;
        x[4] = 1000;
        x[5] = 1300;

        y[0] = 1;
        y[1] = 0.6;
        y[2] = 0.4;
        y[3] = 0.2;
        y[4] = 0.1;
        y[5] = 0.01;

        x0 = 500;
        double y0 = MathUtils.calculateLGLR(x, y, x0);
        System.out.print("result = "+y0);
    }


    /**
     * 用分段直线，计算承受伤害
     */
    @Test
    public void testResist2(){
        float result = 0;
       /* for(int i =-5;i<30;i++){
            result = DamgeModel.getResistResult(i*50);
            System.out.println("resist  = "+(i*50)+ " result:"+result);
        }*/

        float resist = 447;
        float damage = 1088f;
        result = damage* DamgeModel.getResistResult(resist);
        System.out.println("resist  = "+resist+ " result:"+result);
    }

    private void displayTime(long time1,long time2){
        System.out.println("耗时："+(time2 - time1));
    }
}