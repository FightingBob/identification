package com.bob.identification.controller;

import com.bob.identification.common.api.CommonPage;
import com.bob.identification.common.api.CommonResult;
import com.bob.identification.dto.AddBrandParam;
import com.bob.identification.identification.po.Brand;
import com.bob.identification.service.IdentificationBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 防伪品牌管理
 * Created by LittleBob on 2020/12/29/029.
 */
@Api(tags = "IdentificationBrandController", description = "防伪品牌管理")
@RestController
@RequestMapping("/brand")
public class IdentificationBrandController {

    @Autowired
    private IdentificationBrandService brandService;

    @ApiOperation("新增品牌")
    @PostMapping("/add")
    public CommonResult add(@Validated @RequestBody AddBrandParam addBrandParam) {
        int count = brandService.add(addBrandParam);
        return count > 0 ? CommonResult.success(null) : CommonResult.failed();
    }

    @ApiOperation(value = "品牌列表")
    @GetMapping("/list")
    public CommonResult list() {
        List<Brand> list = brandService.list();
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation("修改品牌")
    @PostMapping("/update/{brandId}")
    public CommonResult update(@PathVariable("brandId") Integer brandId, @RequestBody Brand brand) {
        int count = brandService.update(brandId, brand);
        return count > 0 ? CommonResult.success(null) : CommonResult.failed();
    }

    @ApiOperation("删除品牌")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam Integer brandId) {
        int count = brandService.delete(brandId);
        return count > 0 ? CommonResult.success(null) : CommonResult.failed();
    }
}
