package cn.com.xuxiaowei.boot.idempotent.context;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 幂等内容
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class IdempotentContext implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 幂等 Token
     */
    private String token;

    /**
     * 幂等状态
     */
    private StatusEnum status;

    /**
     * 幂等Token创建时间
     */
    private LocalDateTime tokenDate;

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
     * -1：未创建幂等，未找到
     * -0：已创建，未调用
     * 1：调用一次
     * 2：调用两次
     * 3：调用三次
     */
    private Integer number = -1;

}
