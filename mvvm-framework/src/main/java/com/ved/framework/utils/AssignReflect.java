package com.ved.framework.utils;

import java.io.ObjectStreamException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AssignReflect {
    private static class SingletonHolder {
        private static final AssignReflect INSTANCE = new AssignReflect();
    }

    public static AssignReflect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //防止反序列化产生多个对象
    private Object readResolve() throws ObjectStreamException {
        return AssignReflect.getInstance();
    }

    /**
     * 将map对象的值赋值给T对象
     * @param cz T 类型
     * @param map 健值对
     * @return T对象
     */
    public <T> T convertObj(Class<T> cz, Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            KLog.w("AssignReflect","The incoming map object is empty or size is zero");
            return null;
        }
        T t = null;
        try {
            t = cz.newInstance();
            Method[] methods = cz.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().contains("set")
                        && !m.getName().equals("equals")
                        && !m.getName().equals("toString")
                        && !m.getName().equals("hashCode")) {
                    try {
                        m.invoke(t, map.get(getString(m.getName().substring(3))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将R对象的值赋值给T对象
     * @param des T 类型
     * @param raw R 值
     * @return T对象
     */
    public <T, R> T convertObj(T des, R raw) {
        if (raw == null) {
            KLog.w("AssignReflect","The incoming raw is empty");
            return null;
        }
        try {
            Method[] desMs = des.getClass().getDeclaredMethods();
            Method[] rawMs = raw.getClass().getDeclaredMethods();
            for (Method m : rawMs) {
                String mn = m.getName();
                if (mn.contains("get")
                        && !mn.equals("equals")
                        && !mn.equals("toString")
                        && !mn.equals("hashCode")) {
                    for(Method d : desMs){
                        String dn = d.getName();
                        if (dn.contains("set")
                                && !dn.equals("equals")
                                && !dn.equals("toString")
                                && !dn.equals("hashCode")) {
                            if(mn.substring(3).equals(dn.substring(3))){
                                d.invoke(des, m.invoke(raw));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return des;
    }

    /**
     *  将T对象的值赋值给map对象
     * @param object T对象
     * @return map对象
     */
    public <T> Map<String, Object> convertMap(T object) {
        if (object == null) {
            KLog.w("AssignReflect","The incoming object is empty");
            return null;
        }
        Map<String, Object> map = null;
        try {
            map = new HashMap<String, Object>();
            Class<?> cz = object.getClass();
            Method[] methods = cz.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().contains("get")
                        && !m.getName().equals("equals")
                        && !m.getName().equals("toString")
                        && !m.getName().equals("hashCode")) {
                    map.put(getString(m.getName().substring(3)), m.invoke(object));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将传入的英文字母，首个字母小写
     * @param string 要处理的字符串
     * @return 首个字母小写的字符串
     */
    private String  getString(String string){
        if(Objects.equals(string, "") || string == null){
            return null;
        }
        String s = string.substring(0, 1);
        string  = string.replace(s, s.toLowerCase());
        return string;
    }
}
