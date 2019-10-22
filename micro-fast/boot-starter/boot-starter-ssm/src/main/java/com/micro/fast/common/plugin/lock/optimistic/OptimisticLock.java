package com.micro.fast.common.plugin.lock.optimistic;

import java.lang.annotation.*;

/**
 * @author  lsy
 * 加在方法上，表示方法使用乐观锁
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OptimisticLock {

}
