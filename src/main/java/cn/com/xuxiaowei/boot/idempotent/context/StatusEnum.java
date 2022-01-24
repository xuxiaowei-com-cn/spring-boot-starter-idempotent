package cn.com.xuxiaowei.boot.idempotent.context;

/**
 * 幂等状态 枚举
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public enum StatusEnum {

    /**
     * 执行前
     */
    BEFORE_EXECUTE,

    /**
     * 正在执行
     */
    EXECUTE,

    /**
     * 执行后
     */
    AFTER_EXECUTE,

    /**
     * 中断
     */
    INTERRUPTED,

    /**
     * 异常
     */
    EXCEPTION,

}
