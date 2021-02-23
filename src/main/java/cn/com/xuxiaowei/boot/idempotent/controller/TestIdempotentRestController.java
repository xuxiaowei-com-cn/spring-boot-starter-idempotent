package cn.com.xuxiaowei.boot.idempotent.controller;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 测试幂等
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/test/idempotent")
public class TestIdempotentRestController {

    /**
     * UUID
     *
     * @param request  请求
     * @param response 响应
     * @return 返回 UUID
     */
    @Idempotent(key = "key1", expireTime = 10, header = "h1")
    @RequestMapping("/uuid")
    public Map<String, Object> uuid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("uuid", UUID.randomUUID().toString());
        log.info(String.valueOf(map));
        return map;
    }

}
