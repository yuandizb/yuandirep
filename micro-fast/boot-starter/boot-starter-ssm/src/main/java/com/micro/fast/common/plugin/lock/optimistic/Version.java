package com.micro.fast.common.plugin.lock.optimistic;

import java.lang.annotation.*;

/**
 * @author lsy
 * 运行时注解
 * 可以加载字段和参数上
 * 可以被继承
 * 标识实体类哪个字段是乐观锁
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Documented
public @interface Version {
    String value() default "";
}
