package com.bob.identification.service.impl;

import com.bob.identification.common.service.RedisService;
import com.bob.identification.service.IdentificationCodeCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LittleBob on 2020/12/31/031.
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

    @Override
    public void addList(Integer batchId, String preThreeNumber, List<String> list) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_CODE_LIST + ":" + batchId + ":" + preThreeNumber;
        redisService.set(key, list, REDIS_EXPIRE_TIME);
    }

    @Override
    public List<String> getList(Integer batchId, String preThreeNumber) {
        String key = REDIS_DATABASE_IDENTIFICATION + ":" + REDIS_KEY_CODE_LIST + ":" + batchId + ":" + preThreeNumber;
        return (List<String>) redisService.get(key);
    }
}
