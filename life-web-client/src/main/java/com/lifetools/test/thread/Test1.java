package com.lifetools.test.thread;

/**
 * Created by Zheng.Ke
 * Date 2016/11/9.
 */
public class Test1 {
    public static void main(String[] args) {
        Test1 test1 = new Test1();
        try {
            synchronized (test1) {
                test1.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(1);
        synchronized (test1) {
            test1.notify();
        }
        System.out.println(2);
    }


}
