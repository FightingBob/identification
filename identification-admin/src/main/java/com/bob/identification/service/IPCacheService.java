package com.bob.identification.service;

import com.bob.identification.common.domain.VisitDto;

/**
 * IP地址缓存管理接口
 * Created by LittleBob on 2021/1/9/009.
 */
public interface IPCacheService {

    /**
     * 获取今日访问记录
     * @return 访问记录
     */
    VisitDto getTodayVisitRecord();
}
