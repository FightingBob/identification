package com.bob.identification.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.identification.mapper.OldBatchMapper;
import com.bob.identification.identification.po.OldBatch;
import com.bob.identification.service.IdentificationOldBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 旧防伪批次管理实现类
 * Created by LittleBob on 2021/1/6/006.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_2")
public class IdentificationOldBatchServiceImpl implements IdentificationOldBatchService {

    @Autowired
    private OldBatchMapper oldBatchMapper;

    @Override
    public OldBatch getByPreThreeNumber(String preThreeNumber) {
        QueryWrapper<OldBatch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pre_three_number", preThreeNumber);
        queryWrapper.last("limit 1");
        return oldBatchMapper.selectOne(queryWrapper);
    }
}
