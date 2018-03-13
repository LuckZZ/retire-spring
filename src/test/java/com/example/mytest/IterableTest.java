package com.example.mytest;

import java.util.*;

public class IterableTest {
    public static void main(String[] args){
        String[] s = new String[]{"a","b","c"};
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(s));
        Iterator<String> iterator =  list.iterator();
        while (iterator.hasNext()){
            String str = iterator.next();
            System.out.println(str);
        }
        System.out.println("- - - - - - -");
        Iterable<String> iterable = (Iterable) list.iterator();
        iterable.forEach((ele)->{System.out.println(ele);});
    }
}
