package cn.com.xuxiaowei.boot.idempotent.controller;

import cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 测试幂等
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
@ConditionalOnExpression("${xxw.idempotent.test:false}")
@RequestMapping("${xxw.idempotent.test-prefix:/test}/idempotent")
public class TestIdempotentRestController {

    /**
     * Map
     *
     * @param request  请求
     * @param response 响应
     * @return 返回 Map
     */
    @Idempotent(key = "key1", expireTime = 10, timeUnit = TimeUnit.SECONDS, header = "h1")
    @RequestMapping("/map")
    public Map<String, Object> map(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("uuid", UUID.randomUUID().toString());
        log.info(String.valueOf(map));
        return map;
    }

    /**
     * Map 严格模式
     *
     * @param request  请求
     * @param response 响应
     * @return 返回 Map
     */
    @Idempotent(strict = true, key = "key1", expireTime = 10, timeUnit = TimeUnit.SECONDS)
    @RequestMapping("/map-strict")
    public Map<String, Object> mapStrict(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("uuid", UUID.randomUUID().toString());
        log.info(String.valueOf(map));
        return map;
    }

    /**
     * List
     *
     * @param request  请求
     * @param response 响应
     * @return 返回 List
     */
    @Idempotent(key = "key2", expireTime = 10, timeUnit = TimeUnit.SECONDS, header = "h2")
    @RequestMapping("/list")
    public List<Map<String, Object>> list(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(8);
        list.add(map);
        map.put("uuid", UUID.randomUUID().toString());
        log.info(String.valueOf(list));
        return list;
    }

    /**
     * Integer
     *
     * @param request  请求
     * @param response 响应
     * @return 返回 Integer
     */
    @Idempotent(key = "key3", expireTime = 10, timeUnit = TimeUnit.SECONDS, header = "h3")
    @RequestMapping("/integer")
    public Integer integer(HttpServletRequest request, HttpServletResponse response) {
        Random random = new Random();
        int i = random.nextInt();
        log.info(String.valueOf(i));
        return i;
    }

}
