package com.ved.framework.utils;

import android.os.Build;

import java.util.List;

public class ListUtil {


    public static String ListToString(List<String> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.join(",", list);
        } else {
            String style = "";
            if (!BlankUtil.isEmptyList(list)) {
                StringBuilder buffer = new StringBuilder();
                for (String item : list) {
                    buffer.append(item);
                    buffer.append(",");
                }
                style = buffer.substring(0, buffer.length() - 1);
            }
            return style;
        }
    }

    /**
     * 使用时，务必保证每个对象中实现了equals()方法。
     * 如果是自己写的类，比如，Dog，Cat这些的，请重写Object中的equals方法！
     *
     * @param aList 左右顺序无所谓
     * @param bList 左右顺序无所谓
     * @return 尽可能避免相同的情况
     */
    public static boolean equals(List aList, List bList) {

        if (aList == bList) {
            return true;
        }

        if (aList.size() != bList.size()) {
            return false;
        }

        int n = aList.size();
        int i = 0;
        while (n-- != 0) {
            if (!aList.get(i).equals(bList.get(i))) {
                return false;
            }
            i++;
        }

        return true;
    }

}
