package com.ved.framework.utils;

import java.util.List;

/**
 * 判断各种数据是否为null的工具类
 *
 * @author L
 */
public class BlankUtil {


    /**
     * 判断text是否为null或者为空字符串
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        if (text == null || "".equals(text)) {
            return true;
        }
        return false;
    }

    /**
     * 判断list是否为null或空数组
     *
     * @param list
     * @return
     */
    public static boolean isEmptyList(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断obj是否为null
     *
     * @param obj
     * @return
     */
    public static boolean isEmptyObj(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为0
     */
    public static boolean isZero(Integer integer) {
        if (integer == null || integer == 0) {
            return true;
        }
        return false;
    }

}
