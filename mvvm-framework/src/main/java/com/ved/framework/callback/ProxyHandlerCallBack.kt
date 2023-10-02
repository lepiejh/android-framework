package com.ved.framework.callback

import android.os.Handler
import android.os.Message
import com.ved.framework.utils.Constant
import com.ved.framework.utils.KLog

class ProxyHandlerCallBack(mH: Handler) : Handler.Callback {

    private var mHandler: Handler = mH

    override fun handleMessage(msg: Message): Boolean {
        try {
            mHandler.handleMessage(msg)//实现系统handler的handleMessage
        } catch (e: IllegalStateException) {
            if (msg.what == Constant.EXECUTE_TRANSACTION && e.message?.contains("Activity top position already set to onTop") == true) {
                KLog.d("system_callback", "ProxyHandlerCallBack get \"Activity top position already set to onTop\" ")
            } else {
                KLog.e("system_callback","ProxyHandlerCallBack error: "+e.message)
            }
        }
        //这里必须返回true。因为 ProxyHandlerCallBack 的 handleMessage 已经实现了系统handler的handleMessage
        //如果return false，会调用两次系统的 handleMessage
        return true
    }
}