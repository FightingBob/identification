package com.bob.identification.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.authority.mapper.UmsResourceCategoryMapper;
import com.bob.identification.authority.po.UmsResourceCategory;
import com.bob.identification.service.UmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台资源管理实现类
 * Created by LittleBob on 2020/12/26/026.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("master")
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {

    @Autowired
    private UmsResourceCategoryMapper resourceCategoryMapper;

    @Override
    public int create(UmsResourceCategory resourceCategory) {
        resourceCategory.setCreateTime(LocalDateTime.now());
        resourceCategory.setUpdateTime(LocalDateTime.now());
        return resourceCategoryMapper.insert(resourceCategory);
    }

    @Override
    public List<UmsResourceCategory> listAll() {
        QueryWrapper<UmsResourceCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        return resourceCategoryMapper.selectList(queryWrapper);
    }

    @Override
    public int update(Long id, UmsResourceCategory resourceCategory) {
        resourceCategory.setId(id);
        resourceCategory.setUpdateTime(LocalDateTime.now());
        return resourceCategoryMapper.updateById(resourceCategory);
    }

    @Override
    public int delete(Long id) {
        return resourceCategoryMapper.deleteById(id);
    }
}
