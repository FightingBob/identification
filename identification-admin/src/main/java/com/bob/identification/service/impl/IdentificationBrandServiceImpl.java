package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.dto.AddBrandParam;
import com.bob.identification.identification.mapper.BrandMapper;
import com.bob.identification.identification.po.Brand;
import com.bob.identification.service.IdentificationBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 防伪品牌管理实现类
 * Created by LittleBob on 2020/12/29/029.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_2")
public class IdentificationBrandServiceImpl implements IdentificationBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> list() {
        return brandMapper.selectList(null);
    }

    @Override
    public int add(AddBrandParam addBrandParam) {
        if (hasPreNumber(addBrandParam.getPreNumber())) {
            Asserts.fail("防伪码前缀已被使用");
        }
        return addBrand(addBrandParam);
    }

    @Override
    public int update(Integer brandId, Brand brand) {
        brand.setUpdateTime(LocalDateTime.now());
        brand.setId(brandId);
        return brandMapper.updateById(brand);
    }

    @Override
    public int delete(Integer brandId) {
        return brandMapper.deleteById(brandId);
    }

    @Override
    public Brand getByPreNumber(Integer preNumber) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pre_number", preNumber);
        queryWrapper.last("limit 1");
        return brandMapper.selectOne(queryWrapper);
    }

    @Override
    public Brand getById(Integer brandId) {
        return brandMapper.selectById(brandId);
    }

    /**
     * 新增品牌
     *
     * @param addBrandParam 参数
     * @return 新增状态
     */
    private int addBrand(AddBrandParam addBrandParam) {
        Brand brand = new Brand();
        BeanUtil.copyProperties(addBrandParam, brand);
        brand.setCreateTime(LocalDateTime.now());
        brand.setUpdateTime(LocalDateTime.now());
        return brandMapper.insert(brand);
    }

    /**
     * 前缀是否已经被使用
     *
     * @param preNumber 前缀
     * @return 是否
     */
    private boolean hasPreNumber(String preNumber) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pre_number", preNumber);
        queryWrapper.last("limit 1");
        return BeanUtil.isNotEmpty(brandMapper.selectOne(queryWrapper));
    }
}
