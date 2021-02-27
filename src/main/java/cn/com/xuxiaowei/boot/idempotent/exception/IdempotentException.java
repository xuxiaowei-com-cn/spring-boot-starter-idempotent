package cn.com.xuxiaowei.boot.idempotent.exception;

/**
 * 父异常
 *
 * @author xuxiaowei
 * @see 0.0.1
 */
public class IdempotentException extends Exception {

    private IdempotentException() {
        super();
    }

    public IdempotentException(String message) {
        super(message);
    }

}
