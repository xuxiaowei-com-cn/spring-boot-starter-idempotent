package cn.com.xuxiaowei.boot.idempotent.context;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 幂等调用记录
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Accessors(chain = true)
public class IdempotentContext implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 幂等 Token 放入响应 Header 中的 Name
	 */
	public static final String TOKEN = "token";

	/**
	 * 幂等状态 放入响应 Header 中的 Name
	 */
	public static final String STATUS = "status";

	/**
	 * 请求时间（首次） 放入响应 Header 中的 Name
	 */
	public static final String REQUEST_DATE = "requestDate";

	/**
	 * 执行结果时间（首次） 放入响应 Header 中的 Name
	 */
	public static final String RESULT_DATE = "resultDate";

	/**
	 * 过期时间 放入响应 Header 中的 Name
	 */
	public static final String EXPIRE_DATE = "expireDate";

	/**
	 * 调用次数 放入响应 Header 中的 Name
	 */
	public static final String NUMBER = "number";

	/**
	 * 幂等 Token
	 */
	private String token;

	/**
	 * 幂等状态
	 */
	private StatusEnum status;

	/**
	 * 请求时间（首次）
	 */
	private LocalDateTime requestDate;

	/**
	 * 执行结果时间（首次）
	 */
	private LocalDateTime resultDate;

	/**
	 * 过期时间
	 */
	private LocalDateTime expireDate;

	/**
	 * 调用次数
	 * <p>
	 * 0：正在执行 1：调用一次 2：调用两次 3：调用三次
	 */
	private Integer number = 0;

}
