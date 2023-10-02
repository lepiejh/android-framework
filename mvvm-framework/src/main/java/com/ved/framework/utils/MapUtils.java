package com.ved.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapUtils {
    private static class SingletonHolder {
        private static final MapUtils INSTANCE = new MapUtils();
    }

    public static MapUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //防止反序列化产生多个对象
    private Object readResolve() throws ObjectStreamException {
        return MapUtils.getInstance();
    }

    public String sortMap(Map<String, Object> map,long t){
        List<String> paramsList = new ArrayList<>();
        if (com.ved.framework.utils.bland.code.MapUtils.isNotEmpty(map)) {
            TreeMap<String, Object> paramsMap = new TreeMap<>(map);
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                String key = entry.getKey();
                Object values = entry.getValue();
                if (values != null && com.ved.framework.utils.StringUtils.isNotEmpty(values)) {
                    if (values instanceof Collection) {
                        String collValues = StringUtils.join((Collection)values, ",");
                        paramsList.add(key + "=" + collValues);
                    } else {
                        paramsList.add(key + "=" + values);
                    }
                }
            }
        }
        paramsList.add("ts=" + t);
        return StringUtils.join(paramsList, "&");
    }
}
