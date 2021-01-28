package com.bob.identification.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户管理接口
 * Created by LittleBob on 2021/1/4/004.
 */
public interface UmsMemberService {

    /**
     * 获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserDetails loadUserByUsername(String username);
}
