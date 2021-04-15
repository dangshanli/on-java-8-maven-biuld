package utils;

import java.util.concurrent.atomic.AtomicInteger;

public class ValotileT {

    private AtomicInteger i = new AtomicInteger(0);

    void counter() {
        for (int j = 0; j < 10000; j++) {
            this.i.getAndAdd(1);
        }
    }

    public int getI() {
        return this.i.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ValotileT vt = new ValotileT();

        Thread t1 = new Thread(() -> {
            vt.counter();
        });

        Thread t2 = new Thread(() -> {
            vt.counter();
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(vt.getI());


    }


}
