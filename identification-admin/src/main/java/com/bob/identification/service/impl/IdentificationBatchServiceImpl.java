package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.identification.common.api.CommonPage;
import com.bob.identification.common.api.CommonStatus;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.common.util.ExportTxtUtil;
import com.bob.identification.common.util.ListPageUtil;
import com.bob.identification.common.util.MyIdUtil;
import com.bob.identification.common.util.NumberGeneratorUtil;
import com.bob.identification.dto.AddBatchParam;
import com.bob.identification.identification.mapper.BatchMapper;
import com.bob.identification.identification.po.Batch;
import com.bob.identification.identification.po.Brand;
import com.bob.identification.identification.po.CodePre;
import com.bob.identification.service.IdentificationBatchService;
import com.bob.identification.service.IdentificationBrandService;
import com.bob.identification.service.IdentificationCodeCacheService;
import com.bob.identification.service.IdentificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 防伪批次管理实现类
 * Created by LittleBob on 2020/12/29/029.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("slave_2")
public class IdentificationBatchServiceImpl implements IdentificationBatchService {

    private static final int BATCH_NUMBER = 100000;



    @Autowired
    private BatchMapper batchMapper;

    @Autowired
    private IdentificationCodeService codeService;

    @Autowired
    private IdentificationBrandService brandService;

    @Autowired
    private IdentificationCodeCacheService codeCacheService;

    @Async
    @Override
    public void add(AddBatchParam addBatchParam) {
        Batch batch = new Batch();
        BeanUtil.copyProperties(addBatchParam, batch);
        batch.setCreateTime(LocalDateTime.now());
        batch.setSecondNumber(setSecondNumber(getBatchByFirstNumber(addBatchParam.getFirstNumber())));
        batch.setThirdNumber(setThirdNumber(getBatchBySecondNumber(addBatchParam.getFirstNumber(), batch.getSecondNumber())));
        batch.setPreThreeNumber(setPreThreeNumber(batch.getFirstNumber(), batch.getSecondNumber(), batch.getThirdNumber()));
        batch.setStatus(1);
        batch.setDemand(addBatchParam.getDemand());
        batchMapper.insert(batch);
        addList(batch);
        //return batchMapper.insert(batch) > 0 ? addList(batch) : CommonStatus.FAILED.getStatus();
    }

    /**
     * 添加一个批次的防伪码
     * @param batch 批次
     * @return 添加状态
     */
    private int addList(Batch batch) {
        Brand brand = brandService.getByPreNumber(batch.getFirstNumber());
        if (BeanUtil.isEmpty(brand)) {
            Asserts.fail("防伪品牌数据异常，请修复");
        }
        switch (brand.getBrandCode()) {
            case "LAIKOU":
                return codeService.laikouAddList(batch);
            case "FENYI":
                return codeService.fenyiAddList(batch);
            case "KAIBIN":
                return codeService.kaibinAddList(batch);
            default:
                return codeService.yaladysAddList(batch);
        }
    }

