package com.crp.example.createThreadExamples;

import lombok.extern.log4j.Log4j;

/**
 * ms-util - com.crp.example.createThreadExamples
 * 类继承thread实现创建新线程
 * @author superChen
 * @create 2017-05-08 11:03
 */
@Log4j
public class CreateThreadExample1 extends Thread {

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }
    }


    public static void main(String[] args) {
        CreateThreadExample1 thread1 = new CreateThreadExample1();
        log.info("thread1 start!");
        thread1.start();

    }

}
