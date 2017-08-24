package com.crp.Multithreading;

import lombok.extern.slf4j.Slf4j;

/**
 * com.crp.Multithreading
 *
 * @author chenrunping
 * @create 2017-08-24 10:22
 **/
@Slf4j
public class Main{

    public static void main(String[] args){
        SourceA s = new SourceA();
        TestThread tt = new TestThread(s);
        TestRunnable tr = new TestRunnable(s);
        Thread t = new Thread(tr);
        log.info("调用线程1");
        tt.start();
        log.info("调用线程2");
        t.start();
    }
}
