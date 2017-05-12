package com.crp.example.joinString;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * ms-util - com.crp.example.joinString
 *
 * @author superChen
 * @create 2017-05-12 16:32
 */
@Slf4j
public class ChangeString {

    /**
     *通过反射机制实现修改不可变对象string的值
     * @throws Exception
     */
    private static void modifyString() throws Exception {

        //创建字符串"Hello World"， 并赋给引用s
        String s = "Hello World";

        System.out.println("s = " + s); //Hello World

        //获取String类中的value字段
        Field valueFieldOfString = String.class.getDeclaredField("value");

        //改变value属性的访问权限
        valueFieldOfString.setAccessible(true);

        //获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);

        //改变value所引用的数组中的第5个字符
        value[5] = '_';

        System.out.println("s = " + s);  //Hello_World
    }


    public static void main(String[] args) {
        try {
            modifyString();
        } catch (Exception e) {
           log.info("异常");
        }

    }
}
