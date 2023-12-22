package com.ved.framework.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.codbking.widget.utils.b;

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
        if (b.INSTANCE.a()){
            return new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        }else {
            return new Gson();
        }
    }
}
