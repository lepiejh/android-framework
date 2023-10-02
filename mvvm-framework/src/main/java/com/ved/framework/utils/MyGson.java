package com.ved.framework.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyGson {
    private MyGson() {
    }

    private static class SingletonHolder {
        private static final MyGson INSTANCE = new MyGson();
    }

    public static MyGson getInstance() {
        return MyGson.SingletonHolder.INSTANCE;
    }

    public Gson getGson(){
        return new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
    }
}
