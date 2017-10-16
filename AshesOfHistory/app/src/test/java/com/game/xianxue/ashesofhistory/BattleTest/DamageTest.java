package com.game.xianxue.ashesofhistory.BattleTest;

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

    @Test
    public void test01(){
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

    private void displayTime(long time1,long time2){
        System.out.println("耗时："+(time2 - time1));
    }
}