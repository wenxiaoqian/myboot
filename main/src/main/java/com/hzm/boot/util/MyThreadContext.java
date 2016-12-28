package com.hzm.boot.util;

import static java.lang.System.out;

/**
 * 上下文线程变量
 *
 * @author xiaoqian.wen
 * @create 2016-12-28 10:07
 **/
public class MyThreadContext {

    /**
     *  ThreadLocal 子线程不能获取主线程的值
     *
     *  InheritableThreadLocal 子线程可以获取主线程的值
     */
    final static ThreadLocal<String> local = new ThreadLocal<String>();
    final static ThreadLocal<String> local2 = new InheritableThreadLocal<String>();

    private static void work(final ThreadLocal<String> local) {
        local.set("a");
        out.println(Thread.currentThread() + "," + local.get());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                out.println(Thread.currentThread() + "," + local.get());
                local.set("b");
                out.println(Thread.currentThread() + "," + local.get());
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        out.println(Thread.currentThread() + "," + local.get());
    }


    public void main(String[] args) {
        work(local);
        work(local2);
    }
}
