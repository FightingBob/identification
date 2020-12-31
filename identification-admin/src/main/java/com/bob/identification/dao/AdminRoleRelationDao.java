package com.bob.identification.dao;

import com.bob.identification.authority.po.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Created by LittleBob on 2020/12/21/021.
 */
public interface AdminRoleRelationDao {

    int addList(@Param("adminId") Long adminId, @Param("roleIds") List<Long> roleIds);

    /**
     * 获取用户角色
     * @param adminId 用户id
     * @return 角色列表
     */
    List<UmsRole> getRoleListByAdminId(@Param("adminId") Long adminId);
}
