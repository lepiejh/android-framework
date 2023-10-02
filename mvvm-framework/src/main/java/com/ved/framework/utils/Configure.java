package com.ved.framework.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configure {
    private static List<String> url;
    private static String imageHeard;
    private static int code;
    private static int authNumber;
    private static int form;
    private static int to;

    private Configure() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setUrl(String imageHeard,int code,String... url){
        List<String> urlList = new ArrayList<>();
        if (url.length > 0){
            urlList.addAll(Arrays.asList(url));
        }
        Configure.url = urlList;
        Configure.imageHeard = imageHeard;
        Configure.code = code;
    }

    public static void setAuth(int auth,int form,int to){
        Configure.authNumber = auth;
        Configure.form = form;
        Configure.to = to;
    }

    public static int getAuthNumber() {
        return authNumber;
    }

    public static int getForm() {
        return form;
    }

    public static int getTo() {
        return to;
    }

    public static List<String> getUrl() {
        if (url != null && url.size() > 0) {
            return url;
        }
        throw new NullPointerException("should be set in net url");
    }

    public static String getImageHeardUrl() {
        if (!TextUtils.isEmpty(imageHeard)) {
            return imageHeard;
        }
        throw new NullPointerException("should be set in image url");
    }

    public static int getCode() {
        return code;
    }
}
