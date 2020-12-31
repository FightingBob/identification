package com.bob.identification.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.bob.identification.authority.po.UmsAdmin;
import com.bob.identification.authority.po.UmsRole;
import com.bob.identification.common.api.CommonPage;
import com.bob.identification.common.api.CommonResult;
import com.bob.identification.dto.AdminLoginParam;
import com.bob.identification.dto.AdminRegisterParam;
import com.bob.identification.dto.AdminUpdateInfoParam;
import com.bob.identification.dto.AdminUpdatePasswordParam;
import com.bob.identification.service.UmsAdminService;
import com.bob.identification.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理o
 * Created by LittleBob on 2020/12/17/017.
 */
@RestController
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@RequestBody @Validated AdminRegisterParam adminRegisterParam) {
        int status = adminService.register(adminRegisterParam);
        return status > 0 ? CommonResult.success(null) : CommonResult.failed();
    }

    @ApiOperation(value = "登录后返回token")
    @PostMapping("/login")
    public CommonResult login(@Validated @RequestBody AdminLoginParam adminLoginParam) {
        String token = adminService.login(adminLoginParam);
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/info")
    public CommonResult getAdminInfo(Principal principal) {
        if (BeanUtil.isEmpty(principal)) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("phone", umsAdmin.getPhone());
        data.put("nickname", umsAdmin.getNickname());
        data.put("email", umsAdmin.getEmail());
        data.put("introduction", umsAdmin.getNote());
        data.put("name", umsAdmin.getUsername());
        data.put("avatar", umsAdmin.getIcon());
        List<UmsRole> roleList = adminService.getRoleByAdminId(umsAdmin.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream()
                    .map(UmsRole::getName)
                    .collect(Collectors.toList());
            data.put("roles", roles);
        }
        //data.put("roles", new String[]{"TEST"});
        data.put("menus", roleService.getMenuListByAdminId(umsAdmin.getId()));

        return CommonResult.success(data);
    }

    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/updateInfo")
    public CommonResult updateInfo(@Validated @RequestBody AdminUpdateInfoParam adminUpdateInfoParam,
                                   Principal principal) {
        if (BeanUtil.isEmpty(principal)) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        int status = adminService.updateInfo(adminUpdateInfoParam, username);
        if (status == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "更新密码")
    @PostMapping("/updatePassword")
    public CommonResult updatePassword(@Validated @RequestBody AdminUpdatePasswordParam adminUpdatePasswordParam,
                                       Principal principal) {
        if (BeanUtil.isEmpty(principal)) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        int status = adminService.updatePassword(adminUpdatePasswordParam, username);
        if (status == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "根据用户名或昵称分页获取用户列表")
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage commonPage = adminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(commonPage);
    }

    @ApiOperation(value = "导出列表")
    @GetMapping("/exportList")
    public CommonResult exportList(@RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage commonPage = adminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(commonPage);
    }

    @ApiOperation(value = "获取指定用户的角色")
    @GetMapping("/role/{adminId}")
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> list = adminService.getRoleByAdminId(adminId);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "给用户分配角色")
    @PostMapping("/role/update")
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRoleByAdmin(adminId, roleIds);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "删除指定用户")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = adminService.delete(id);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "修改账号状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id,
                                     @RequestParam(value = "status", required = true) Integer status) {
        int count = adminService.updateStatus(id, status);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getItem(id);
        return CommonResult.success(admin);
    }

    @ApiOperation(value = "用户列表")
    @GetMapping("/listAll")
    public CommonResult listAll() {
        List<UmsAdmin> list = adminService.listAll();
        return CommonResult.success(list);
    }

    @ApiOperation(value = "重置密码")
    @PostMapping("/resetPassword/{id}")
    public CommonResult resetPassword(@PathVariable Long id) {
        int count = adminService.resetPassword(id);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

}
