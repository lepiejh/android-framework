package com.ved.framework.net;

import com.ved.framework.http.cookie.CookieJarImpl;
import com.ved.framework.http.cookie.store.PersistentCookieStore;
import com.ved.framework.http.interceptor.CacheInterceptor;
import com.ved.framework.utils.Configure;
import com.ved.framework.utils.Constant;
import com.ved.framework.utils.KLog;
import com.ved.framework.utils.MyGson;
import com.ved.framework.utils.Utils;

import java.io.File;
import java.io.ObjectStreamException;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

class RetrofitClient {

    private static class SingletonHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //防止反序列化产生多个对象
    private Object readResolve() throws ObjectStreamException {
        return RetrofitClient.getInstance();
    }

    public <T> T create(final Class<T> service,int i,Map<String, String> headers) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        return new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .cache(new Cache(new File(Utils.getContext().getCacheDir(), "ved_cache"),Constant.CACHE_TIMEOUT))
                        .cookieJar(new CookieJarImpl(new PersistentCookieStore(Utils.getContext())))
                        .addInterceptor(new MyInterceptor(headers))
                        .addInterceptor(new CacheInterceptor(Utils.getContext()))
                        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                        .addInterceptor(chain -> {
                            Request request = chain.request();

                            long startTime = System.currentTimeMillis();
                            Response response = chain.proceed(chain.request());
                            long endTime = System.currentTimeMillis();
                            long duration = endTime - startTime;
                            MediaType mediaType = response.body().contentType();
                            String content = response.body().string();

                            KLog.e("Interceptor", "请求地址：| " + request);
                            KLog.e("Interceptor", "请求体返回：| Response:" + content);
                            KLog.e("Interceptor", "----------请求耗时:" + duration + "毫秒----------");
                            return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();
                        }).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                        .connectTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                        .proxy(Proxy.NO_PROXY)
                        .build())
                .addConverterFactory(GsonDConverterFactory.create(MyGson.getInstance().getGson()))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(Configure.getUrl().get(i))
                .build().create(service);
    }


    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }

}

