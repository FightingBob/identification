package com.bob.identification.service;

import com.bob.identification.authority.po.UmsMenu;
import com.bob.identification.common.api.CommonPage;
import com.bob.identification.dto.UmsMenuNode;

import java.util.List;

/**
 * 后他资源管理接口
 * Created by LittleBob on 2020/12/26/026.
 */
public interface UmsMenuService {

    /**
     * 添加后台菜单
     * @param menu 菜单
     * @return 添加状态
     */
    int create(UmsMenu menu);

    /**
     * 查询后台菜单
     * @param parentId 父菜单id
     * @param pageSize 每页数量
     * @param pageNum 页号
     * @return 菜单
     */
    CommonPage list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 更新后台菜单
     * @param id 菜单id
     * @param menu 菜单
     * @return 更新状态
     */
    int update(Long id, UmsMenu menu);

    /**
     * 删除菜单
     * @param id 菜单id
     * @return 删除状态
     */
    int delete(Long id);

    /**
     * 修改菜单显示状态
     * @param id 菜单id
     * @param hidden 是否隐藏
     * @return 修改状态
     */
    int updateHidden(Long id, Integer hidden);

    /**
     * 获取菜单详情
     * @param id 菜单id
     * @return 菜单
     */
    UmsMenu getItem(Long id);

    /**
     * 树形结构返回所有菜单列表
     * @return 菜单列表
     */
    List<UmsMenuNode> treeList();
}
