package com.ved.framework.utils;


import java.util.HashMap;
import java.util.Map;

public class DataHolder {//解决activity之间传递数据过大导致崩溃的问题

    private Map dataList = new HashMap();

    private static volatile DataHolder instance;

    public static DataHolder getInstance() {

        if (instance == null) {

            synchronized (DataHolder.class) {

                if (instance == null) {

                    instance = new DataHolder();

                }

            }

        }

        return instance;

    }

    public void setData(String key, Object o) {
        dataList.put(key, o);

    }

    public Object getData(String key) {
        Object o = dataList.get(key);
        dataList.clear();
        return o;
    }

}