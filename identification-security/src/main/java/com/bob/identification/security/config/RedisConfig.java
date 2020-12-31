package com.bob.identification.security.config;

import com.bob.identification.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Redis配置类
 * Created by LittleBob on 2020/12/17.
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
