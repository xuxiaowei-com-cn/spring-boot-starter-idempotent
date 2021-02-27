package cn.com.xuxiaowei.boot.idempotent.exception;

/**
 * 不存在 Token 异常
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class NotExistentTokenException extends IdempotentException {

    public NotExistentTokenException(String message) {
        super(message);
    }

}
