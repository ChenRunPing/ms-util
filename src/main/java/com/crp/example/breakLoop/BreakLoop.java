package com.crp.example.breakLoop;

import lombok.extern.slf4j.Slf4j;

/**
 * ms-util - com.crp.example.breakLoop
 *
 * @author superChen
 * @create 2017-05-11 17:33
 */
@Slf4j
public class BreakLoop {

    /**
     *
     * @param args
     */
    public static void main(String[] args){

        point:for (int i=0;i<100;i++){
            for(int j = 0;j<10;j++){
                log.info("i:"+i);
                if(j == 5){
                    log.info("j:"+j);
                    break point;
                }
            }
        }
        log.info("1111");

    }
}
