package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.common.api.DatabaseOptionUtil;
import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.identification.mapper.ConfigMapper;
import com.bob.identification.identification.po.Config;
import com.bob.identification.service.IdentificationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 防伪码提示管理接口实现类
 * Created by LittleBob on 2021/1/4/004.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_2")
public class IdentificationConfigServiceImpl implements IdentificationConfigService {

    private static final String ERROR_MESSAGE = "error_identity";
    private static final String FIRST_IDENTITY = "first_identity";
    private static final String IDENTITY = "n_identity";

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public String getErrorMessage(String code) {
        Config config = queryByCode(ERROR_MESSAGE);
        return StrUtil.format(config.getCodeValue(), code);
    }

    @Override
    public String getConfigByQueryCode(QueryCode queryCode) {
        if (queryCode.getStatus() > 0) {
            if (queryCode.getQueryTime() != 1) {
                if (queryCode.getQueryTime() > 5) {
                    return getErrorMessage(queryCode.getSerialNumber());
                }
                return getIdentity(queryCode);
            }
            return getFirstIdentity(queryCode);
        }
        return getErrorMessage(queryCode.getSerialNumber());
    }

    /**
     * 识别
     * @param queryCode 防伪码
     */
    private String getIdentity(QueryCode queryCode) {
        Config config = queryByCode(IDENTITY);
        return StrUtil.format(config.getCodeValue(), queryCode.getSerialNumber(), queryCode.getQueryTime());
    }

    /**
     * 第一次查询结果为真时
     * @param queryCode 防伪码
     * @return 查询结果
     */
    private String getFirstIdentity(QueryCode queryCode) {
        Config config = queryByCode(FIRST_IDENTITY);
        return StrUtil.format(config.getCodeValue(), queryCode.getSerialNumber(), DatabaseOptionUtil.getCodeNameByOption(queryCode.getDatabaseOption()));
    }

    /**
     * 根据代号查询提示
     * @param code 代号
     * @return 提示
     */
    private Config queryByCode(String code) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        queryWrapper.last("limit 1");
        Config config = configMapper.selectOne(queryWrapper);
        if (BeanUtil.isEmpty(config)) {
            Asserts.fail("提示管理数据缺失，请修复");
        }
        return config;
    }
}
