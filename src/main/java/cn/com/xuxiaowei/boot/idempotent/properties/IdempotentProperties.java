package cn.com.xuxiaowei.boot.idempotent.properties;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
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
     * 默认 调用之前创建Token（在 Redis 中）
     * <p>
     * 默认值：false
     * <p>
     * 第一优先级
     * <p>
     * 第二优先级：{@link Idempotent#beforeCreateToken}
     */
    private Boolean defaultBeforeCreateToken;

    /**
     * 输入流过滤器（用于将输入流修改为可重复获取的输入流）
     * <p>
     * 默认为：false，不可为空
     */
    private Boolean inputStreamFilter = false;

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
