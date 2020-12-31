package com.bob.identification.service;

import com.bob.identification.authority.po.UmsResource;

import java.util.List;

/**
 * 后台资源缓存接口
 * Created by LittleBob on 2020/12/21/021.
 */
public interface ResourceCacheService {

    /**
     * 获取用户资源缓存
     * @param adminId 用户id
     * @return 用户资源
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 设置用户资源缓存
     * @param adminId 用户id
     * @param resourceList 用户资源
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
