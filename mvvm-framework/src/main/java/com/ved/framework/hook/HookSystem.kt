package com.ved.framework.hook

import android.annotation.SuppressLint
import android.os.Handler
import com.ved.framework.R
import com.ved.framework.callback.ProxyHandlerCallBack
import com.ved.framework.utils.KLog
import com.ved.framework.utils.UIUtils
import java.io.ObjectStreamException

class HookSystem private constructor(){
    private object SingletonHolder {
        val instance = HookSystem()
    }

    init {
        // 防止反射获取多个对象的漏洞
        if (null != SingletonHolder.instance) {
            throw RuntimeException(UIUtils.getString(R.string.singleton_hook_hint))
        }
    }

    //防止反序列化产生多个对象
    @Throws(ObjectStreamException::class)
    private fun readResolve(): Any {
        return SingletonHolder.instance
    }

    companion object {
        fun getInstance() = SingletonHolder.instance
    }

    /**
     * Activity top position already set to onTop=false
     */
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    fun hookSystemHandler() {
        try {
            //获取ActivityThread实例
            val activityThreadClazz = Class.forName("android.app.ActivityThread")
            val sCurrentActivityThreadField = activityThreadClazz.getDeclaredField("sCurrentActivityThread")
            sCurrentActivityThreadField.isAccessible = true
            val sCurrentActivityThreadObj = sCurrentActivityThreadField.get(null)
            //获取mH实例
            val mHField = activityThreadClazz.getDeclaredField("mH")
            mHField.isAccessible = true
            val mH = mHField.get(sCurrentActivityThreadObj) as Handler
            //给mH添加ProxyHandlerCallBack
            val callBackField = Handler::class.java.getDeclaredField("mCallback")
            callBackField.isAccessible = true
            callBackField.set(mH, ProxyHandlerCallBack(mH))
            KLog.d("system_callback", "已为mH设置ProxyHandlerCallBack")
        } catch (e: Exception) {
            KLog.d("system_callback", "设置ProxyHandlerCallBack失败，${e.message}")
        }
    }
}