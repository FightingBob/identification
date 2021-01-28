package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.bob.identification.common.domain.VisitDto;
import com.bob.identification.common.service.RedisService;
import com.bob.identification.service.IPCacheService;
import com.bob.identification.service.IdentificationVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 前台访问管理接口实现类
 * Created by LittleBob on 2020/12/29/029.
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class IdentificationVisitServiceImpl implements IdentificationVisitService {

    @Autowired
    private IPCacheService ipCacheService;

    @Override
    public Integer getVisitNumber() {
        VisitDto visitDto = ipCacheService.getTodayVisitRecord();
        return BeanUtil.isNotEmpty(visitDto) ? visitDto.getVisitNumber() : 0;
    }


}
