package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.identification.authority.mapper.UmsMenuMapper;
import com.bob.identification.authority.po.UmsMenu;
import com.bob.identification.common.api.CommonPage;
import com.bob.identification.dto.UmsMenuNode;
import com.bob.identification.service.UmsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台菜单实现类
 * Created by LittleBob on 2020/12/26/026.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
@DS("master")
public class UmsMenuServiceImpl implements UmsMenuService {

    @Autowired
    private UmsMenuMapper menuMapper;

    @Override
    public int create(UmsMenu menu) {
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        updateLevel(menu);
        return menuMapper.insert(menu);
    }

    @Override
    public CommonPage list(Long parentId, Integer pageSize, Integer pageNum) {
        IPage<UmsMenu> menuPage = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        queryWrapper.eq("parent_id", parentId);
        List<UmsMenu> list = menuMapper.selectPage(menuPage, queryWrapper).getRecords();
        long total = menuPage.getTotal();
        return CommonPage.restPage(list, total, pageNum, pageSize);
    }

    @Override
    public int update(Long id, UmsMenu menu) {
        menu.setId(id);
        menu.setUpdateTime(LocalDateTime.now());
        updateLevel(menu);
        return menuMapper.updateById(menu);
    }

    @Override
    public int delete(Long id) {
        return menuMapper.deleteById(id);
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        UmsMenu menu = new UmsMenu();
        menu.setUpdateTime(LocalDateTime.now());
        menu.setHidden(hidden);
        menu.setId(id);
        return menuMapper.updateById(menu);
    }

    @Override
    public UmsMenu getItem(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> list = menuMapper.selectList(null);
        return list.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, list))
                .collect(Collectors.toList());
    }

    /**
     * 修改菜单层级
     *
     * @param menu 菜单
     */
    private void updateLevel(UmsMenu menu) {
        if (menu.getParentId() == 0) {
            // 没有父菜单时为一级菜单
            menu.setLevel(0);
        } else {
            // 有父菜单时选择根据父菜单level设置
            UmsMenu parentMenu = menuMapper.selectById(menu.getParentId());
            if (BeanUtil.isNotEmpty(parentMenu)) {
                menu.setLevel(parentMenu.getLevel() + 1);
            } else {
                menu.setLevel(0);
            }
        }

    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     *
     * @param menu     菜单
     * @param menuList 菜单列表
     * @return 树形结构菜单列表
     */
    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtil.copyProperties(menu, node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList))
                .collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
