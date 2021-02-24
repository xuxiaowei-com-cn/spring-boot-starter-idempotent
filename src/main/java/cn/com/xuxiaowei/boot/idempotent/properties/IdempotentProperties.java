package cn.com.xuxiaowei.boot.idempotent.properties;

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
     * 默认为：false，不可为空
     */
    private Boolean inputStreamFilter = false;

}
