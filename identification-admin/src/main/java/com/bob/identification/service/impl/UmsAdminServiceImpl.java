package com.bob.identification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.identification.authority.mapper.UmsAdminMapper;
import com.bob.identification.authority.mapper.UmsAdminRoleRelationMapper;
import com.bob.identification.authority.po.UmsAdmin;
import com.bob.identification.authority.po.UmsAdminRoleRelation;
import com.bob.identification.authority.po.UmsResource;
import com.bob.identification.authority.po.UmsRole;
import com.bob.identification.bo.AdminUserDetails;
import com.bob.identification.common.api.CommonPage;
import com.bob.identification.common.api.CommonStatus;
import com.bob.identification.common.exception.Asserts;
import com.bob.identification.common.util.ValidatorUtil;
import com.bob.identification.dao.AdminRoleRelationDao;
import com.bob.identification.dto.AdminLoginParam;
import com.bob.identification.dto.AdminRegisterParam;
import com.bob.identification.dto.AdminUpdateInfoParam;
import com.bob.identification.dto.AdminUpdatePasswordParam;
import com.bob.identification.security.util.JwtTokenUtil;
import com.bob.identification.service.UmsAdminService;
import com.bob.identification.service.UmsResourceService;
import com.bob.identification.service.UmsRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户管理实现类
 * Created by LittleBob on 2020/12/18/018.
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@DS("master")
public class UmsAdminServiceImpl implements UmsAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Autowired
    private UmsResourceService resourceService;

    @Value("${littlebob.password.prefix}")
    private String prefix;

    @Value("${littlebob.password.suffix}")
    private String suffix;

    @Autowired
    private UmsRoleService roleService;

    @Autowired
    private AdminRoleRelationDao adminRoleRelationDao;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Override
    public int register(AdminRegisterParam adminRegisterParam) {
        UmsAdmin admin = new UmsAdmin();
        BeanUtil.copyProperties(adminRegisterParam, admin);
        judgeAdminByUsername(admin.getUsername());
        judgeAdminByPhone(admin.getPhone());
        return save(setAdmin(admin));
    }

    /**
     * 新增用户
     *
     * @param admin 用户
     * @return 新增状态
     */
    private int save(UmsAdmin admin) {
        return umsAdminMapper.insert(admin) > 0 ? roleService.adminAddRole(admin.getId()) : CommonStatus.FAILED.getStatus();
    }

    /**
     * 配置用户信息
     *
     * @param data 用户
     * @return 用户
     */
    private UmsAdmin setAdmin(UmsAdmin data) {
        String newUsername = setUsername(data.getUsername(), data.getPhone());
        data.setPassword(StrUtil.isNotBlank(data.getPassword()) ? data.getPassword() : data.getPhone());
        String newPassword = encodePassword(data.getPassword());
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(LocalDateTime.now());
        data.setUsername(newUsername);
        data.setPassword(newPassword);
        return data;
    }

    /**
     * 加密
     *
     * @param password 密码
     * @return 已加密的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(splicePassword(password));
    }


    /**
     * 拼接密码
     *
     * @param password 密码
     * @return 密码
     */
    private String splicePassword(String password) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(prefix)
                .append(password)
                .append(suffix);
        return stringBuffer.toString();
    }

    /**
     * 设置用户名
     * 如果为空，则默认未手机号
     *
     * @param username 用户名
     * @param phone    手机号
     * @return 用户名
     */
    private String setUsername(String username, String phone) {
        return StrUtil.isEmpty(username) ? phone : username;
    }


    /**
     * 判断账号是否被占用
     *
     * @param username 账号
     */
    private void judgeAdminByUsername(String username) {
        if (BeanUtil.isNotEmpty(getAdminByUsername(username))) {
            Asserts.fail("账号已被占用，请更换账号");
        }
    }

    /**
     * 判断手机是否被占用
     *
     * @param phone 手机号
     */
    private void judgeAdminByPhone(String phone) {
        if (BeanUtil.isNotEmpty(getAdminByPhone(phone))) {
            Asserts.fail("手机号已被占用，请更换手机号");
        }
    }

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户
     */
    private UmsAdmin getAdminByPhone(String phone) {
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        wrapper.last("limit 1");
        return umsAdminMapper.selectOne(wrapper);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (BeanUtil.isNotEmpty(admin)) {
            if (BeanUtil.isEmpty(admin.getStatus()) || 1 != admin.getStatus()) {
                Asserts.fail("该账号无法登录");
            }
            List<UmsResource> resourceList = resourceService.getResourceList(admin.getId());
            return new AdminUserDetails(admin, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public String login(AdminLoginParam adminLoginParam) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(adminLoginParam.getUsername());
            String splicePassword = splicePassword(adminLoginParam.getPassword());
            if (!passwordEncoder.matches(splicePassword, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            updateLoginTimeByUsername(userDetails.getUsername());
            // TODO: 2020/4/28/028 登录日志更新
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常：{}", e.getMessage());
        }
        return token;
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        QueryWrapper<UmsAdmin> wrapper = getQueryWrapperByUsername(username);
        wrapper.last("limit 1");
        return umsAdminMapper.selectOne(wrapper);
    }

    @Override
    public List<UmsRole> getRoleByAdminId(Long id) {
        return adminRoleRelationDao.getRoleListByAdminId(id);
    }

    @Override
    public int updateInfo(AdminUpdateInfoParam adminUpdateInfoParam, String username) {
        ValidatorUtil.isEmail(adminUpdateInfoParam.getEmail());
        ValidatorUtil.isPhone(adminUpdateInfoParam.getPhone());
        ValidatorUtil.isUsername(adminUpdateInfoParam.getUsername());
        if (!isSamePhone(username, adminUpdateInfoParam.getPhone())) {
            hasExistSamePhone(adminUpdateInfoParam.getPhone());
        }
        if (!isSameUsername(username, adminUpdateInfoParam.getUsername())) {
            hasExistedSameUsername(adminUpdateInfoParam.getUsername());
        }
        UmsAdmin admin = new UmsAdmin();
        BeanUtil.copyProperties(adminUpdateInfoParam, admin);
        admin.setUpdateTime(LocalDateTime.now());
        return umsAdminMapper.update(admin, getQueryWrapperByUsername(username));
    }

    @Override
    public int updatePassword(AdminUpdatePasswordParam adminUpdatePasswordParam, String username) {
        String splicePassword = splicePassword(adminUpdatePasswordParam.getOldPassword());
        if (!passwordEncoder.matches(splicePassword, getAdminByUsername(username).getPassword())) {
            Asserts.fail("原密码不正确");
        }
        UmsAdmin admin = new UmsAdmin();
        admin.setUpdateTime(LocalDateTime.now());
        String newPassword = encodePassword(adminUpdatePasswordParam.getNewPassword());
        admin.setPassword(newPassword);
        return umsAdminMapper.update(admin, getQueryWrapperByUsername(username));
    }

    @Override
    public CommonPage list(String keyword, Integer pageSize, Integer pageNum) {
        IPage<UmsAdmin> userPage = new Page<>(pageNum, pageSize);
        List<UmsAdmin> list;
        if (StrUtil.isNotBlank(keyword)) {
            QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("username", keyword)
                    .or()
                    .like("nickname", keyword);
            list = umsAdminMapper.selectPage(userPage, queryWrapper).getRecords();
        } else {
            list = umsAdminMapper.selectPage(userPage, null).getRecords();
        }
        long total = userPage.getTotal();
        return CommonPage.restPage(list, total, pageNum, pageSize);
    }

    @Override
    public int updateRoleByAdmin(Long adminId, List<Long> roleIds) {
        deleteRoleByAdminId(adminId);
        return setAdminRole(adminId, roleIds);
    }

    @Override
    public int delete(Long id) {
        return umsAdminMapper.deleteById(id);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        UmsAdmin admin = new UmsAdmin();
        admin.setUpdateTime(LocalDateTime.now());
        admin.setStatus(status);
        admin.setId(id);
        return umsAdminMapper.updateById(admin);
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    @Override
    public UmsAdmin getItem(Long id) {
        return umsAdminMapper.selectById(id);
    }

    @Override
    public List<UmsAdmin> listAll() {
        return umsAdminMapper.selectList(null);
    }

    @Override
    public int resetPassword(Long id) {
        UmsAdmin admin = umsAdminMapper.selectById(id);
        if (BeanUtil.isEmpty(admin)) {
            Asserts.fail("该用户不存在");
        }
        UmsAdmin data = new UmsAdmin();
        data.setPassword(encodePassword(admin.getPhone()));
        data.setUpdateTime(LocalDateTime.now());
        data.setId(id);
        return umsAdminMapper.updateById(data);
    }

    /**
     * 设置用户角色
     *
     * @param adminId 用户id
     * @param roleIds 角色id
     * @return 设置状态
     */
    private int setAdminRole(Long adminId, List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            Asserts.fail("没有选择角色");
        }
        return adminRoleRelationDao.addList(adminId, roleIds);
    }

    /**
     * 删除用户角色
     *
     * @param adminId 用户id
     */
    private void deleteRoleByAdminId(Long adminId) {
        QueryWrapper<UmsAdminRoleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", adminId);
        adminRoleRelationMapper.delete(queryWrapper);
    }

    /**
     * 根据账号配置条件构造器
     *
     * @param username 账号
     * @return 条件构造器
     */
    private QueryWrapper<UmsAdmin> getQueryWrapperByUsername(String username) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return queryWrapper;
    }

    /**
     * 根据用户名更新登录时间
     *
     * @param username 用户名
     */
    private void updateLoginTimeByUsername(String username) {
        UmsAdmin admin = new UmsAdmin();
        admin.setLoginTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        umsAdminMapper.update(admin, getQueryWrapperByUsername(username));
    }

    /**
     * 判断手机号是否与数据库的相同
     *
     * @param username 用户名
     * @param phone    手机号
     * @return 相同与否
     */
    private boolean isSamePhone(String username, String phone) {
        return phone.equals(getAdminByUsername(username).getPhone());
    }

    /**
     * 判断是否存在其他相同的手机号
     *
     * @param phone 手机号
     */
    private void hasExistSamePhone(String phone) {
        if (BeanUtil.isNotEmpty(getAdminByPhone(phone))) {
            Asserts.fail("该手机号已被占用");
        }
    }

    /**
     * 判断用户名是否与数据库的相同
     *
     * @param username    用户名
     * @param newUsername 新用户名
     * @return 相同与否
     */
    private boolean isSameUsername(String username, String newUsername) {
        return username.equals(newUsername);
    }

    /**
     * 判断是否镩子其他相同的用户名
     *
     * @param username 用户名
     */
    private void hasExistedSameUsername(String username) {
        if (BeanUtil.isNotEmpty(getAdminByUsername(username))) {
            Asserts.fail("该账号已被占用");
        }
    }


}
