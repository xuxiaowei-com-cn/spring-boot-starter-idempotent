package cn.com.xuxiaowei.boot.idempotent.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 请求工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class RequestUtils {

    /**
     * 请求参数集
     *
     * @param request 请求
     * @return 返回 请求参数集
     */
    public static String keyValue(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder keyValue = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            keyValue.append(key).append(":").append(Arrays.toString(value)).append("&");
        }
        return keyValue.toString();
    }

    /**
     * 请求参数集
     *
     * @param request 请求
     * @return 返回 请求参数集
     */
    @SneakyThrows
    public static String keyValueJson(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(parameterMap);
    }

    /**
     * 请求参数流
     *
     * @param request 请求
     * @return 返回 请求参数流
     * @throws IOException 读取流异常
     * @see HttpServletRequest#getContentLength()  请关注是否为负数
     */
    public static String getInputStream(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        int contentLength = request.getContentLength();
        if (contentLength == -1) {
            // GET 请求时
            // DELETE、HEAD、OPTIONS 请求未设置流时
            return null;
        } else {
            byte[] bytes = new byte[contentLength];
            String characterEncoding = request.getCharacterEncoding();
            inputStream.read(bytes);
            return new String(bytes, characterEncoding);
        }
    }

    /**
     * 根据 节点名称（使用 . 分隔） 获取 请求流中的参数
     *
     * @param request  请求
     * @param nodeName 节点名称，使用 . 分隔
     * @return 返回 请求流中的参数
     * @throws IOException 读取流异常
     */
    public static String getInputStreamNode(HttpServletRequest request, String nodeName) throws IOException {
        String inputStream = getInputStream(request);
        return JsonUtils.getNode(nodeName, inputStream);
    }

    /**
     * 移除参数
     *
     * @param queryString 参数
     * @param queryNames  移除参数名
     * @return 返回移除结果
     */
    public static String removeQuery(String queryString, String... queryNames) {
        if (queryString != null && !"".equals(queryString)) {
            for (String queryName : queryNames) {
                queryString = queryString.replaceAll("&?" + queryName + "=[^&]*", "");
            }
        }
        return queryString;
    }

    /**
     * 获取 Header Map
     *
     * @param request 请求
     * @return 返回 Header Map
     */
    public static Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>(8);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            map.put(headerName, headerValue);
        }
        return map;
    }

    /**
     * 获取 Headers Map
     *
     * @param request 请求
     * @return 返回 Headers Map
     */
    public static Map<String, List<String>> getHeadersMap(HttpServletRequest request) {
        Map<String, List<String>> map = new HashMap<>(8);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            List<String> headerValuesList = Collections.list(headerValues);
            map.put(headerName, headerValuesList);
        }
        return map;
    }

}
