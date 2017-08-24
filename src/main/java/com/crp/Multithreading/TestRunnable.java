package com.crp.Multithreading;

import lombok.extern.slf4j.Slf4j;

/**
 * com.crp.Multithreading
 *
 * @author chenrunping
 * @create 2017-08-24 10:18
 **/
@Slf4j
public class TestRunnable implements Runnable {

    private int time=1;
    private SourceA s;
    private String id = "001";
    public TestRunnable(SourceA s){
        this.s = s;
    }
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void run() {
        try {
            System.out.println("i will sleep"+ time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
           log.error("异常：",e);
        }

        synchronized(s){
            s.notify();
            System.out.println("我唤醒了002！");
            System.out.println("我存入了id"+id);
            s.setSource(id);
        }
    }

}
