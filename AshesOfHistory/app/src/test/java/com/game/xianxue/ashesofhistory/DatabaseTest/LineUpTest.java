package com.game.xianxue.ashesofhistory.DatabaseTest;

import com.game.xianxue.ashesofhistory.game.model.lineup.UnitBase;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class LineUpTest {
    @Test
    public void testLineUPCreate() throws Exception {
        UnitBase unit = new UnitBase("1,2,3,7,8,9");
        System.out.print("unit = "+unit);

        unit = new UnitBase("3,9,3,4,5");
        System.out.print("unit = "+unit);
    }

    @Test
    public void testInitMatrix(){
        String[][] matrix = new String[5][6];
        int x = 0;
        for(int i = 0;i<5;i++){
            for(int j =0;j<6;j++){
                if(i%2 ==0)continue;
                matrix[i][j] = "a";
                x++;
            }
        }

        for(int i = 0;i<5;i++){
            for(int j =0;j<6;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

}