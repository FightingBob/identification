package com.bob.identification.service.impl;

import com.bob.identification.service.UmsMemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by LittleBob on 2021/1/4/004.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
