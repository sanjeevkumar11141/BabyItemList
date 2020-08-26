package com.sanju007.testdemo;

public class Test {
    public static void main(String[] args) {
        testReminder();
    }

    static int index = 0;

    public static void testReminder(){
        int a [] = new  int[7];
        index = (index + 97) % a.length ;
        System.out.println("Index value is :"+index);
    }
}
