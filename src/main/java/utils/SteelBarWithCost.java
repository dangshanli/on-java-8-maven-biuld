package utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 带有切割花费的钢条问题
 * 每次切割需要花费1$
 */
public class SteelBarWithCost {
    public static void main(String[] args) {
        int n = 50;
        SteelBarWithCost steelBar = new SteelBarWithCost();
        int max = steelBar.memoizedCutRod(n);
        System.out.println("max = "+max);
    }

    final int cost = 1;
    static int[] prices;

    static {
        try {
            prices = SteelPriceUtil.readPrices();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //自上而下法
    int memoizedCutRod(int n) {
        int[] r = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            r[i] = Integer.MIN_VALUE;
        }
        int[] firstPosition = new int[n + 1];
        int max = memoizedCutRodAux(n, r, firstPosition);
        List<Integer> designs = cutDesign(n, firstPosition);
        System.out.println("design schema:" + designs);
        return max;
    }

    /**
     * 计算最高收益。以及n以下长度钢管，最高收益时，首段切割长度
     *
     * @param n             钢条长度
     * @param r             利润缓存
     * @param firstPosition n以下钢条，首段切割长度
     * @return
     */
    int memoizedCutRodAux(int n, int[] r, int[] firstPosition) {
        if (r[n] >= 0)
            return r[n];
        int q;
        if (n == 0)
            q = 0;
        else {
            q = Integer.MIN_VALUE;
            for (int i = 1; i <= n; i++) {
                int price = prices[i] - cost + memoizedCutRodAux(n - i, r, firstPosition);
                if (q < price) {
                    firstPosition[n] = i;
                    q = price;
                }
            }
        }
        r[n] = q;
        return q;
    }


    /**
     * 钢铁截断方案
     *
     * @param n 钢铁长度
     * @param s 从1->n长度的钢铁，实现最佳收益的首段切割长度
     * @return 长度为n钢铁的最佳收益切割方案
     */
    List<Integer> cutDesign(int n, int[] s) {
        List<Integer> design = new ArrayList<>();
        while (n > 0) {
            design.add(s[n]);
            n -= s[n];
        }
        return design;
    }


}
