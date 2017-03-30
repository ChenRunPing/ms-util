package com.crp.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ms-util - com.crp.test
 *
 * @author superChen
 * @create 2017-02-17 10:55
 */
@Slf4j
@RestController
public class TestController {

    @RequestMapping("/test")
    public String welcomeIndex(){
        String response = "Hello Java,Hello World!";
        log.info("response:"+response);
        return  response;
    }

}
