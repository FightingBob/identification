package com.bob.identification.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.identification.mapper.BatchMapper;
import com.bob.identification.identification.po.Batch;
import com.bob.identification.service.IdentificationBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LittleBob on 2021/1/4/004.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_2")
public class IdentificationBatchServiceImpl implements IdentificationBatchService {

    @Autowired
    private BatchMapper batchMapper;

    @Override
    public Batch getByPreThreeNumber(String preThreeNumber) {
        QueryWrapper<Batch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pre_three_number", preThreeNumber);
        queryWrapper.last("limit 1");
        return batchMapper.selectOne(queryWrapper);
    }
}
