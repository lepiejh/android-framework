package com.ved.framework.utils.glide;


/**
 * Glide类型
 *
 * @author LSQ
 */
public enum GlideType implements BaseEnum<String> {

    // 默认
    DEFAULT("default"),
    // 裁切并居中展示
    CENTER_CROP("center_crop"),
    // 压缩并居中展示 - 完整展示
    CENTER_INSIDE("center_inside"),
    // 压缩并居中展示 - 部分展示
    FIT_CENTER("fit_center"),
    // 圆角
    ROUNDED_CORNERS("rounded_corners"),
    // 圆形
    CIRCLE_CROP("circle_crop");

    private String key;

    GlideType(String key) {
        this.key = key;
    }

    /**
     * 获取Key
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * 获取Value
     */
    @Override
    public String getValue() {
        return this.name();
    }
}
