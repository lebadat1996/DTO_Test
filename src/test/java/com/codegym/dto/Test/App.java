package com.codegym.dto.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {


    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Test test = new Test();
//        test.setName("badat");
//        test.setEmail("@gmail");
        Method method = test.getClass().getMethod("setName", String.class);
        method.invoke(test,"badat");
        Method method1 = test.getClass().getMethod("setEmail", String.class);
        method1.invoke(test,"badat@gmail");
        System.out.println(test);
    }
}
