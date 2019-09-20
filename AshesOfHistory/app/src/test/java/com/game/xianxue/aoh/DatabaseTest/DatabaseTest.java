package com.game.xianxue.aoh.DatabaseTest;

import com.game.xianxue.aoh.game.model.lineup.FormationUnitBase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DatabaseTest {
    @Test
    public void testLineUPCreate() throws Exception {
        FormationUnitBase unit = new FormationUnitBase("1,2,3,7,8,9");
        System.out.print("unit = "+unit);

        unit = new FormationUnitBase("3,9,3,4,5");
        System.out.print("unit = "+unit);
    }

    @Test
    public void test01(){
        int a = 10;
        System.out.println("a = "+a);
    }

}