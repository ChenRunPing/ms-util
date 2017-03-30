package com.crp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ms-util - com.crp.controller
 *
 * @author superChen
 * @create 2017-01-13 10:32
 */
@RestController
@Slf4j
public class MainController {


   @RequestMapping("/index")
   public String welcome(){
       String response = "hello,java;hello,world!";
       log.info("输出："+response);
       return  response;
   }

}
