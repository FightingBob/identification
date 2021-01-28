package com.bob.identification;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableAsync // 开启异步调用
@MapperScan("com.bob.identification.*.mapper")
public class IdentificationAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdentificationAdminApplication.class, args);
    }

}
