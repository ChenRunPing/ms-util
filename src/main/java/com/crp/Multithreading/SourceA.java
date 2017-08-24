package com.crp.Multithreading;

import java.util.ArrayList;
import java.util.List;

/**
 * com.crp.Multithreading
 *
 * @author chenrunping
 * @create 2017-08-24 10:19
 **/
public class SourceA {
    private List<String> list = new ArrayList<String>();
    public synchronized void getSource(){
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
    public synchronized void setSource(String id){
        list.add(id);
    }
}
