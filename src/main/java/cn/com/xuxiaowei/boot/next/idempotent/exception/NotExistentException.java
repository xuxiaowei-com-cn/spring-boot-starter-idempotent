package cn.com.xuxiaowei.boot.next.idempotent.exception;

/**
 * 不存在 异常
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class NotExistentException extends IdempotentException {

	public NotExistentException(String message) {
		super(message);
	}

}
