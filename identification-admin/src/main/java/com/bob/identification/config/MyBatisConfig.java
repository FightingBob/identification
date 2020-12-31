package com.bob.identification.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 * Created by LittleBob on 2020/12/17/017.
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.bob.identification.*.mapper", "com.bob.identification.dao"})
public class MyBatisConfig {
}
