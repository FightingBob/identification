package com.bob.identification.config;

import com.bob.identification.security.config.SecurityConfig;
import com.bob.identification.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * identification-security 模块相关配置
 * Created by LittleBob on 2021/1/4/004.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class IdentificationSecurityConfig extends SecurityConfig {

    @Autowired
    private UmsMemberService memberService;

    @Bean
    public UserDetailsService userDetailsService() {
        // 获取登录用户信息
        return username -> memberService.loadUserByUsername(username);
    }
}
