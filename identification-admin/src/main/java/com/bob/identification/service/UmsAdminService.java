package com.bob.identification.service;

import com.bob.identification.authority.po.UmsAdmin;
import com.bob.identification.authority.po.UmsRole;
import com.bob.identification.common.api.CommonPage;
import com.bob.identification.dto.AdminLoginParam;
import com.bob.identification.dto.AdminRegisterParam;
import com.bob.identification.dto.AdminUpdateInfoParam;
import com.bob.identification.dto.AdminUpdatePasswordParam;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 后台用户管理接口
 * Created by LittleBob on 2020/12/18/018.
 */
public interface UmsAdminService {

    /**
     * 注册
     * @param adminRegisterParam 注册参数
     * @return 注册状态
     */
    int register(AdminRegisterParam adminRegisterParam);

    /**
     * 获取用户信息
     * @param username 账号
     * @return 用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 登录后返回token
     * @param adminLoginParam 登录参数
     * @return token
     */
    String login(AdminLoginParam adminLoginParam);

    /**
     * 根据账号获取用户
     * @param username 账号
     * @return 用户
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 根据用户id获取角色
     * @param id 用户id
     * @return 角色列表
     */
    List<UmsRole> getRoleByAdminId(Long id);

    /**
     * 更新用户信息
     * @param adminUpdateInfoParam 用户信息参数
     * @param username 用户名
     * @return 更新状态
     */
    int updateInfo(AdminUpdateInfoParam adminUpdateInfoParam, String username);

    /**
     * 更新用户密码
     * @param adminUpdatePasswordParam 用户更新密码参数
     * @param username 账号
     * @return 更新状态
     */
    int updatePassword(AdminUpdatePasswordParam adminUpdatePasswordParam, String username);

    /**
     * 分页查询
     * @param keyword 关键词 用户名或昵称
     * @param pageSize 每页数量
     * @param pageNum 当前页号
     * @return 用户列表
     */
    CommonPage list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改用户角色
     * @param adminId 用户id
     * @param roleIds 角色id
     * @return 修改状态
     */
    int updateRoleByAdmin(Long adminId, List<Long> roleIds);

    /**
     * 删除指定用户
     * @param id 用户id
     * @return 删除状态
     */
    int delete(Long id);

    /**
     * 更新用户状态
     * @param id 用户id
     * @param status 用户状态
     * @return 更新状态
     */
    int updateStatus(Long id, Integer status);

    /**
     * 刷新缓存
     * @param token token
     * @return token
     */
    String refreshToken(String token);

    /**
     * 获取指定用户信息
     * @param id 用户
     * @return 用户信息
     */
    UmsAdmin getItem(Long id);

    /**
     * 获取用户列表
     * @return 用户列表
     */
    List<UmsAdmin> listAll();

    /**
     * 重置密码
     * @param id 用户id
     * @return 重置状态
     */
    int resetPassword(Long id);
}
