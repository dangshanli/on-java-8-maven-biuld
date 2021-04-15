package utils;

//测试方法运行时间
public class TestTime {

    public long showTimeMills(Tester tester){
        long start = System.currentTimeMillis();
        tester.tester();
        long duration = System.currentTimeMillis() - start;
        return duration;
    }

    public static void main(String[] args) {

    }



}
