package cn.com.xuxiaowei.boot.idempotent.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class ResponseUtils {

	/**
	 * 响应
	 * @param response 响应
	 * @param object 数据
	 * @throws IOException IO异常
	 */
	public static void response(HttpServletResponse response, Object object) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String json = objectMapper.writeValueAsString(object);
		response.getWriter().println(json);
		response.setStatus(200);
		response.flushBuffer();
	}

}
