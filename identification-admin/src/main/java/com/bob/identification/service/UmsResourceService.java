package com.bob.identification.service;

import com.bob.identification.authority.po.UmsResource;
import com.bob.identification.common.api.CommonPage;

import java.util.List;

/**
 * 后台资源管理接口
 * Created by LittleBob on 2020/12/21/021.
 */
public interface UmsResourceService {

    /**
     *
     * @param adminId 用户id
     * @return 用户所拥有的资源
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 查询用户资源
     * @return 用户资源
     */
    List<UmsResource> listAll();

    /**
     * 添加资源
     * @param resource 资源
     * @return 添加状态
     */
    int create(UmsResource resource);

    /**
     * 查询后台资源
     * @param categoryId 资源分类id
     * @param nameKeyword 资源名称关键词
     * @param urlKeyword 地址关键词
     * @param pageSize 每页数量
     * @param pageNum 页号
     * @return 后台资源
     */
    CommonPage list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

    /**
     * 更新后台资源
     * @param id 资源id
     * @param resource 资源
     * @return 更新状态
     */
    int update(Long id, UmsResource resource);

    /**
     * 根据id获取资源详情
     * @param id 资源id
     * @return 资源
     */
    UmsResource getItem(Long id);

    /**
     * 删除资源
     * @param id 资源id
     * @return 删除状态
     */
    int delete(Long id);
}
