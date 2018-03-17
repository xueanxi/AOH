package com.game.xianxue.ashesofhistory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MathTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int a = 10;
        System.out.println("a = "+a);
        assertEquals(4, 2 + 2);
    }

    /**
     * 测试上下取整
     */
    @Test
    public void testQuZheng(){
        // 向上取整
        int result =  (int)Math.ceil(6.9);
        System.out.println("result = "+result);

        // 向下取整
        int result2 =  (int)Math.floor(6.999);
        System.out.println("result = "+result2);
    }
}