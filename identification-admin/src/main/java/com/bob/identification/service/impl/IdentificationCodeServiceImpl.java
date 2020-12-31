package com.bob.identification.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.identification.bo.DynamicTableTreadLocal;
import com.bob.identification.common.api.CommonStatus;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.common.util.ListPageUtil;
import com.bob.identification.common.util.NumberGeneratorUtil;
import com.bob.identification.dao.CodePreDao;
import com.bob.identification.identification.mapper.CodePreMapper;
import com.bob.identification.identification.po.Batch;
import com.bob.identification.identification.po.CodePre;
import com.bob.identification.service.IdentificationCodeCacheService;
import com.bob.identification.service.IdentificationCodeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 防伪码管理实现类o
 * Created by LittleBob on 2020/12/30/030.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_1")
public class IdentificationCodeServiceImpl implements IdentificationCodeService {

    private static final int BATCH_NUMBER = 1000;

    private static final int CODE_SUFFIX_LENGTH = 12;

    private static final int PAGE_SIZE = 200000;

    @Autowired
    private CodePreMapper codePreMapper;

    @Autowired
    private CodePreDao codePreDao;

    @Autowired
    private IdentificationCodeCacheService codeCacheService;

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
    @DS("slave_1")
    public void laikouAddCode(String suffix) {
        addCode(suffix);
    }

    @Override
    @DS("slave_1")
    public List<String> laikouGetListByBatchId(Batch batch) {
        return getListByBatchId(batch);
    }

    @Override
    @DS("slave_1")
    public int laikouAddList(Batch batch) {
        addList(batch, CODE_SUFFIX_LENGTH);
        return CommonStatus.SUCCESS.getStatus();
    }

    @Override
    @DS("slave_3")
    public int fenyiAddList(Batch batch) {
        addList(batch, CODE_SUFFIX_LENGTH);
        return CommonStatus.SUCCESS.getStatus();
    }



    @Override
    @DS("slave_3")
    public List<String> fenyiGetListByBatchId(Batch batch) {
        return getListByBatchId(batch);
    }

    @Override
    @DS("slave_3")
    public void fenyiAddCode(String suffix) {
        addCode(suffix);
    }

    /**
     * 添加防伪码
     * @param batch 批次
     * @param length 防伪码长度
     */
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
        }
        codeCacheService.addList(batch.getId(), batch.getPreThreeNumber(), list);
    }

    /**
     * 根据批次id获取防伪码
     * @param batch 批次
     * @return 防伪码
     */
    public List<String> getListByBatchId(Batch batch) {
        DynamicTableTreadLocal.INSTANCE.setTableName(batch.getSecondNumber().toString());
        List<CodePre> codePreList;
        List<CodePre> resultList = new ArrayList<>();
        int pageNum = 0;
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