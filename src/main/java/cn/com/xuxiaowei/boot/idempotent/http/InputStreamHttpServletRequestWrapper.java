package cn.com.xuxiaowei.boot.idempotent.http;

import cn.com.xuxiaowei.boot.idempotent.util.RequestUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 请求流支持多次获取{@link HttpServletRequest}
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class InputStreamHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    /**
     * 构造包装给定请求的请求对象。
     *
     * @param request 包装请求
     * @throws IOException 读取流异常
     */
    public InputStreamHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String characterEncoding = request.getCharacterEncoding();
        String inputStream = RequestUtils.getInputStream(request);
        body = inputStream == null ? null : inputStream.getBytes(characterEncoding);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

}
