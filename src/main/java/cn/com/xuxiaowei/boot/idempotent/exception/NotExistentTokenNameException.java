package cn.com.xuxiaowei.boot.idempotent.exception;

/**
 * 不存在 Token Name 异常
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class NotExistentTokenNameException extends IdempotentException {

	public NotExistentTokenNameException(String message) {
		super(message);
	}

}
