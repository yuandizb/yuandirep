package com.micro.fast.common.shardbatis;

import java.lang.annotation.*;

/**
 * 指定分表依据的参数的注解，分表的参数上必须有这个注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface IdShard{
  String value() default "id";
}
