package com.ved.framework.utils

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
}