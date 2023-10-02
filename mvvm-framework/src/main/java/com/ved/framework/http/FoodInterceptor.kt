package com.ved.framework.http

import com.ved.framework.utils.*
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

class FoodInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var origin: Request = chain.request()
        val currentTime = System.currentTimeMillis()/1000
        val requestBody: RequestBody? = origin.body
        val builder = origin.newBuilder()
        FoodUtils.getInstance().calcSign(MapUtils.getInstance().sortMap(
            if (requestBody is FormBody){
                LinkedHashMap<String,Any>().apply {
                    val formBody = origin.body as FormBody
                    for (i in 0 until formBody.size) {
                        this[formBody.name(i)] = formBody.value(i)
                    }
                }
            }else{
                JsonPraise.gsonToMap(
                    Buffer().let {
                        requestBody?.writeTo(it)
                        it.readString(
                            requestBody?.contentType()?.charset(Charset.forName("UTF-8")) ?: kotlin.run {
                                Charset.forName("UTF-8")
                            }
                        )
                    })
            },currentTime
        ))?.let {
            builder.addHeader("x-jiu-sign",it)
        }
        builder.addHeader("x-jiu-sign-ts",StringUtils.parseStr(currentTime))
        val values = SPUtils.getInstance("prams_sp").getEntity(LinkedHashMap::class.java,null)
        if (values != null) {
            for ((key,value) in values){
                val k = StringUtils.parseStr(key)
                val v = StringUtils.parseStr(value)
                if (k.isNotEmpty() && v.isNotEmpty()){
                    builder.addHeader(k,v)
                }
            }
        }
        val headerList = SPUtils.getInstance("header_sp").getList(Pair::class.java)
        if (headerList.isNotEmpty()){
            for (headers in headerList){
                val key = StringUtils.parseStr(headers.first)
                val value = StringUtils.parseStr(headers.second)
                if (key.isNotEmpty() && key.isNotEmpty()){
                    builder.addHeader(key,value)
                }
            }
        }
        origin = builder.build()
        return chain.proceed(origin)
    }
}