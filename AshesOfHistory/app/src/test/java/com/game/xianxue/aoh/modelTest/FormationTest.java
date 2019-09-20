package com.game.xianxue.aoh.modelTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class FormationTest {
    @Test
    public void test1() throws Exception {
        int[][] array = new int[3][8];
        array[1][2] = 2;
        for(int i = 0;i<array.length;i++){
            for(int j = 0;j< array[i].length;j++){
                System.out.print(array[i][j]);
            }
            System.out.println("");
        }
    }
}