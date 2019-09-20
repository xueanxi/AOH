package com.game.xianxue.aoh.utils;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 8/25/17.
 */
public class TextTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testStringToIntArray(){
        int[] a1 = new int[]{1,23,4,5,7};
        float[] a2 = new float[]{1.5f,23f,4f,5.9f,7f};
        String s1 = TextUtils.getStringFromIntArray(a1);
        String s2 = TextUtils.getStringFromFloatArray(a2);
        System.out.println("s1 = "+s1);
        System.out.println("s2 = "+s2);

        int[]b1 = TextUtils.getIntArrayFromString(s1);
        float[]b2 = TextUtils.getFloatArrayFromString(s2);
        ShowUtils.showArraysJAVA(b1);
        System.out.println("");
        ShowUtils.showArraysJAVA(b2);
    }
}