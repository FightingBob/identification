package com.bob.identification.dao;

import com.bob.identification.authority.po.UmsRoleResourceRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by LittleBob on 2020/12/25/025.
 */
public interface RoleResourceDao {
    /**
     * 给角色分配资源
     * @param list 角色资源
     * @return 分配状态
     */
    int insertList(@Param("list") List<UmsRoleResourceRelation> list);
}
