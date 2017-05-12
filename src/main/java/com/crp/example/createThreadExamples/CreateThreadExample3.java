package com.crp.example.createThreadExamples;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * ms-util - com.crp.example.createThreadExamples
 *使用callable接口创建新线程
 * @author superChen
 * @create 2017-05-08 11:30
 */
@Log4j
public class CreateThreadExample3 implements Callable{

    public String call(){
        return "hello world!";
    }

    public static void main(String args[]){

        CreateThreadExample3 createThreadExample3 = new CreateThreadExample3();
        FutureTask<String> futureTask = new FutureTask<String>(createThreadExample3);
        Thread thread3 = new Thread(futureTask);
        log.info("thread3 start!");
        thread3.start();

    }

}
