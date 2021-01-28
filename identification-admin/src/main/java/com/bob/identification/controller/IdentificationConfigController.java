package com.bob.identification.controller;

import com.bob.identification.common.api.CommonPage;
import com.bob.identification.common.api.CommonResult;
import com.bob.identification.identification.po.Config;
import com.bob.identification.service.IdentificationConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 防伪提示管理
 * Created by LittleBob on 2021/1/4/004.
 */
@Api(tags = "IdentificationConfigController", description = "防伪提示管理")
@RestController
@RequestMapping("config")
public class IdentificationConfigController {

    @Autowired
    private IdentificationConfigService configService;

    @ApiOperation("新增提示")
    @PostMapping("/add")
    public CommonResult add(@Validated @RequestBody Config config) {
        int count = configService.add(config);
        return count > 0 ? CommonResult.success(null) : CommonResult.failed();
    }

    @ApiOperation("提示列表")
    @GetMapping("/list")
    public CommonResult list() {
        List<Config> list = configService.list();
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation("删除提示")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam Long configId) {
        int count = configService.delete(configId);
        return count > 0 ? CommonResult.success(null) : CommonResult.failed();
    }

    @ApiOperation("修改提示")
    @PostMapping("/update")
    public CommonResult update(@Valid @RequestBody Config config) {
        int count = configService.update(config);
        return count > 0 ? CommonResult.success(null) : CommonResult.failed();
    }
}
