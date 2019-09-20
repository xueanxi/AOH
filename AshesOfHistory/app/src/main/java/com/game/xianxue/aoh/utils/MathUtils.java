package com.game.xianxue.aoh.utils;

import java.util.Scanner;

/**
 * Created by user on 10/16/17.
 */


public class MathUtils {

    public static double calculateLGLR(double x[], double y[], double x0) {
        int m = x.length;

        double result;
        double j = 0, k = 0, l = 0;
        int ic = 0;
        for (int ia = 0; ia < m; ia++) {
            double w = 1;
            for (int ib = 0; ib < m; ib++) {
                if (ia != ib) w /= (x[ia] - x[ib]);
            }
            k += (w / ((x0 - x[ic]))) * y[ic];
            l += (w / ((x0 - x[ic])));
            ic++;
            j = k / l;
        }
        result = j;

        return result;
    }
}

