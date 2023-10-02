package com.ved.framework.proguard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NotProguard, Means not proguard something, like class, method, field<br/>
 * 1. 在 Proguard 配置文件中过滤被这个注解修饰的元素
 *    -keep @com.ved.framework.proguard.NotProguard class * {*;}
 * -keep class * {
 * @com.ved.framework.proguard.NotProguard <fields>;
 * }
 * -keepclassmembers class * {
 * @com.ved.framework.proguard.NotProguard <methods>;
 * }
 *
 * 2.使用NotProguard让指定的域不进行混淆：
 * (1) 整个类不混淆:
 * @NotProguard
 * public class User {}
 * (2) 单个属性不混淆
 * @NotProguard
 * public int id;
 * (3) 单个方法不混淆
 * @NotProguard
 * public boolean isValid() {
 * …
 * }
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface NotProguard {
}
