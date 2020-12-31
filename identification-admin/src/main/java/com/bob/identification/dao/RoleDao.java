package com.bob.identification.dao;

import com.bob.identification.authority.po.UmsMenu;
import com.bob.identification.authority.po.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by LittleBob on 2020/12/22/022.
 */
public interface RoleDao {


    /**
     * 根据用户id查询菜单
     * @param adminId 用户id
     * @return 菜单列表
     */
    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);

    /**
     * 根据角色id查询资源
     * @param roleId 角色id
     * @return 资源
     */
    List<UmsResource> getResourceByRoleId(@Param("roleId") Long roleId);
}