    @Override
    public CommonPage list(String keyword, Integer pageSize, Integer pageNum) {
        IPage<Batch> batchIPage = new Page<>(pageNum, pageSize);
        List<Batch> list;
        QueryWrapper<Batch> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(keyword)) {
            queryWrapper.eq("first_number", keyword);
        }
        queryWrapper.orderByDesc("create_time");
        list = batchMapper.selectPage(batchIPage, queryWrapper).getRecords();
        long total = batchIPage.getTotal();
        return CommonPage.restPage(list, total, pageNum, pageSize);
    }

    @Override
    public int updateStatus(Integer id, Integer status) {
        Batch batch = new Batch();
        batch.setStatus(status);
        batch.setId(id);
        return batchMapper.updateById(batch);
    }

    @Override
    public int delete(Long batchId) {
        codeService.deleteCacheCodeByBatchId(batchId);
        return batchMapper.deleteById(batchId);
    }

    @Async
    @Override
    public void createFile(Integer batchId) {
        Batch batch = getById(batchId);
        if (BeanUtil.isEmpty(batch)) {
            Asserts.fail("批次数据异常，请修复");
        }
        List<String> list = codeCacheService.getList(batchId, batch.getPreThreeNumber());
        if (CollUtil.isEmpty(list)) {
            Brand brand = brandService.getByPreNumber(batch.getFirstNumber());
            if (BeanUtil.isEmpty(brand)) {
                Asserts.fail("防伪品牌数据异常，请修复");
            }
            switch (brand.getBrandCode()) {
                case "LAIKOU":
                    list = codeService.laikouGetListByBatchId(batch);
                    break;
                case "FENYI":
                    list = codeService.fenyiGetListByBatchId(batch);
                    break;
                case "KAIBIN":
                    list = codeService.kaibinGetListByBatchId(batch);
                    break;
                default:
                    list = codeService.yaladysGetListByBatchId(batch);
                    break;
            }
        }
        String relativePath = ExportTxtUtil.listToTxt(list);
        if (StrUtil.isNotBlank(relativePath)) {
            updateCodeSufUrl(batchId, relativePath);
        }
    }

    @Override
    public void exportFile(Integer batchId, String code, HttpServletResponse response) {
        Batch batch = getById(batchId);
        String relativePath = null;
        if (StrUtil.isNotBlank(code) && code.equals(batch.getEncryptCodeSufUrl())) {
            relativePath = batch.getCodeSufUrl();
        }else {
            Asserts.fail("导出失败");
        }
        String fileName = System.currentTimeMillis() + "-防伪码.txt";
        ExportTxtUtil.exportTxt(response, fileName, relativePath);
    }

    @Override
    public int deleteFile(Integer batchId) {
        Batch batch = new Batch();
        batch.setId(batchId);
        batch.setCodeSufUrl(null);
        batch.setEncryptCodeSufUrl(null);
        deleteFileFolder(batchId);
        return batchMapper.updateById(batch);
    }

    @Override
    public Batch getByPreThreeNumber(String preThreeNumber) {
        QueryWrapper<Batch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pre_three_number", preThreeNumber);
        queryWrapper.last("limit 1");
        return batchMapper.selectOne(queryWrapper);
    }

    /**
     * 删除防伪码文件夹及文件
     * @param batchId 批次id
     */
    private void deleteFileFolder(Integer batchId) {
        Batch batch = getById(batchId);
        ExportTxtUtil.deleteTxt(batch.getCodeSufUrl());
    }

    /**
     * 根据id查询批次
     * @param batchId 批次id
     * @return 批次
     */
    private Batch getById(Integer batchId) {
        return batchMapper.selectById(batchId);
    }

    /**
     * 更新防伪码后缀地址
     * @param batchId 批次id
     * @param relativePath 相对地址
     * @return 更新状态
     */
    private int updateCodeSufUrl(Integer batchId, String relativePath) {
        Batch batch = new Batch();
        batch.setCodeSufUrl(relativePath);
        batch.setId(batchId);
        batch.setEncryptCodeSufUrl(MyIdUtil.getSnowflake());
        return batchMapper.updateById(batch);
    }

    /**
     * 设置防伪码前缀三个数字
     *
     * @param firstNumber  首数字
     * @param secondNumber 第二个数字
     * @param thirdNumber  第三个数字
     * @return 防伪码前缀
     */
    private String setPreThreeNumber(Integer firstNumber, Integer secondNumber, Integer thirdNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append(firstNumber);
        builder.append(secondNumber);
        builder.append(thirdNumber);
        return builder.toString();
    }

    /**
     * 设置防伪码前缀第二个数字
     *
     * @param batch 批次
     * @return 防伪码前缀第二个数字
     */
    private int setSecondNumber(Batch batch) {
        if (BeanUtil.isEmpty(batch) || batch.getSecondNumber() == 9) {
            return 0;
        }
        return batch.getSecondNumber() + 1;
    }

    /**
     * 设置防伪码前缀第三个数字
     *
     * @param batch 批次
     * @return 防伪码前缀第三个数字
     */
    private int setThirdNumber(Batch batch) {
        if (BeanUtil.isEmpty(batch) || batch.getThirdNumber() == 9) {
            return 0;
        }
        return batch.getThirdNumber() + 1;
    }

    /**
     * 根据防伪码首数字获取批次
     *
     * @param firstNumber 防伪码首数字
     * @return 批次
     */
    private Batch getBatchByFirstNumber(Integer firstNumber) {
        QueryWrapper<Batch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("first_number", firstNumber);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");
        return batchMapper.selectOne(queryWrapper);
    }

    /**
     * 根据防伪码第二个数字获取批次
     *
     * @param secondNumber 防伪码第二个数字
     * @return 批次
     */
    private Batch getBatchBySecondNumber(Integer firstNumber, Integer secondNumber) {
        QueryWrapper<Batch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("first_number", firstNumber);
        queryWrapper.eq("second_number", secondNumber);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");
        return batchMapper.selectOne(queryWrapper);
    }
}
