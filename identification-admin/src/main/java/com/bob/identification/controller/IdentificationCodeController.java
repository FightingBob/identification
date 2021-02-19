package com.bob.identification.controller;

import cn.hutool.core.bean.BeanUtil;
import com.bob.identification.common.api.*;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.common.util.MyStrUtil;
import com.bob.identification.dto.CustomCodeAddParam;
import com.bob.identification.identification.po.Batch;
import com.bob.identification.identification.po.Brand;
import com.bob.identification.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台防伪码管理
 * Created by LittleBob on 2021/1/3/003.
 */
@Api(tags = "IdentificationCodeController", description = "后台防伪码管理")
@RestController
@RequestMapping("/code")
public class IdentificationCodeController {

    @Autowired
    private IdentificationCodeService codeService;

    @Autowired
    private IdentificationCodeCacheService codeCacheService;

    @Autowired
    private IdentificationConfigService configService;

    @Autowired
    private IdentificationBatchService batchService;

    @Autowired
    private IdentificationBrandService brandService;

    @Autowired
    private IdentificationCustomCodeService customCodeService;

    @Autowired
    private IdentificationOldCodeService oldCodeService;

    @ApiOperation(value = "查询")
    @GetMapping("/query")
    public CommonResult query(@RequestParam String code) {
        codeService.validateCode(code);
        QueryCodeResult queryCodeResult = codeService.queryCodeFromCache(code);
        if (BeanUtil.isNotEmpty(queryCodeResult)) {
            if (queryCodeResult.getQueryCode().getIsReal() == 1) {
                if (DatabaseOption.LAIKOU.getDatabaseOption().equals(queryCodeResult.getQueryCode().getDatabaseOption())) {
                    codeService.laikouUpdate(queryCodeResult.getQueryCode());
                } else if (DatabaseOption.FENYI.getDatabaseOption().equals(queryCodeResult.getQueryCode().getDatabaseOption())) {
                    codeService.fenyiUpdate(queryCodeResult.getQueryCode());
                } else if (DatabaseOption.KAIBIN.getDatabaseOption().equals(queryCodeResult.getQueryCode().getDatabaseOption())) {
                    codeService.kaibinUpdate(queryCodeResult.getQueryCode());
                } else {
                    codeService.yaladysUpdate(queryCodeResult.getQueryCode());
                }
            } else if (queryCodeResult.getQueryCode().getIsReal() == 2) {
                customCodeService.update(queryCodeResult.getQueryCode());
            } else if (queryCodeResult.getQueryCode().getIsReal() == 3) {
                oldCodeService.update(queryCodeResult.getQueryCode());
            }
        } else {
            queryCodeResult = customCodeService.query(code);
            if (BeanUtil.isNotEmpty(queryCodeResult)) {
                customCodeService.update(queryCodeResult.getQueryCode());
            } else {
                queryCodeResult = oldCodeService.query(code);
                if (BeanUtil.isNotEmpty(queryCodeResult)) {
                    oldCodeService.update(queryCodeResult.getQueryCode());
                } else {
                    Batch batch = batchService.getByPreThreeNumber(MyStrUtil.getPreThreeNumber(code));
                    if (BeanUtil.isNotEmpty(batch)) {
                        Brand brand = brandService.getByPreNumber(batch.getFirstNumber());
                        if (DatabaseOption.LAIKOU.getDatabaseCodeName().equals(brand.getBrandCode())) {
                            queryCodeResult = codeService.laikouQuery(code, batch.getSecondNumber());
                        } else if (DatabaseOption.FENYI.getDatabaseCodeName().equals(brand.getBrandCode())) {
                            queryCodeResult = codeService.fenyiQuery(code, batch.getSecondNumber());
                        } else if (DatabaseOption.KAIBIN.getDatabaseCodeName().equals(brand.getBrandCode())) {
                            queryCodeResult = codeService.kaibinQuery(code, batch.getSecondNumber());
                        } else {
                            queryCodeResult = codeService.yaladysQuery(code, batch.getSecondNumber());
                        }
                        if (BeanUtil.isEmpty(queryCodeResult)) {
                            queryCodeResult = codeService.getErrorQueryCodeResult(code);
                        }
                    } else {
                        queryCodeResult = new QueryCodeResult();
                        queryCodeResult.setResult(configService.getErrorMessage(code));
                        codeCacheService.addBlackCode(code);
                    }
                }
            }
        }
        Map<String, Object> dataMap = new HashMap<>();
        if (BeanUtil.isNotEmpty(queryCodeResult.getQueryCode())) {
            dataMap.put("status", queryCodeResult.getQueryCode().getStatus() > 0 ? "有效" : "无效");
            dataMap.put("isReal", queryCodeResult.getQueryCode().getIsReal() > 0 ? "真" : "假");
        } else {
            dataMap.put("status", "无效");
            dataMap.put("isReal", "假");
        }
        dataMap.put("serialNumber", code);
        dataMap.put("result", queryCodeResult.getResult());
        List<Object> list = new ArrayList<>();
        list.add(dataMap);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation(value = "添加防伪码")
    @PostMapping("/add")
    public CommonResult addCode(@Validated @RequestBody CustomCodeAddParam customCodeAddParam) {
        QueryCode queryCode = customCodeService.add(customCodeAddParam);
        codeCacheService.addWhiteCode(queryCode);
        codeCacheService.deleteBlackCode(queryCode.getSerialNumber());
        return CommonResult.success(null);
    }

    @ApiOperation(value = "修改防伪码状态，改为有效")
    @PostMapping("/clear")
    public CommonResult clearCode(@RequestBody QueryCode queryCode) {
        if (BeanUtil.isEmpty(queryCode) || queryCode.getStatus() == 1 || queryCode.getIsReal() <= 0) {
            Asserts.fail("防伪码状态更新异常111，请修复");
        }
        QueryCode blackCode = codeCacheService.queryCodeFromBlackList(queryCode.getSerialNumber());
        if (BeanUtil.isNotEmpty(blackCode)) {
            if (blackCode.getIsReal() == 1) {
                if (DatabaseOption.LAIKOU.getDatabaseOption().equals(blackCode.getDatabaseOption())) {
                    codeService.laikouUpdateStatus(blackCode);
                } else if (DatabaseOption.FENYI.getDatabaseOption().equals(blackCode.getDatabaseOption())) {
                    codeService.fenyiUpdateStatus(blackCode);
                } else if (DatabaseOption.KAIBIN.getDatabaseOption().equals(blackCode.getDatabaseOption())) {
                    codeService.kaibinUpdateStatus(blackCode);
                } else {
                    codeService.yaladysUpdateStatus(blackCode);
                }
            } else if (blackCode.getIsReal() == 2) {
                customCodeService.updateStatus(blackCode);
            } else if (blackCode.getIsReal() == 3) {
                oldCodeService.updateStatus(blackCode);
            }
            codeCacheService.deleteBlackCode(blackCode.getSerialNumber());
        } else {
            Asserts.fail("防伪码状态更新异常222，请修复");
        }
        return CommonResult.success(null);
    }
}
