package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.bo.DynamicTableTreadLocal;
import com.bob.identification.common.api.DatabaseOption;
import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.api.QueryCodeResult;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.common.util.MyStrUtil;
import com.bob.identification.identification.mapper.CodePreMapper;
import com.bob.identification.identification.po.CodePre;
import com.bob.identification.service.IdentificationCodeCacheService;
import com.bob.identification.service.IdentificationCodeService;
import com.bob.identification.service.IdentificationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 防伪码管理实现类
 * Created by LittleBob on 2021/1/4/004.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class IdentificationCodeServiceImpl implements IdentificationCodeService {

    private static final Integer LIMIT_QUERY_TIME = 5;

    @Autowired
    private CodePreMapper codePreMapper;

    @Autowired
    private IdentificationCodeCacheService codeCacheService;

    @Autowired
    private IdentificationConfigService configService;

    @Override
    @DS("slave_1")
    public void laikouUpdate(QueryCode queryCode) {
        updateCode(queryCode);
    }

    @Override
    @DS("slave_1")
    public QueryCodeResult laikouQuery(String code, Integer secondNumber) {
        QueryCode queryCode = codePreToQueryCode(query(code, secondNumber), secondNumber);
        if (BeanUtil.isEmpty(queryCode)) {
            return null;
        }
        queryCode.setDatabaseOption(DatabaseOption.LAIKOU.getDatabaseOption());
        updateCode(queryCode);
        return setQueryCodeResult(queryCode);
    }

    @Override
    @DS("slave_3")
    public void fenyiUpdate(QueryCode queryCode) {
        updateCode(queryCode);
    }

    @Override
    @DS("slave_3")
    public QueryCodeResult fenyiQuery(String code, Integer secondNumber) {
        QueryCode queryCode = codePreToQueryCode(query(code, secondNumber), secondNumber);
        if (BeanUtil.isEmpty(queryCode)) {
            return null;
        }
        queryCode.setDatabaseOption(DatabaseOption.FENYI.getDatabaseOption());
        updateCode(queryCode);
        return setQueryCodeResult(queryCode);
    }

    @Override
    @DS("slave_4")
    public void kaibinUpdate(QueryCode queryCode) {
        updateCode(queryCode);
    }

    @Override
    @DS("slave_4")
    public QueryCodeResult kaibinQuery(String code, Integer secondNumber) {
        QueryCode queryCode = codePreToQueryCode(query(code, secondNumber), secondNumber);
        if (BeanUtil.isEmpty(queryCode)) {
            return null;
        }
        queryCode.setDatabaseOption(DatabaseOption.KAIBIN.getDatabaseOption());
        updateCode(queryCode);
        return setQueryCodeResult(queryCode);
    }

    @Override
    @DS("slave_5")
    public void yaladysUpdate(QueryCode queryCode) {
        updateCode(queryCode);
    }

    @Override
    @DS("slave_5")
    public QueryCodeResult yaladysQuery(String code, Integer secondNumber) {
        QueryCode queryCode = codePreToQueryCode(query(code, secondNumber), secondNumber);
        if (BeanUtil.isEmpty(queryCode)) {
            return null;
        }
        queryCode.setDatabaseOption(DatabaseOption.YALADYS.getDatabaseOption());
        updateCode(queryCode);
        return setQueryCodeResult(queryCode);
    }

    @Override
    public void validateCode(String code) {
        if (MyStrUtil.validateSerialNumber(code)) {
            Asserts.fail("该防伪码不符合15位全数字的格式，请检查后重新输入");
        }
    }

    /**
     * 查询防伪码
     *
     * @param code 防伪码
     * @return 防伪码
     */
    private CodePre query(String code, Integer secondNumber) {
        DynamicTableTreadLocal.INSTANCE.setTableName(secondNumber.toString());
        QueryWrapper<CodePre> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("serial_number", code);
        queryWrapper.last("limit 1");
        return codePreMapper.selectOne(queryWrapper);
    }

    /**
     * 对象转换
     * @param codePre 防伪码
     * @param secondNumber 防伪码前缀第二个数字
     * @return 防伪码
     */
    private QueryCode codePreToQueryCode(CodePre codePre, Integer secondNumber) {
        if (BeanUtil.isEmpty(codePre)) {
            return null;
        }
        QueryCode queryCode = new QueryCode();
        BeanUtil.copyProperties(codePre, queryCode);
        queryCode.setTableOption(secondNumber);
        queryCode.setIsReal(1);
        queryCode.setQueryTime(queryCode.getQueryTime() + 1);
        return queryCode;
    }

    /**
     * 更新防伪码查询次数
     *
     * @param queryCode 防伪码
     */
    private void updateCode(QueryCode queryCode) {
        DynamicTableTreadLocal.INSTANCE.setTableName(queryCode.getTableOption().toString());
        CodePre codePre = new CodePre();
        BeanUtil.copyProperties(queryCode, codePre);
        codePreMapper.updateById(codePre);
    }

    @Override
    public QueryCodeResult setQueryCodeResult(QueryCode queryCode) {
        QueryCodeResult queryCodeResult = new QueryCodeResult();
        queryCodeResult.setQueryCode(queryCode);
        if (queryCode.getStatus() == 0) {
            codeCacheService.addBlackCode(queryCode);
            queryCodeResult.setResult(configService.getErrorMessage(queryCode.getSerialNumber()));
            return queryCodeResult;
        }
        if (queryCode.getQueryTime() > LIMIT_QUERY_TIME) {
            queryCode.setStatus(0);
            codeCacheService.deleteWhiteCode(queryCode);
            codeCacheService.addBlackCode(queryCode);
            queryCodeResult.getQueryCode().setStatus(queryCode.getStatus());
            queryCodeResult.setResult(configService.getErrorMessage(queryCode.getSerialNumber()));
            return queryCodeResult;
        }
        queryCodeResult.setResult(configService.getConfigByQueryCode(queryCode));
        codeCacheService.addWhiteCode(queryCode);
        return queryCodeResult;
    }

    @Override
    public QueryCodeResult getErrorQueryCodeResult(String code) {
        QueryCode queryCode = new QueryCode();
        queryCode.setSerialNumber(code);
        queryCode.setIsReal(0);
        queryCode.setStatus(0);
        queryCode.setQueryTime(1);
        QueryCodeResult queryCodeResult = new QueryCodeResult();
        queryCodeResult.setQueryCode(queryCode);
        queryCodeResult.setResult(configService.getErrorMessage(code));
        codeCacheService.addBlackCode(queryCode);
        return queryCodeResult;
    }

    @Override
    public QueryCodeResult queryCodeFromCache(String code) {
        QueryCode queryCode = codeCacheService.queryCodeFromWhiteList(code);
        if (BeanUtil.isNotEmpty(queryCode)) {
            queryCode.setQueryTime(queryCode.getQueryTime() + 1);
            return setQueryCodeResult(queryCode);
        }
        queryCode = codeCacheService.queryCodeFromBlackList(code);
        if (BeanUtil.isNotEmpty(queryCode)) {
            queryCode.setQueryTime(queryCode.getQueryTime() + 1);
            return setQueryCodeResult(queryCode);
        }
        return null;
    }
}
