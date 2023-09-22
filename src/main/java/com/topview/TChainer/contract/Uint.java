package com.topview.TChainer.contract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于实现sol的uint类型
 * @author 刘家辉
 * @date 2023/09/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Uint {
    /**默认256位
    * 8 16 32 64 128 256... solidity支持的位数
    **/
    int value() default 256;
    boolean isArray() default false;
}
