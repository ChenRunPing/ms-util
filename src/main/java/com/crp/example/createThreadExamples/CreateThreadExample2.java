package com.crp.example.createThreadExamples;

import lombok.extern.log4j.Log4j;

/**
 * ms-util - com.crp.example.createThreadExamples
 * 实现Runnable创建新线程
 * @author superChen
 * @create 2017-05-08 11:09
 */
@Log4j
public class CreateThreadExample2 implements Runnable {

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        CreateThreadExample2 createThreadExample2 = new CreateThreadExample2();
        Thread thread2 = new Thread(createThreadExample2);
        log.info("thread2 start!");
        thread2.start();
    }
}
