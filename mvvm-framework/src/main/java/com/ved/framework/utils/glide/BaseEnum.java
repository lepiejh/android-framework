package com.ved.framework.utils.glide;

/**
 * Base Enum interface
 *
 * @param <K> Key Type
 * @author LSQ
 */
public interface BaseEnum<K> {

    /**
     * 获取Key
     *
     * @return key
     */
    K getKey();

    /**
     * 获取Value
     *
     * @return value
     */
    String getValue();
}