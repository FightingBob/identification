package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.identification.authority.mapper.UmsResourceMapper;
import com.bob.identification.authority.po.UmsResource;
import com.bob.identification.common.api.CommonPage;
import com.bob.identification.dao.AdminResourceDao;
import com.bob.identification.service.ResourceCacheService;
import com.bob.identification.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台资源管理实现类
 * Created by LittleBob on 2020/12/21/021.
 */
@Service
@DS("master")
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private AdminResourceDao adminResourceDao;

    @Autowired
    private ResourceCacheService resourceCacheService;

    @Autowired
    private UmsResourceMapper resourceMapper;

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList = resourceCacheService.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = adminResourceDao.getResourceListByAdminId(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            resourceCacheService.setResourceList(adminId, resourceList);
        }
        return resourceList;
    }

    @Override
    public List<UmsResource> listAll() {
        return resourceMapper.selectList(null);
    }

    @Override
    public int create(UmsResource resource) {
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateTime(LocalDateTime.now());
        return resourceMapper.insert(resource);
    }

    @Override
    public CommonPage list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        IPage<UmsResource> resourcePage = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsResource> queryWrapper = new QueryWrapper<>();
        if (BeanUtil.isNotEmpty(categoryId)) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (StrUtil.isNotBlank(nameKeyword)) {
            queryWrapper.and(wrapper->wrapper.like("name", nameKeyword));

        }
        if (StrUtil.isNotBlank(urlKeyword)) {
            queryWrapper.and(wrapper->wrapper.like("url", nameKeyword));
        }
        List<UmsResource> list = resourceMapper.selectPage(resourcePage, queryWrapper).getRecords();
        long total = resourcePage.getTotal();
        return CommonPage.restPage(list, total, pageNum, pageSize);
    }

    @Override
    public int update(Long id, UmsResource resource) {
        resource.setId(id);
        resource.setUpdateTime(LocalDateTime.now());
        return resourceMapper.updateById(resource);
    }

    @Override
    public UmsResource getItem(Long id) {
        return resourceMapper.selectById(id);
    }

    @Override
    public int delete(Long id) {
        return resourceMapper.deleteById(id);
    }
}
