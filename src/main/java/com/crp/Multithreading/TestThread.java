package com.crp.Multithreading;

import lombok.extern.slf4j.Slf4j;

/**
 * com.crp.Multithreading
 *
 * @author chenrunping
 * @create 2017-08-24 10:21
 **/
@Slf4j
public class TestThread extends Thread {
    private int time = 1;
    private SourceA s = null;
    String id = "002";

    public void setTime(int time) {
        this.time = time;
    }

    public TestThread(SourceA s){
        this.s = s ;
    }

    @Override
    public void run() {
        try {
            log.info("i will sleep"+ time);
            sleep(time);
        } catch (InterruptedException e) {
            log.error("异常：",e);
        }

        synchronized(s){
            try {
                log.info("我"+ id +"要进行等待了");
                s.wait();
            } catch (InterruptedException e) {
                log.error("异常：",e);
            }
            System.out.println("我被唤醒了");
            System.out.println("我存入了id"+id);
            s.setSource(id);
        }
    }

}