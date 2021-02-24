package cn.com.xuxiaowei.boot.idempotent.context;

/**
 * 幂等内容持有者
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class IdempotentContextHolder {

    private static final ThreadLocal<IdempotentContext> HOLDER = ThreadLocal.withInitial(IdempotentContext::new);

    public static IdempotentContext getCurrentContext() {
        return HOLDER.get();
    }

    public static void setCurrentContext(IdempotentContext currentContext) {
        HOLDER.set(currentContext);
    }

    public static void clearContext() {
        HOLDER.remove();
    }

}
