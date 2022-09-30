package cn.com.xuxiaowei.boot.next.idempotent.filter;

import cn.com.xuxiaowei.boot.next.idempotent.configuration.FilterConfiguration;
import cn.com.xuxiaowei.boot.next.idempotent.http.InputStreamHttpServletRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * 请求流转换为多次读取的请求流 过滤器
 * <p>
 * 使用配置文件进行条件注解为 {@link Bean}
 *
 * @author xuxiaowei
 * @see FilterConfiguration#httpServletRequestInputStreamFilter()
 * @since 0.0.1
 */
public class HttpServletRequestInputStreamFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// 将请求流转换为可多次读取的请求流
		ServletRequest inputStreamHttpServletRequestWrapper = new InputStreamHttpServletRequestWrapper(
				httpServletRequest);
		chain.doFilter(inputStreamHttpServletRequestWrapper, response);
	}

}