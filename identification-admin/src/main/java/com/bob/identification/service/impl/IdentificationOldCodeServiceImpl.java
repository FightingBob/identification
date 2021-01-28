package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.common.api.DatabaseOption;
import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.util.MyStrUtil;
import com.bob.identification.common.api.QueryCodeResult;
import com.bob.identification.identification.mapper.OldCodeMapper;
import com.bob.identification.identification.po.OldBatch;
import com.bob.identification.identification.po.OldCode;
import com.bob.identification.service.IdentificationCodeService;
import com.bob.identification.service.IdentificationOldBatchService;
import com.bob.identification.service.IdentificationOldCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 旧防伪码管理实现类
 * Created by LittleBob on 2021/1/6/006.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_2")
public class IdentificationOldCodeServiceImpl implements IdentificationOldCodeService {

    @Autowired
    private OldCodeMapper oldCodeMapper;

    @Autowired
    private IdentificationOldBatchService oldBatchService;

    @Autowired
    private IdentificationCodeService codeService;

    @Override
    public QueryCodeResult query(String code) {
        OldBatch oldBatch = oldBatchService.getByPreThreeNumber(MyStrUtil.getPreThreeNumber(code));
        if (BeanUtil.isNotEmpty(oldBatch)) {
            QueryWrapper<OldCode> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("serial_number", code);
            queryWrapper.last("limit 1");
            OldCode oldCode = oldCodeMapper.selectOne(queryWrapper);
            if (BeanUtil.isNotEmpty(oldCode)) {
                QueryCode queryCode = new QueryCode();
                BeanUtil.copyProperties(oldCode, queryCode);
                queryCode.setIsReal(3);
                queryCode.setDatabaseOption(DatabaseOption.LAIKOU.getDatabaseOption());
                queryCode.setQueryTime(queryCode.getQueryTime() + 1);
                return codeService.setQueryCodeResult(queryCode);
            }
        }
        return null;
    }

    @Override
    public void update(QueryCode queryCode) {
        OldCode oldCode = new OldCode();
        oldCode.setId(queryCode.getId());
        oldCode.setQueryTime(queryCode.getQueryTime());
        oldCode.setStatus(queryCode.getStatus());
        oldCodeMapper.updateById(oldCode);
    }

    @Override
    public void updateStatus(QueryCode queryCode) {
        OldCode oldCode = new OldCode();
        oldCode.setId(queryCode.getId());
        oldCode.setStatus(1);
        oldCode.setQueryTime(0);
        oldCodeMapper.updateById(oldCode);
    }
}
