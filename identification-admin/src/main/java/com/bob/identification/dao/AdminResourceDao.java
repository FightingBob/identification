package com.bob.identification.dao;

import com.bob.identification.authority.po.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by LittleBob on 2020/12/21/021.
 */
public interface AdminResourceDao {

    List<UmsResource> getResourceListByAdminId(@Param("adminId") Long adminId);
}
