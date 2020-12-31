package com.bob.identification.controller;

import com.bob.identification.authority.po.UmsResourceCategory;
import com.bob.identification.common.api.CommonResult;
import com.bob.identification.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台资源分类管理
 * Created by LittleBob on 2020/12/26/026.
 */
@RestController
@Api(tags = "UmsResourceCategoryController", description = "后台资源分类管理")
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {

    @Autowired
    private UmsResourceCategoryService resourceCategoryService;

    @ApiOperation(value = "添加后台资源分类")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsResourceCategory resourceCategory) {
        int count = resourceCategoryService.create(resourceCategory);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "查询所有后台资源分类")
    @GetMapping("/listAll")
    public CommonResult<List<UmsResourceCategory>> listAll() {
        List<UmsResourceCategory> list = resourceCategoryService.listAll();
        return CommonResult.success(list);
    }

    @ApiOperation("修改后台资源分类")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,
                               @RequestBody UmsResourceCategory resourceCategory) {
        int count  = resourceCategoryService.update(id, resourceCategory);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation("根据id删除后台资源")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = resourceCategoryService.delete(id);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }
}
