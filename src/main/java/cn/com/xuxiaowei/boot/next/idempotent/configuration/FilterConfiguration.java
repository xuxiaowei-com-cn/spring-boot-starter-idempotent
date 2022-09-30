package cn.com.xuxiaowei.boot.next.idempotent.configuration;

import cn.com.xuxiaowei.boot.next.idempotent.filter.HttpServletRequestInputStreamFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class FilterConfiguration {

	/**
	 * 请求流转换为多次读取的请求流 过滤器
	 * <p>
	 * 使用配置文件进行条件注解为 {@link Bean}
	 * @return 返回 请求流转换为多次读取的请求流 过滤器
	 * @see ConditionalOnExpression Spring EL 表达式成立时条件才成立
	 */
	@Bean
	@ConditionalOnExpression("${xxw.idempotent.input-stream-filter:false}")
	public HttpServletRequestInputStreamFilter httpServletRequestInputStreamFilter() {
		return new HttpServletRequestInputStreamFilter();
	}

}
