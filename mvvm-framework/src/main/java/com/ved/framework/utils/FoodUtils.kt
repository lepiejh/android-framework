package com.ved.framework.utils

import okhttp3.Headers
import java.io.ObjectStreamException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class FoodUtils {
    private object SingletonHolder {
        val instance = FoodUtils()
    }

    //防止反序列化产生多个对象
    @Throws(ObjectStreamException::class)
    private fun readResolve(): Any {
        return SingletonHolder.instance
    }

    companion object {
        fun getInstance() = SingletonHolder.instance
    }


    fun calcSign(signStr: String?): String? {
        KLog.e("Interceptor","https paramsStr : $signStr")
        if (signStr.isNullOrEmpty())return ""
        val userId = SPUtils.getInstance().getString("user_id")
        if (userId.isNullOrEmpty())return ""
        return try {
            val mac: Mac = Mac.getInstance("HmacSHA256")
            val secret: String = userId
            val secretKey = SecretKeySpec(
                secret.toByteArray(charset("UTF-8")), mac.algorithm
            )
            mac.init(secretKey)
            val hash: ByteArray = mac.doFinal(signStr.toByteArray(charset("UTF-8")))
            Base64.encode(hash)
        } catch (e: Exception) {
            ""
        }
    }

    fun parseHeaders(h: Headers){
        val map = LinkedHashMap<String,String>()
        for ((key,value) in h){
            when(key){
                "x-jiu-token-code","x-jiu-token","x-user-type","x-user-id" ->{
                    if (value.isNotEmpty()) {
                        map[key] = value
                    }
                }
            }
        }
        if (map.isNotEmpty()) {
            SPUtils.getInstance("prams_sp").saveEntity(map)
        }
    }

    fun addHeaders(list: MutableList<Pair<Any, Any>>) {
        SPUtils.getInstance("header_sp").saveList(Pair::class.java, list)
    }
}