package com.bob.identification.controller;

import cn.hutool.core.bean.BeanUtil;
import com.bob.identification.common.api.CommonResult;
import com.bob.identification.common.api.DatabaseOption;
import com.bob.identification.common.api.QueryCode;
import com.bob.identification.common.api.QueryCodeResult;
import com.bob.identification.common.util.MyStrUtil;
import com.bob.identification.identification.po.Batch;
import com.bob.identification.identification.po.Brand;
import com.bob.identification.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台防伪码管理
 * Created by LittleBob on 2021/1/4/004.
 */
@Api(tags = "IdentificationCodeController", description = "前台防伪码管理")
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private IdentificationCodeService codeService;

    @Autowired
    private IdentificationConfigService configService;

    @Autowired
    private IdentificationCodeCacheService codeCacheService;

    @Autowired
    private IdentificationCustomCodeService customCodeService;

    @Autowired
    private IdentificationBatchService batchService;

    @Autowired
    private IdentificationBrandService brandService;

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
        return CommonResult.success(queryCodeResult.getResult());
    }

}
