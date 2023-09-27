package com.topview.TChainer.contract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author :Lictory
 * @date : 2023/09/26
 */



@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Address {

}
