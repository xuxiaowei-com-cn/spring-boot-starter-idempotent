package cn.com.xuxiaowei.boot.idempotent.filter;

import cn.com.xuxiaowei.boot.idempotent.util.InputStreamHttpServletRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求流转换为多次读取的请求流 过滤器
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Component
public class HttpServletRequestInputStreamFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 将请求流转换为可多次读取的请求流
        ServletRequest inputStreamHttpServletRequestWrapper = new InputStreamHttpServletRequestWrapper(httpServletRequest);
        chain.doFilter(inputStreamHttpServletRequestWrapper, response);
    }

}
