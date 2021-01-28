package com.bob.identification.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.bob.identification.common.service.RedisService;
import com.bob.identification.common.domain.VisitDto;
import com.bob.identification.common.domain.Visitor;
import com.bob.identification.service.IPCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * IP缓存管理实现类
 * Created by LittleBob on 2021/1/4/004.
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
    public void addVisitor(Visitor visitor) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_VISITOR + ":" + visitor.getIpAddress();
        redisService.set(key, visitor, REDIS_EXPIRE_TIME);
    }

    @Override
    public Visitor getVisitor(String ipAddress) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_VISITOR + ":"  + ipAddress;
        return (Visitor) redisService.get(key);
    }

    @Override
    public VisitDto getVisitRecord() {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_VISIT_RECORD + ":" + DateUtil.today();
        return (VisitDto) redisService.get(key);
    }

    @Override
    public void addVisitRecord(VisitDto visit) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_VISIT_RECORD + ":" + DateUtil.today();
        redisService.set(key, visit, REDIS_EXPIRE_TIME);
    }

    @Override
    public void deleteYesterdayVisitor() {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_VISITOR  + ":" + "*";
        Set<String> keys = redisTemplate.keys(key);
        for (String str : new ArrayList<>(keys)) {
            redisService.del(str);
        }
    }

    @Override
    public void deleteYesterdayVisitRecord() {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_VISIT_RECORD + ":" + DateUtil.formatDate(DateUtil.yesterday());
        redisService.del(key);
    }

    @Override
    public VisitDto getYesterdayVisit() {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_VISIT_RECORD + ":" + DateUtil.formatDate(DateUtil.yesterday());
        return (VisitDto) redisService.get(key);
    }

    @Override
    public Visitor getYesterdayBestVisitor() {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_VISITOR  + ":" + "*";
        Set<String> keys = redisTemplate.keys(key);
        List<Visitor> list = new ArrayList<>();
        for (String str : new ArrayList<>(keys)) {
            list.add((Visitor) redisService.get(str));
        }
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        Optional<Visitor> visitorOptional = list.stream()
                .max(Comparator.comparingInt(Visitor::getVisitTimes));
        return visitorOptional.get();
    }
}
