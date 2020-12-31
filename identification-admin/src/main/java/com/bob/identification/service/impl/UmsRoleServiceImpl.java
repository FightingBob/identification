package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.identification.authority.mapper.UmsAdminRoleRelationMapper;
import com.bob.identification.authority.mapper.UmsRoleMapper;
import com.bob.identification.authority.mapper.UmsRoleMenuRelationMapper;
import com.bob.identification.authority.mapper.UmsRoleResourceRelationMapper;
import com.bob.identification.authority.po.*;
import com.bob.identification.common.api.CommonStatus;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.dao.RoleDao;
import com.bob.identification.dao.RoleMenuDao;
import com.bob.identification.dao.RoleResourceDao;
import com.bob.identification.dto.RoleParam;
import com.bob.identification.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Name;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LittleBob on 2020/12/22/022.
 */
@Service
@DS("master")
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsAdminRoleRelationMapper relationMapper;

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;

    @Autowired
    private RoleResourceDao roleResourceDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private UmsRoleMenuRelationMapper roleMenuRelationMapper;

    @Override
    public int adminAddRole(Long id) {
        UmsAdminRoleRelation relation = new UmsAdminRoleRelation();
        UmsRole role = getRoleByName("超级管理员");
        if (BeanUtil.isEmpty(role)) {
            Asserts.fail("角色数据出现异常，请及时修复");
        }
        relation.setAdminId(id);
        relation.setRoleId(role.getId());
        return relationMapper.insert(relation);
    }

    @Override
    public List<UmsMenu> getMenuListByAdminId(Long id) {
        return roleDao.getMenuList(id);
    }

    @Override
    public int create(RoleParam roleParam) {
        UmsRole role = new UmsRole();
        BeanUtil.copyProperties(roleParam, role);
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        role.setSort(0);
        return roleMapper.insert(role);
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isBlank(keyword)) {
            return roleMapper.selectList(null);
        }
        queryWrapper.like("name", keyword);
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    public int update(Long id, RoleParam roleParam) {
        UmsRole role = new UmsRole();
        BeanUtil.copyProperties(roleParam, role);
        role.setId(id);
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.updateById(role);
    }

    @Override
    public int delete(Long id) {
        return roleMapper.deleteById(id);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        UmsRole role = new UmsRole();
        role.setId(id);
        role.setStatus(status);
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.updateById(role);
    }

    @Override
    public List<UmsRole> listAll() {
        return roleMapper.selectList(null);
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return roleDao.getResourceByRoleId(roleId);
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        deleteRoleResourceRelation(roleId);
        return setRoleResource(roleId, resourceIds);
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return roleMenuDao.getMenuListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        deleteRoleMenuRelation(roleId);
        if (CollUtil.isNotEmpty(menuIds)) {
            setRoleMenu(roleId, menuIds);
        }
        return CommonStatus.SUCCESS.getStatus();
    }

    /**
     * 更具角色名获取角色
     *
     * @param name 角色名
     * @return 角色
     */
    private UmsRole getRoleByName(String name) {
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 1");
        queryWrapper.eq("name", name);
        return roleMapper.selectOne(queryWrapper);
    }

    /**
     * 删除角色可访问资源
     *
     * @param roleId 角色id
     */
    private void deleteRoleResourceRelation(Long roleId) {
        QueryWrapper<UmsRoleResourceRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleResourceRelationMapper.delete(queryWrapper);
    }

    /**
     * 设置角色资源
     *
     * @param roleId      角色id
     * @param resourceIds 资源id
     * @return 设置状态
     */
    private int setRoleResource(Long roleId, List<Long> resourceIds) {
        List<UmsRoleResourceRelation> list = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setResourceId(resourceId);
            relation.setRoleId(roleId);
            list.add(relation);
        }
        return roleResourceDao.insertList(list);
    }

    /**
     * 删除角色可访问菜单
     * @param roleId 角色id
     */
    private void deleteRoleMenuRelation(Long roleId) {
        QueryWrapper<UmsRoleMenuRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleMenuRelationMapper.delete(queryWrapper);
    }

    /**
     * 设置角色菜单
     * @param roleId 角色id
     * @param menuIds 菜单id数组
     * @return 设置状态
     */
    private int setRoleMenu(Long roleId, List<Long> menuIds) {
        List<UmsRoleMenuRelation> list = new ArrayList<>();
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setMenuId(menuId);
            relation.setRoleId(roleId);
            list.add(relation);
        }
        roleMenuDao.insertList(list);
        return CommonStatus.SUCCESS.getStatus();
    }
}
