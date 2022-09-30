package cn.com.xuxiaowei.boot.idempotent.properties;

import cn.com.xuxiaowei.boot.idempotent.controller.TestIdempotentRestController;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 幂等 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@ConfigurationProperties(prefix = "xxw.idempotent")
public class IdempotentProperties {

	/**
	 * 输入流过滤器（用于将输入流修改为可重复获取的输入流）
	 * <p>
	 * 默认值：false，不可为空
	 */
	private Boolean inputStreamFilter = false;

	/**
	 * 是否启用测试 Controller {@link TestIdempotentRestController}
	 * <p>
	 * 默认值：false
	 */
	private Boolean test = false;

	/**
	 * 测试路径前缀
	 * <p>
	 * 默认值：/test
	 */
	private String testPrefix = "/test";

	/**
	 * Redis 内容前缀
	 * <p>
	 * 默认值：idempotent
	 */
	private String prefix = "idempotent";

	/**
	 * Redis 调用记录名
	 * <p>
	 * 默认值：record
	 */
	private String record = "record";

	/**
	 * Redis 调用结果名
	 * <p>
	 * 默认值：result
	 */
	private String result = "result";

}
