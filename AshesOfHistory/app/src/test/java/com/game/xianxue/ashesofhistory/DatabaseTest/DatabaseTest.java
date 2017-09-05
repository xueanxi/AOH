package com.game.xianxue.ashesofhistory.DatabaseTest;

import com.game.xianxue.ashesofhistory.game.model.lineup.LineupUnitBase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DatabaseTest {
    @Test
    public void testLineUPCreate() throws Exception {
        LineupUnitBase unit = new LineupUnitBase("1,2,3,7,8,9");
        System.out.print("unit = "+unit);

        unit = new LineupUnitBase("3,9,3,4,5");
        System.out.print("unit = "+unit);
    }

    @Test
    public void test01(){
        int a = 10;
        System.out.println("a = "+a);
    }
}