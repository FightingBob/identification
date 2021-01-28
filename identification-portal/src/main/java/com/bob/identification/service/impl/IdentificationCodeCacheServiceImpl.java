package com.bob.identification.service.impl;

import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.service.RedisService;
import com.bob.identification.service.IdentificationCodeCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 防伪码缓存管理实现类
 * Created by LittleBob on 2021/1/4/004.
 */
@Service
public class IdentificationCodeCacheServiceImpl implements IdentificationCodeCacheService {

    @Autowired
    private RedisService redisService;

    @Value("${redis.database.identification.name}")
    private String REDIS_DATABASE_IDENTIFICATION;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE_TIME;
    @Value("${redis.database.identification.key.codeList}")
    private String REDIS_KEY_CODE_LIST;
    @Value("${redis.database.identification.key.whiteCode}")
    private String REDIS_KEY_WHITE_CODE;
    @Value("${redis.database.identification.key.blackCode}")
    private String REDIS_KEY_BLACK_CODE;

    @Override
    public QueryCode queryCodeFromWhiteList(String code) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_WHITE_CODE + ":" + code;
        return (QueryCode) redisService.get(key);
    }

    @Override
    public QueryCode queryCodeFromBlackList(String code) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_BLACK_CODE + ":" + code;
        return (QueryCode) redisService.get(key);
    }

    @Override
    public void addBlackCode(QueryCode queryCode) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_BLACK_CODE + ":" + queryCode.getSerialNumber();
        redisService.set(key, queryCode, REDIS_EXPIRE_TIME);
    }

    @Override
    public void deleteWhiteCode(QueryCode queryCode) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_WHITE_CODE + ":" + queryCode.getSerialNumber();
        redisService.del(key);
    }

    @Override
    public void addWhiteCode(QueryCode queryCode) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_WHITE_CODE + ":" + queryCode.getSerialNumber();
        redisService.set(key, queryCode, REDIS_EXPIRE_TIME);
    }

    @Override
    public void addBlackCode(String code) {
        QueryCode queryCode = new QueryCode();
        queryCode.setSerialNumber(code);
        queryCode.setStatus(0);
        queryCode.setQueryTime(1);
        addBlackCode(queryCode);
    }
}
