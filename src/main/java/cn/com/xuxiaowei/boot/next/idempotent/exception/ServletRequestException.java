package cn.com.xuxiaowei.boot.next.idempotent.exception;

/**
 * Servlet 请求异常
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class ServletRequestException extends IdempotentException {

	public ServletRequestException(String message) {
		super(message);
	}

}
