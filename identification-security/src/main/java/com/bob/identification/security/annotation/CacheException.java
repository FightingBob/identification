package com.bob.identification.security.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解,有该注解的缓存方法会抛出异常
 * Created by LittleBob on 2020/12/17/017.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
