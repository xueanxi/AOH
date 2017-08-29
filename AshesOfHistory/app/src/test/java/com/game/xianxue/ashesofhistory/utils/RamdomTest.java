package com.game.xianxue.ashesofhistory.utils;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 8/25/17.
 */
public class RamdomTest {
    @Before
    public void setUp() throws Exception {

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
            if(RandomUtils.flipCoin()){
                truetime++;
            }else{
                falsetime++;
            }
        }
        System.out.println("true = "+truetime+ " false = "+falsetime);
    }

}