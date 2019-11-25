package com.so;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 应用模块名称： api启动类<br>
 * MapperScan 扫描mapper所在包<br>
 * ComponentScan 扫描所有包及相关注解包<br>
 * 
 * @author show
 * @since 2019/11/12 15:48
 */
@SpringBootApplication
@MapperScan(basePackages = "com.so.mapper")
@ComponentScan(basePackages = {"com.so", "org.n3r.idworker"})
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
