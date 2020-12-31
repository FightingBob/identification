package com.bob.identification.controller;

import com.bob.identification.authority.po.UmsMenu;
import com.bob.identification.authority.po.UmsResource;
import com.bob.identification.authority.po.UmsRole;
import com.bob.identification.common.api.CommonPage;
import com.bob.identification.common.api.CommonResult;
import com.bob.identification.dto.RoleParam;
import com.bob.identification.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户角色管理
 * Created by LittleBob on 2020/12/24/024.
 */
@RestController
@Api(tags = "UmsRoleController", description = "后台用户角色管理")
@RequestMapping("/role")
public class UmsRoleController {

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation(value = "添加角色")
    @PostMapping("/create")
    public CommonResult create(@RequestBody RoleParam roleParam) {
        int count = roleService.create(roleParam);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "根据角色名称分页获取角色列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<UmsRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsRole> list = roleService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation(value = "修改角色")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody RoleParam roleParam) {
        int count = roleService.update(id, roleParam);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id", required = true) Long id) {
        int count = roleService.delete(id);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "修改角色状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id,
                                     @RequestParam(value = "status", required = true) Integer status) {
        int count = roleService.updateStatus(id, status);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/listAll")
    public CommonResult<List<UmsRole>> listAll() {
        List<UmsRole> list = roleService.listAll();
        return CommonResult.success(list);
    }

    @ApiOperation(value = "获取角色相关资源")
    @GetMapping("/listResource/{roleId}")
    public CommonResult<List<UmsResource>> listResource(@PathVariable Long roleId) {
        List<UmsResource> list = roleService.listResource(roleId);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "给角色分配资源")
    @PostMapping("/allocResource")
    public CommonResult allocResource(@RequestParam Long roleId,
                                      @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "获取角色相关菜单")
    @GetMapping("/listMenu/{roleId}")
    public CommonResult<List<UmsMenu>> listMenu(@PathVariable Long roleId) {
        List<UmsMenu> list = roleService.listMenu(roleId);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "给角色分配菜单")
    @PostMapping("/allocMenu")
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {

        int count = roleService.allocMenu(roleId, menuIds);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }
}
