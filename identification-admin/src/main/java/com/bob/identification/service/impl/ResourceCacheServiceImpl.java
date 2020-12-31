package com.bob.identification.service.impl;

import com.bob.identification.authority.po.UmsResource;
import com.bob.identification.common.service.RedisService;
import com.bob.identification.service.ResourceCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LittleBob on 2020/12/21/021.
 */
@Service
public class ResourceCacheServiceImpl implements ResourceCacheService {

    @Autowired
    private RedisService redisService;

    @Value("${redis.database.authority.name}")
    private String REDIS_DATABASE_AUTHORITY;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.database.authority.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.database.authority.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        String key = REDIS_DATABASE_AUTHORITY + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResource>) redisService.get(key);
    }

    @Override
    public void setResourceList(Long adminId, List<UmsResource> resourceList) {
        String key = REDIS_DATABASE_AUTHORITY + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }
}
