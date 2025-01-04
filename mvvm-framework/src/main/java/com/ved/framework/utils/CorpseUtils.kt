package com.ved.framework.utils

import android.app.Activity
import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.widget.TextView
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

object CorpseUtils {
    fun remove(s: String?): String? = s?.replace("[\r\n]".toRegex(), "")?.replace(" ", "")

    fun first(s:String?) : String{
        s?.let {
            if (it.contains(".")){
                return it.split(".")[0]
            }else{
                return it
            }
        } ?: kotlin.run {
            return ""
        }
    }

    /**
     * 移除字符串中  最后一个字符
     */
    fun String.modifiedString() = takeUnless { isNullOrEmpty() }?.substring(0,length -1) ?: ""

    /**
     * 获取最大值
     */
    fun findMax(list: List<Int?>): Int? {
        return list.sortedWith(compareBy { it }).last()
    }

    fun makeTime(t:Int?) : String?{
        t?.let {
            if (it >= 10){
                return StringUtils.parseStr(it)
            }else{
                return "0${it}"
            }
        } ?: kotlin.run {
            return ""
        }
    }

    fun countChar(str: String, ch: Char): Int {
        // 将字符串转换为字符数组
        val chs = str.toCharArray()
        // 定义变量count存储字符串出现的次数
        var count = 0
        for (i in chs.indices) {
            if (chs[i] == ch) {
                count++
            }
        }
        return count
    }

    fun split(s:String) : Int{
        val str = s.split(".")
        val ss = str.getOrNull(1)
        return if (ss?.isNotEmpty() == true){
            ss.length
        }else{
            0
        }
    }

    fun k(s:String) = try {
        if (s.contains("/") && s.contains(".")){
            s.substring(s.lastIndexOf("/") + 1,s.lastIndexOf("."))
        }else{
            s
        }
    } catch (e: Exception) {
        s
    }

    fun j(s:String) = try {
        k(s).length == 8
    } catch (e: Exception) {
        false
    }

    fun a(c: Activity, id: Int, a: String, b: String) {
        when (LocaleHelper.getLanguage(Utils.getContext())) {
            "zh", "TW" -> {
                (c.findViewById<View>(id) as TextView).text = a
            }
            else -> {
                (c.findViewById<View>(id) as TextView).text = b
            }
        }
    }

    fun b(a: String, b: String) = when (LocaleHelper.getLanguage(Utils.getContext())) {
        "zh", "TW" -> {
            a
        }
        else -> {
            b
        }
    }

    fun c(a: Int, b: Int) = when (LocaleHelper.getLanguage(Utils.getContext())) {
        "zh", "TW" -> {
            a
        }
        else -> {
            b
        }
    }

    fun d(c: View, id: Int, a: String, b: String) {
        when (LocaleHelper.getLanguage(Utils.getContext())) {
            "zh", "TW" -> {
                (c.findViewById<View>(id) as TextView).text = a
            }
            else -> {
                (c.findViewById<View>(id) as TextView).text = b
            }
        }
    }

    fun e(c: Activity, id: Int, a: String, b: String) {
        when (LocaleHelper.getLanguage(Utils.getContext())) {
            "zh", "TW" -> {
                (c.findViewById<View>(id) as TextView).hint = a
            }
            else -> {
                (c.findViewById<View>(id) as TextView).hint = b
            }
        }
    }

    fun f() = when (LocaleHelper.getLanguage(Utils.getContext())) {
        "zh", "TW" -> true
        else -> false
    }

    fun g(func:(Int)->Unit) = when (LocaleHelper.getLanguage(Utils.getContext())) {
        "zh", "TW" -> func.invoke(1)
        else -> func.invoke(2)
    }

    /**
     * 扩展方法，扩大点击区域
     * NOTE: 需要保证目标targetView有父View，否则无法扩大点击区域
     *
     * @param expandSize 扩大的大小，单位px
     */
    fun View.expandTouchView(expandSize: Int = DisplayUtil.dip2px(Utils.getContext(),10f)) {
        val parentView = (parent as? View)
        parentView?.post {
            val rect = Rect()
            getHitRect(rect) //getHitRect(rect)将视图在父容器中所占据的区域存储到rect中。
            rect.left -= expandSize
            rect.top -= expandSize
            rect.right += expandSize
            rect.bottom += expandSize
            parentView.touchDelegate = TouchDelegate(rect, this)
        }
    }

    internal inline fun <reified T : Any> noOpDelegate(): T {
        val javaClass = T::class.java
        return Proxy.newProxyInstance(
            javaClass.classLoader, arrayOf(javaClass), NO_OP_HANDLER
        ) as T
    }

    private val NO_OP_HANDLER = InvocationHandler { _, _, _ ->
        // no op
    }
}