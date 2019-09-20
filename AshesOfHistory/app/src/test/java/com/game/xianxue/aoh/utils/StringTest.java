package com.game.xianxue.aoh.utils;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 8/25/17.
 */
public class StringTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testInt(){
        String s = String.format("%s %s","1","2");
        System.out.println(s);
    }
}