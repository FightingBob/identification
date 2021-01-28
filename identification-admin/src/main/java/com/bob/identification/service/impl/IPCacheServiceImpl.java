package com.bob.identification.service.impl;

import cn.hutool.core.date.DateUtil;
import com.bob.identification.common.domain.VisitDto;
import com.bob.identification.common.service.RedisService;
import com.bob.identification.service.IPCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * IP地址缓存管理实现类
 * Created by LittleBob on 2021/1/9/009.
 */
@Service
public class IPCacheServiceImpl implements IPCacheService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${redis.database.identification.name}")
    private String REDIS_DATABASE_IDENTIFICATION;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE_TIME;
    @Value("${redis.database.identification.key.visitor}")
    private String REDIS_KEY_VISITOR;
    @Value("${redis.database.identification.key.visitRecord}")
    private String REDIS_KEY_VISIT_RECORD;

    @Override
    public VisitDto getTodayVisitRecord() {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_VISIT_RECORD + ":" + DateUtil.today();
        return (VisitDto) redisService.get(key);
    }
}
