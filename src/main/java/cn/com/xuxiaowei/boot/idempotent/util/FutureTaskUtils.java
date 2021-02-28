package cn.com.xuxiaowei.boot.idempotent.util;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * {@link FutureTask} 线程工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class FutureTaskUtils {

    /**
     * 使用线程调用 环绕通知 执行
     *
     * @param joinPoint 环绕通知 切面方法信息
     * @return 返回 执行结果
     */
    public static Object proceed(ProceedingJoinPoint joinPoint) throws ExecutionException, InterruptedException {
        FutureTask<?> futureTask = new FutureTask<>((Callable<?>) () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new Exception(throwable);
            }
        });
        return futureTask.get();
    }

}
