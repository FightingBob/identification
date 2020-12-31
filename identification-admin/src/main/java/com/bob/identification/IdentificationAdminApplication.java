package com.bob.identification;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bob.identification.*.mapper")
public class IdentificationAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdentificationAdminApplication.class, args);
    }

}
