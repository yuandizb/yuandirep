package com.micro.fast.common.plugin.lock.optimistic;

/**
 * 乐观锁插件运行时异常
 * @author lsy
 */
public class OptimisticException extends RuntimeException {
    public OptimisticException(String message) {
        super(message);
    }
}
