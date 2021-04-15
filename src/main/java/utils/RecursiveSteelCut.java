package utils;

import utils.SteelPriceUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * 递归求解钢条切割问题
 * 效率极低
 */
public class RecursiveSteelCut {
    static int[] prices;

    static {
        try {
            prices = SteelPriceUtil.readPrices();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int cutRod(int n) {
        if (n == 0)
            return 0;
        int q = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            int price = prices[i] + cutRod(n - i);
            q = max(q, price);
        }
        return q;
    }

    static int max(int a, int b) {
        return Math.max(a, b);
    }

    public static void main(String[] args) throws FileNotFoundException {
        int max = cutRod(20);
        System.out.println("max=" + max);
    }
}
