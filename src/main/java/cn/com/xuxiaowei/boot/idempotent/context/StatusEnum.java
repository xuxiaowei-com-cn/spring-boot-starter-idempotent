package cn.com.xuxiaowei.boot.idempotent.context;

/**
 * 幂等状态 枚举
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public enum StatusEnum {

    /**
     * 未创建
     */
    NOT_CREATE,

    /**
     * 创建
     */
    CREATE,

    /**
     * 正在执行
     */
    EXECUTE,

    /**
     * 正常
     */
    NORMAL,

    /**
     * 重复执行
     */
    REPEAT,

    /**
     * 过期
     */
    EXPIRE,

    /**
     * 中断
     */
    INTERRUPTED,

    /**
     * 异常
     */
    EXCEPTION,

}
