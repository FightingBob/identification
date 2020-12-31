package com.bob.identification.service;

import com.bob.identification.authority.po.UmsResourceCategory;

import java.util.List;

/**
 * 后台资源分类管理接口
 * Created by LittleBob on 2020/12/26/026.
 */
public interface UmsResourceCategoryService {


    int create(UmsResourceCategory resourceCategory);

    /**
     * 获取后台资源分类列表
     * @return 后台资源分类列表
     */
    List<UmsResourceCategory> listAll();

    /**
     * 更新后台资源分类
     * @param id 资源分类id
     * @param resourceCategory 资源分类
     * @return 更新状态
     */
    int update(Long id, UmsResourceCategory resourceCategory);

    /**
     * 删除资源分类
     * @param id 资源分类id
     * @return 删除状态
     */
    int delete(Long id);
}
