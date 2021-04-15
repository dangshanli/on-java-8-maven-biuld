package utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 长度为n的钢条，其每个长度的价格各个不同
 * 已知其参照价格表 int[] prices，在可以取整切割的情况下，如何切割才能卖出最高的价格
 */
public class ProgramingSteelBar {
    static int[] prices;//价格表

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
        int max =  memoizedCutRodAux(n, r, firstPosition);
        List<Integer> designs = cutDesign(n,firstPosition);
        System.out.println("design schema:"+designs);
        return max;
    }

    /**
     * 计算最高收益。以及n以下长度钢管，最高收益时，首段切割长度
     * @param n 钢条长度
     * @param r 利润缓存
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
                int price = prices[i] + memoizedCutRodAux(n - i, r, firstPosition);
                if (q < price) {
                    firstPosition[n] = i;
                    q = price;
                }
            }
        }
        r[n] = q;
        return q;
    }

    int max(int a, int b) {
        return Math.max(a, b);
    }


    static private class SteelCut {
        int max;
        List<Integer> designs;

        public int getMax() {
            return max;
        }

        public List<Integer> getDesigns() {
            return designs;
        }
    }

    //自下而上法
    SteelCut bottomUpCutRod(int n) {
        int[] r = new int[n + 1];
        int[] s = new int[n + 1];
        r[0] = 0;
        for (int i = 1; i <= n; i++) {
            int q = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                int price = prices[j] + r[i - j];
                if (q < price) {
                    q = price;
                    s[i] = j;
                }
            }
            r[i] = q;
        }
        SteelCut sCut = new SteelCut();
        sCut.max = r[n];
        sCut.designs = cutDesign(n, s);
        return sCut;
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

    public static void main(String[] args) {
        int n = 50;//钢条长度
        ProgramingSteelBar steelBar = new ProgramingSteelBar();

        System.out.println("自上而下:");

        long start = System.currentTimeMillis();
        int max = steelBar.memoizedCutRod(n);
        long duration = System.currentTimeMillis() - start;
        System.out.println("duration=" + duration);
        System.out.println("max=" + max);

        System.out.println();

        System.out.println("自下而上:");
        start = System.currentTimeMillis();
        SteelCut cut = steelBar.bottomUpCutRod(n);
        duration = System.currentTimeMillis() - start;
        System.out.println("duration=" + duration);
        System.out.println("max=" + cut.getMax());

        System.out.println("design schema:" + cut.getDesigns());
    }
}
