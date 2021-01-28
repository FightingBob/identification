package com.bob.identification.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.identification.mapper.BrandMapper;
import com.bob.identification.identification.po.Brand;
import com.bob.identification.service.IdentificationBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 防伪品牌管理实现类
 * Created by LittleBob on 2021/1/4/004.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_2")
public class IdentificationBrandServiceImpl implements IdentificationBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Brand getById(Integer brandId) {
        return brandMapper.selectById(brandId);
    }

    @Override
    public Brand getByPreNumber(Integer preNumber) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pre_number", preNumber);
        queryWrapper.last("limit 1");
        return brandMapper.selectOne(queryWrapper);
    }
}
