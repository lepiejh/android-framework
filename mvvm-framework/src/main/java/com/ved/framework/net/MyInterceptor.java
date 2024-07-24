package com.ved.framework.net;

import com.ved.framework.utils.SPUtils;
import com.ved.framework.utils.bland.code.DeviceUtils;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class MyInterceptor implements Interceptor {
    private final Map<String, String> headers;

    public MyInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = chain.request()
                .newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, Objects.requireNonNull(headers.get(headerKey))).build();
            }
        }
        builder.addHeader("device_id", DeviceUtils.getUniqueDeviceId());

        List<String> headerValues = request.headers("url_name");
        if (headerValues.size() > 0) {
            String headerValue = headerValues.get(0);
            HttpUrl oldHttpUrl = request.url();
            HttpUrl newBaseUrl;
            if("weixin".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse("https://api.weixin.qq.com/sns/oauth2/");
            }else{
                newBaseUrl = oldHttpUrl;
            }
            if (newBaseUrl != null) {
                HttpUrl newFullUrl = oldHttpUrl
                        .newBuilder()
                        .scheme(newBaseUrl.scheme())
                        .host(newBaseUrl.host())
                        .port(newBaseUrl.port())
                        .build();
                builder = builder.url(newFullUrl);
            }
        }
        request = builder.build();
        Response r = chain.proceed(request);
        r.close();
        return r;
    }
}