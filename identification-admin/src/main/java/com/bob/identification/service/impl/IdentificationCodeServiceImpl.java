package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.identification.bo.DynamicTableTreadLocal;
import com.bob.identification.common.api.CommonStatus;
import com.bob.identification.common.api.DatabaseOption;
import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.common.util.ListPageUtil;
import com.bob.identification.common.util.MyStrUtil;
import com.bob.identification.common.util.NumberGeneratorUtil;
import com.bob.identification.dao.CodePreDao;
import com.bob.identification.common.api.QueryCodeResult;
import com.bob.identification.identification.mapper.CodePreMapper;
import com.bob.identification.identification.po.Batch;
import com.bob.identification.identification.po.CodePre;
import com.bob.identification.service.IdentificationCodeCacheService;
import com.bob.identification.service.IdentificationCodeService;
import com.bob.identification.service.IdentificationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 防伪码管理实现类o
 * Created by LittleBob on 2020/12/30/030.
 */
@Service
@DS("slave_1")
public class IdentificationCodeServiceImpl implements IdentificationCodeService {

    private static final int BATCH_NUMBER = 1000;

    private static final int PAGE_SIZE = 200000;

    private static final Integer LIMIT_QUERY_TIME = 5;

    private static final int CODE_SUFFIX_LENGTH = 12;

    @Autowired
    private CodePreMapper codePreMapper;

    @Autowired
    private CodePreDao codePreDao;

    @Autowired
    private IdentificationCodeCacheService codeCacheService;

