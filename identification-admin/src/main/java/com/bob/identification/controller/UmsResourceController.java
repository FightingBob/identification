package com.bob.identification.controller;

import com.bob.identification.authority.po.UmsResource;
import com.bob.identification.common.api.CommonPage;
import com.bob.identification.common.api.CommonResult;
import com.bob.identification.security.component.DynamicSecurityMetadataSource;
import com.bob.identification.service.UmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台资源管理
 * Created by LittleBob on 2020/12/25/025.
 */
@RestController
@Api(tags = "UmsResourceController", description = "后台资源管理")
@RequestMapping("/resource")
public class UmsResourceController {

    @Autowired
    private UmsResourceService resourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation(value = "添加后台资源")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsResource resource) {
        int count = resourceService.create(resource);
        dynamicSecurityMetadataSource.clearDataSource();
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "分页模糊查询后台资源")
    @GetMapping("/list")
    public CommonResult<CommonPage<UmsResource>> list(@RequestParam(required = false) Long categoryId,
                                                      @RequestParam(required = false) String nameKeyword,
                                                      @RequestParam(required = false) String urlKeyword,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage commonPage = resourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(commonPage);
    }

    @ApiOperation(value = "修改后台资源")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,
                               @RequestBody UmsResource resource) {
        int count = resourceService.update(id, resource);
        dynamicSecurityMetadataSource.clearDataSource();
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "根据id获取资源详情")
    @GetMapping("/{id}")
    public CommonResult<UmsResource> getItem(@PathVariable Long id) {
        UmsResource resource = resourceService.getItem(id);
        return CommonResult.success(resource);
    }

    @ApiOperation(value = "根据id删除后台资源")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = resourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "查询所有后台资源")
    @GetMapping("/listAll")
    public CommonResult<List<UmsResource>> listAll() {
        List<UmsResource> list = resourceService.listAll();
        return CommonResult.success(list);
    }
}
