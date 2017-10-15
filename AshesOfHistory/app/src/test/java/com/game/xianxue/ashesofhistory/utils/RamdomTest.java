package com.game.xianxue.ashesofhistory.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Created by user on 8/25/17.
 */
public class RamdomTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetRandomNumberbetween() throws Exception {
        float start = 0.4f;
        float end = 0.6f;
        float result = 0;
        for(int i=0;i<100;i++){
            result = RandomUtils.getRandomNumberbetween(start,end);
            System.out.println("result = "+result);
        }
    }

    @Test
    public void testIsHappen() throws Exception {
        int truetime = 0;
        int falsetime = 0;
        for(int i=0;i<1000;i++){
            if(RandomUtils.isHappen(0.3f)){
                truetime++;
            }else{
                falsetime++;
            }
        }
        System.out.println("true = "+truetime+ " false = "+falsetime);
    }

    @Test
    public void testFiveFive() throws Exception {
        int truetime = 0;
        int falsetime = 0;
        for(int i=0;i<1000;i++){
            if(RandomUtils.isHappen(0.5f)){
                truetime++;
            }else{
                falsetime++;
            }
        }
        System.out.println("true = "+truetime+ " false = "+falsetime);
    }

    @Test
    public void tesSelectOneByProbability() throws Exception {

        float [] arrays = {0.1f,0.2f,0.6f,0.2f,0.1f};
        int one = 0;
        int two = 0;
        int thr = 0;
        int fou = 0;
        int fiv = 0;
        for(int i=0;i<10000000;i++){
            int result = RandomUtils.selectOneByProbability(arrays);
            switch (result){
                case 0:
                    one++;
                    break;
                case 1:
                    two++;
                    break;
                case 2:
                    thr++;
                    break;
                case 3:
                    fou++;
                    break;
                case 4:
                    fiv++;
                    break;
            }
        }
        System.out.println("one = "+one);
        System.out.println("two = "+two);
        System.out.println("thr = "+thr);
        System.out.println("fou = "+fou);
        System.out.println("fiv = "+fiv);
    }

    @Test
    public void testNextInt() throws Exception {
        Random random = new Random();
        for(int i = 0;i< 100 ;i++){
            System.out.println(random.nextInt(10));
        }
    }

    @Test
    public void testGetRandomTarget() throws Exception {
        int[] result;
        for(int x = 0;x<100;x++){
            result = RandomUtils.getRandomTarget(7,5);
            for(int i = 0;i< result.length ;i++){
                System.out.print(result[i]+"");
            }
            System.out.println("");
        }
    }
}