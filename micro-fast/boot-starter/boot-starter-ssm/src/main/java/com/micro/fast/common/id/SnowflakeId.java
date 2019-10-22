package com.micro.fast.common.id;

/**
 * 使用雪花算法生成分布式唯一主键
 * id为64位的long型
 * 第一位标记为 0
 * 41位的开始时间-当前时间 69年不重复
 * 10位的机器id   最多1024个节点
 * 12位的每个时间戳内能生产的id数 每个时间戳内4096个
 * @author  lry
 */
public class SnowflakeId {

    /** 起始时间戳，用于用当前时间戳减去这个时间戳，算出偏移量 **/
    private final long startTime = 1519740777809L;

    /** workerId占用的位数10（表示只允许workId的范围为：0-1023）**/
    private final long workerIdBits = 10L;

    /** 序列号占用的位数：12（表示只允许workId的范围为：0-4095）**/
    private final long sequenceBits = 12L;

    /** workerId可以使用的最大数值：31 **/
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    private final long workerIdShift = sequenceBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits;

    /** 用mask防止溢出:位与运算保证计算的结果范围始终是 0-4095 **/
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private boolean isClock = false;

    /**
     * 基于Snowflake创建分布式ID生成器
     * 注：sequence
     *
     * @param workerId     工作机器ID,数据范围为0~31
     */
    public SnowflakeId(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    public void setClock(boolean clock) {
        isClock = clock;
    }

    /**
     * 获取ID
     *
     * @return
     */
    public synchronized Long nextId() {
        long timestamp = this.timeGen();

        // 闰秒：如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    this.wait(offset << 1);
                    timestamp = this.timeGen();
                    if (timestamp < lastTimestamp) {
                        throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
            }
        }

        // 解决跨毫秒生成ID序列号始终为偶数的缺陷:如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            // 通过位与运算保证计算的结果范围始终是 0-4095
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = this.tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        /*
         * 1.左移运算是为了将数值移动到对应的段(41、5、5，12那段因为本来就在最右，因此不用左移)
         * 2.然后对每个左移后的值(la、lb、lc、sequence)做位或运算，是为了把各个短的数据合并起来，合并成一个二进制数
         * 3.最后转换成10进制，就是最终生成的id
         */
        return ((timestamp - startTime) << timestampLeftShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 保证返回的毫秒数在参数之后(阻塞到下一个毫秒，直到获得新的时间戳)
     *
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     *
     * @return timestamp
     */
    private long timeGen() {
        if (isClock) {
            // 解决高并发下获取时间戳的性能问题
            return SystemClock.now();
        } else {
            return System.currentTimeMillis();
        }
    }

}
