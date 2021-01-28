package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.common.api.DatabaseOptionUtil;
import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.dto.CustomCodeAddParam;
import com.bob.identification.common.api.QueryCodeResult;
import com.bob.identification.identification.mapper.CustomCodeMapper;
import com.bob.identification.identification.po.Brand;
import com.bob.identification.identification.po.CustomCode;
import com.bob.identification.service.IdentificationBrandService;
import com.bob.identification.service.IdentificationCodeService;
import com.bob.identification.service.IdentificationCustomCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 自定义防伪码管理实现类
 * Created by LittleBob on 2021/1/3/003.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_2")
public class IdentificationCustomCodeServiceImpl implements IdentificationCustomCodeService {

    @Autowired
    private CustomCodeMapper customCodeMapper;

    @Autowired
    private IdentificationBrandService brandService;

    @Autowired
    private IdentificationCodeService codeService;

    @Override
    public QueryCode add(CustomCodeAddParam customCodeAddParam) {
        CustomCode customCode = new CustomCode();
        customCode.setCreateTime(LocalDateTime.now());
        customCode.setQueryTime(0);
        customCode.setSerialNumber(customCodeAddParam.getSerialNumber());
        customCode.setStatus(1);
        customCode.setBrandId(customCodeAddParam.getBrandId());
        customCodeMapper.insert(customCode);
        if (BeanUtil.isEmpty(customCode.getId())) {
            Asserts.fail("防伪码添加异常，请修复");
        }
        Brand brand = brandService.getById(customCodeAddParam.getBrandId());
        QueryCode queryCode = new QueryCode();
        BeanUtil.copyProperties(customCode, queryCode);
        queryCode.setIsReal(2);
        queryCode.setDatabaseOption(DatabaseOptionUtil.getOptionByCodeName(brand.getBrandCode()));
        return queryCode;
    }

    @Override
    public void updateStatus(QueryCode queryCode) {
        CustomCode customCode = new CustomCode();
        customCode.setId(queryCode.getId());
        customCode.setStatus(1);
        customCode.setQueryTime(0);
        customCodeMapper.updateById(customCode);
    }

    @Override
    public QueryCodeResult query(String code) {
        QueryWrapper<CustomCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("serial_number", code);
        queryWrapper.last("limit 1");
        CustomCode customCode = customCodeMapper.selectOne(queryWrapper);
        if (BeanUtil.isNotEmpty(customCode)) {
            QueryCode queryCode = new QueryCode();
            BeanUtil.copyProperties(customCode, queryCode);
            queryCode.setIsReal(2);
            queryCode.setQueryTime(queryCode.getQueryTime() + 1);
            Brand brand = brandService.getById(customCode.getBrandId());
            queryCode.setDatabaseOption(DatabaseOptionUtil.getOptionByCodeName(brand.getBrandCode()));
            queryCode.setQueryTime(queryCode.getQueryTime() + 1);
            return codeService.setQueryCodeResult(queryCode);
        }
        return null;
    }

    @Override
    public void update(QueryCode queryCode) {
        CustomCode customCode = new CustomCode();
        customCode.setId(queryCode.getId());
        customCode.setQueryTime(queryCode.getQueryTime());
        customCode.setStatus(queryCode.getStatus());
        customCodeMapper.updateById(customCode);
    }
}
