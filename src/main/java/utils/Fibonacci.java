package utils;

/**
 * 计算斐波那契数
 */
public class Fibonacci {
    public static void main(String[] args) {
        final Fibonacci fibonacci = new Fibonacci();
        int n = 70;
        TestTime tt = new TestTime();
        long cost = tt.showTimeMills(() -> {
            long fibs = fibonacci.getFib(n);
            System.out.println("fibs=" + fibs);
        });
        System.out.println("time:" + cost + "ms");
        System.out.println(fibonacci.getFib(70));
    }

    /**
     * 获取第n位的斐波那契数
     *
     * @param n
     * @return
     */
    public long getFib(int n) {
        long[] cache = new long[n + 1];
        for (int i = 0; i < n + 1; i++) {
            cache[i] = Integer.MIN_VALUE;
        }
        return fib(n, cache);
    }

    private long fib(int n, long[] cache) {
        if (n == 1)
            return 0;
        else if (n == 2)
            return 1;
        if (cache[n] > 0)
            return cache[n];
        long result = fib(n - 1, cache) + fib(n - 2, cache);
        cache[n] = result;
        return result;
    }


}
