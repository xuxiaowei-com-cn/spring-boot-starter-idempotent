package cn.com.xuxiaowei.boot.next.idempotent.service;

import cn.com.xuxiaowei.boot.next.idempotent.aspect.IdempotentAspect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Controller;

/**
 * 幂等超时接口
 *
 * @author xuxiaowei
 * @see IdempotentAspect#setTimeoutExceptionService(TimeoutExceptionService)
 * @since 0.0.1
 */
public interface TimeoutExceptionService {

	/**
	 * 自定义幂等调用超时返回的数据
	 * <p>
	 * 1、如果 {@link Controller} 返回数据类型与本方法返回数据类型相同，可直接使用 `return` 返回结果
	 * <p>
	 * 2、如果 {@link Controller} 返回数据类型与本方法返回数据类型不同，可直接使用 `return` 返回 `null`， 使用
	 * `javax.servlet.http.HttpServletResponse` 返回结果
	 * @param request 请求
	 * @param response 响应
	 * @param joinPoint 切点
	 * @return 返回 超时调用结果
	 */
	Object timeout(HttpServletRequest request, HttpServletResponse response, ProceedingJoinPoint joinPoint);

}
