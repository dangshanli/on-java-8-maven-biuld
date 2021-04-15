package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.Iterator;

public class SteelPriceUtil {

    static int N;
    //价格函数
    static public int[] priceTable(int n) throws FileNotFoundException {
        N = n;
        SecureRandom random = new SecureRandom();
        int[] arr = random.ints(0, 5).limit(n).toArray();
        int[] prices = new int[n+1];
        prices[0] = Integer.MIN_VALUE;
        prices[1] = 1;
        int count = 1;
        PrintStream printStream = new PrintStream("src/main/resources/number.txt");
        for (int i : arr) {
            int price = prices[count] + i;
            printStream.println(prices[count]);
            prices[++count] = price;
            if (count == n)
                break;
        }
        printStream.println(prices[n]);
        return prices;
    }

    static public int[] readPrices() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/number.txt"));
        Iterator<Integer> prices = reader.lines()
                .mapToInt(s -> Integer.valueOf(s))
                .iterator();
        int[] arr = new int[101];
        arr[0] = Integer.MIN_VALUE;
        int count = 0;
        while (prices.hasNext()) {
            arr[++count] = prices.next();
        }
        return arr;
    }
}
