package cn.com.xuxiaowei.boot.idempotent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序执行入口
 * <p>
 * 发布可执行jar包到中央仓库时，忽略此文件
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@SpringBootApplication
public class IdempotentApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdempotentApplication.class, args);
	}

}
