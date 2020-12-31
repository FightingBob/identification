package com.bob.identification.dao;

import com.bob.identification.authority.po.UmsMenu;
import com.bob.identification.authority.po.UmsRoleMenuRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by LittleBob on 2020/12/25/025.
 */
public interface RoleMenuDao {

    /**
     * 根据角色id获取菜单
     * @param roleId 角色id
     * @return 菜单列表
     */
    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

    /**
     * 插入列表
     * @param list 数据
     */
    void insertList(@Param("list") List<UmsRoleMenuRelation> list);
}