    @Autowired
    private IdentificationConfigService configService;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_1")
    public void laikouAddCode(String suffix) {
        addCode(suffix);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_1")
    public List<String> laikouGetListByBatchId(Batch batch) {
        return getListByBatchId(batch);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_1")
    public int laikouAddList(Batch batch) {
        addList(batch, CODE_SUFFIX_LENGTH);
        return CommonStatus.SUCCESS.getStatus();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_1")
    public void laikouUpdate(QueryCode queryCode) {
        updateCode(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
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

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_1")
    public void laikouUpdateStatus(QueryCode queryCode) {
        updateStatus(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_3")
    public void fenyiUpdateStatus(QueryCode queryCode) {
        updateStatus(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_3")
    public QueryCodeResult fenyiQuery(String code, Integer secondNumber) {
        QueryCode queryCode = codePreToQueryCode(query(code, secondNumber), secondNumber);
        if (BeanUtil.isEmpty(queryCode)) {
            return null;
        }
        queryCode.setDatabaseOption(DatabaseOption.FENYI.getDatabaseOption());
        queryCode.setQueryTime(queryCode.getQueryTime() + 1);
        updateCode(queryCode);
        return setQueryCodeResult(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_3")
    public void fenyiUpdate(QueryCode queryCode) {
        updateCode(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_3")
    public int fenyiAddList(Batch batch) {
        addList(batch, CODE_SUFFIX_LENGTH);
        return CommonStatus.SUCCESS.getStatus();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_3")
    public List<String> fenyiGetListByBatchId(Batch batch) {
        return getListByBatchId(batch);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_3")
    public void fenyiAddCode(String suffix) {
        addCode(suffix);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_4")
    public List<String> kaibinGetListByBatchId(Batch batch) {
        return getListByBatchId(batch);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_4")
    public int kaibinAddList(Batch batch) {
        addList(batch, CODE_SUFFIX_LENGTH);
        return CommonStatus.SUCCESS.getStatus();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_4")
    public void kaibinUpdateStatus(QueryCode queryCode) {
        updateStatus(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_4")
    public void kaibinUpdate(QueryCode queryCode) {
        updateCode(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_4")
    public QueryCodeResult kaibinQuery(String code, Integer secondNumber) {
        QueryCode queryCode = codePreToQueryCode(query(code, secondNumber), secondNumber);
        if (BeanUtil.isEmpty(queryCode)) {
            return null;
        }
        queryCode.setDatabaseOption(DatabaseOption.KAIBIN.getDatabaseOption());
        queryCode.setQueryTime(queryCode.getQueryTime() + 1);
        updateCode(queryCode);
        return setQueryCodeResult(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_5")
    public List<String> yaladysGetListByBatchId(Batch batch) {
        return getListByBatchId(batch);
    }

    @Override
    public void deleteCacheCodeByBatchId(Long batchId) {
        codeCacheService.deleteListByBatchId(batchId);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_5")
    public int yaladysAddList(Batch batch) {
        addList(batch, CODE_SUFFIX_LENGTH);
        return CommonStatus.SUCCESS.getStatus();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_5")
    public void yaladysUpdateStatus(QueryCode queryCode) {
        updateStatus(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_5")
    public QueryCodeResult yaladysQuery(String code, Integer secondNumber) {
        QueryCode queryCode = codePreToQueryCode(query(code, secondNumber), secondNumber);
        if (BeanUtil.isEmpty(queryCode)) {
            return null;
        }
        queryCode.setDatabaseOption(DatabaseOption.YALADYS.getDatabaseOption());
        updateCode(queryCode);
        queryCode.setQueryTime(queryCode.getQueryTime() + 1);
        return setQueryCodeResult(queryCode);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("slave_5")
    public void yaladysUpdate(QueryCode queryCode) {
        updateCode(queryCode);
    }

    @Override
    public void validateCode(String code) {
        if (MyStrUtil.validateSerialNumber(code)) {
            Asserts.fail("该防伪码不符合15位全数字的格式，请检查后重新输入");
        }
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

    /**
     * 添加防伪码
     *
     * @param suffix 数据表后缀
     */
    private void addCode(String suffix) {
        DynamicTableTreadLocal.INSTANCE.setTableName(suffix);
        CodePre codePre = new CodePre();
        codePre.setQueryTime(Integer.parseInt(suffix));
        codePre.setSerialNumber(RandomUtil.randomNumbers(15));
        codePre.setStatus(1);
        codePre.setBatchId(1);
        codePreMapper.insert(codePre);
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

    /**
     * 对象转换
     *
     * @param codePre      防伪码
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
     * 更新防伪码状态，次数归零
     *
     * @param queryCode 防伪码
     */
    private void updateStatus(QueryCode queryCode) {
        DynamicTableTreadLocal.INSTANCE.setTableName(queryCode.getTableOption().toString());
        CodePre codePre = new CodePre();
        codePre.setId(queryCode.getId());
        codePre.setStatus(1);
        codePre.setQueryTime(0);
        codePreMapper.updateById(codePre);
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

    private void addList(Batch batch, Integer length) {
        List<String> list = NumberGeneratorUtil.generateRandomNumberList(batch.getPreThreeNumber(), length, batch.getDemand());
        List<CodePre> codePreList = new ArrayList<>();
        for (int i = 0; i < batch.getDemand(); i++) {
            CodePre codePre = new CodePre();
            codePre.setBatchId(batch.getId());
            codePre.setStatus(1);
            codePre.setSerialNumber(list.get(i));
            codePre.setQueryTime(0);
            codePreList.add(codePre);
        }
        String tableName = "code_pre" + batch.getSecondNumber();
        for (int page = 1; (page - 1) * BATCH_NUMBER < list.size(); page++) {
            codePreDao.batchInsert(ListPageUtil.getPageResult(codePreList, page, BATCH_NUMBER), tableName);
            System.out.println("第" + page + "页");
        }
        codeCacheService.addList(batch.getId(), batch.getPreThreeNumber(), list);
    }

    /**
     * 根据批次id获取防伪码
     *
     * @param batch 批次
     * @return 防伪码
     */
    private List<String> getListByBatchId(Batch batch) {
        List<CodePre> codePreList;
        List<CodePre> resultList = new ArrayList<>();
        int pageNum = 0;
        DynamicTableTreadLocal.INSTANCE.setTableName(batch.getSecondNumber().toString());
        do {
            pageNum++;
            IPage<CodePre> codePreIPage = new Page<>(pageNum, PAGE_SIZE);
            QueryWrapper<CodePre> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("batch_id", batch.getId());
            codePreList = codePreMapper.selectPage(codePreIPage, queryWrapper).getRecords();
            resultList.addAll(codePreList);
        } while (CollUtil.isNotEmpty(codePreList));

        if (CollUtil.isEmpty(resultList)) {
            Asserts.fail("该批次不存在");
        }
        List<String> codeList = resultList.stream().map(CodePre::getSerialNumber).collect(Collectors.toList());
        codeCacheService.addList(batch.getId(), batch.getPreThreeNumber(), codeList);
        return codeList;
    }


}