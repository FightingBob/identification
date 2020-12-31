package com.bob.identification.service;

import com.bob.identification.authority.po.UmsAdmin;
import com.bob.identification.authority.po.UmsMenu;
import com.bob.identification.authority.po.UmsResource;
import com.bob.identification.authority.po.UmsRole;
import com.bob.identification.dto.RoleParam;

import java.util.List;

/**
 * 后台角色管理接口
 * Created by LittleBob on 2020/12/22/022.
 */
public interface UmsRoleService {

    /**
     * 给用户分配默认员工角色
     * @param id 用户id
     * @return 分配状态
     */
    int adminAddRole(Long id);

    /**
     * 根据用户id获取对应菜单
     * @param id 用户id
     * @return 菜单
     */
    List<UmsMenu> getMenuListByAdminId(Long id);

    /**
     * 创建角色
     * @param roleParam 角色参数
     * @return 创建状态
     */
    int create(RoleParam roleParam);

    /**
     * 获取角色列表
     * @param keyword 关键词
     * @param pageSize 每页数量
     * @param pageNum 页号
     * @return 角色列表
     */
    List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改角色
     * @param id 角色id
     * @param roleParam 角色参数
     * @return 修改状态
     */
    int update(Long id, RoleParam roleParam);

    /**
     * 删除角色
     * @param id 角色id
     * @return 删除状态
     */
    int delete(Long id);

    /**
     * 更新角色状态
     * @param id 角色id
     * @param status 状态
     * @return 更新状态
     */
    int updateStatus(Long id, Integer status);

    /**
     * 获取所有角色
     * @return 角色列表
     */
    List<UmsRole> listAll();

    /**
     * 获取角色相关资源
     * @param roleId 角色id
     * @return 资源列表
     */
    List<UmsResource> listResource(Long roleId);

    /**
     * 给角色分配资源
     * @param roleId 角色id
     * @param resourceIds 资源ids
     * @return 分配状态
     */
    int allocResource(Long roleId, List<Long> resourceIds);

    /**
     * 获取角色相关菜单
     * @param roleId 角色id
     * @return 菜单
     */
    List<UmsMenu> listMenu(Long roleId);

    /**
     * 给角色分配菜单
     * @param roleId 角色id
     * @param menuIds 菜单id
     * @return 分配状态
     */
    int allocMenu(Long roleId, List<Long> menuIds);
}
