package com.ved.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


class CopyUtils {

    public static <T> T sourceToTarget(Object source, Class<T> tClass) {
        if (source == null) {
            return null;
        }
        // 获取源对象的类的详情信息
        Class<?> sClass = source.getClass();
        // 获取源对象的所有属性
        Field[] sFields = sClass.getDeclaredFields();
        // 获取目标对象的所有属性
        Field[] tFields = tClass.getDeclaredFields();
        T target = null;
        try {
            // 通过类的详情信息，创建目标对象 这一步等同于UserTwo target = new UserTwo()；
            target = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 循环取到源对象的单个属性
        for (Field sField : sFields) {
            // 循环取到目标对象的单个属性
            for (Field tField : tFields) {
                // 判断源对象的属性名、属性类型是否和目标对象的属性名、属性类型一致
                if (sField.getName().equals(tField.getName()) && sField.getGenericType().equals(tField.getGenericType())) {
                    try {
                        // 获取源对象的属性名，将属性名首字母大写，拼接如：getUsername、getId的字符串
                        String sName = sField.getName();
                        char[] sChars = sName.toCharArray();
                        sChars[0] -= 32;
                        String sMethodName = "get" + String.valueOf(sChars);
                        // 获得属性的get方法
                        Method sMethod = sClass.getMethod(sMethodName);
                        // 调用get方法
                        Object sFieldValue = sMethod.invoke(source);
                        // 获取目标对象的属性名，将属性名首字母大写，拼接如：setUsername、setId的字符串
                        String tName = tField.getName();
                        char[] tChars = tName.toCharArray();
                        tChars[0] -= 32;
                        String tMethodName = "set" + String.valueOf(tChars);
                        // 获得属性的set方法
                        Method tMethod = tClass.getMethod(tMethodName, tField.getType());
                        // 调用方法，并将源对象get方法返回值作为参数传入
                        tMethod.invoke(target, sFieldValue);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return target;
    }


    public static <T, E> List<E> copyProperties(List<T> source, Class<E> tClass) throws Exception {
        // 判断传入源数据是否为空，如果空，则抛自定义异常
        if (null == source) {
            throw new Exception("数据源为空");
        }
        // 创建一个集合，用于存储目标对象，全部数据抓换完成后，将该集合返回
        List<E> targetList = new ArrayList<>();
        // 循环取到单个源对象
        for (T t : source) {
            // 获取源对象的类的详情信息
            Class<?> sClass = t.getClass();
            // 获取源对象的所有属性
            Field[] sFields = sClass.getDeclaredFields();
            // 获取目标对象的所有属性
            Field[] tFields = tClass.getDeclaredFields();
            E target;
            try {
                // 通过类的详情信息，创建目标对象 这一步等同于UserTwo target = new UserTwo()；
                target = tClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("目标对象创建失败");
            }
            // 循环取到源对象的单个属性
            for (Field sField : sFields) {
                // 循环取到目标对象的单个属性
                for (Field tField : tFields) {
                    // 判断源对象的属性名、属性类型是否和目标对象的属性名、属性类型一致
                    if (sField.getName().equals(tField.getName()) && sField.getGenericType().equals(tField.getGenericType())) {
                        try {
                            // 获取源对象的属性名，将属性名首字母大写，拼接如：getUsername、getId的字符串
                            String sName = sField.getName();
                            char[] sChars = sName.toCharArray();
                            sChars[0] -= 32;
                            String sMethodName = "get" + String.valueOf(sChars);
                            // 获得属性的get方法
                            Method sMethod = sClass.getMethod(sMethodName);
                            // 调用get方法
                            Object sFieldValue = sMethod.invoke(t);
                            // 获取目标对象的属性名，将属性名首字母大写，拼接如：setUsername、setId的字符串
                            String tName = tField.getName();
                            char[] tChars = tName.toCharArray();
                            tChars[0] -= 32;
                            String tMethodName = "set" + String.valueOf(tChars);
                            // 获得属性的set方法
                            Method tMethod = tClass.getMethod(tMethodName, tField.getType());
                            // 调用方法，并将源对象get方法返回值作为参数传入
                            tMethod.invoke(target, sFieldValue);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            // 将通过反射创建的目标对象放入集合中
            targetList.add(target);
        }
        // 返回集合
        return targetList;
    }


}