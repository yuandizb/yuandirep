package com.micro.fast.common.plugin.lock.optimistic;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

/**
 * @author lsy
 */
@Getter
@Setter
public class CacheOptimisticLock {

    private String fieldName;

    private Field field;
}
