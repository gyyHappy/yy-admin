package com.gyy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gyy.common.constants.Constant;
import com.gyy.common.exception.BusinessException;
import com.gyy.common.exception.code.BaseResponseCode;
import com.gyy.common.utils.*;
import com.gyy.modules.sys.entity.SysRoleEntity;
import com.gyy.modules.sys.entity.SysUserEntity;
import com.gyy.modules.sys.form.SysLoginForm;
import com.gyy.modules.sys.mapper.SysUserMapper;
import com.gyy.modules.sys.service.SysMenuService;
import com.gyy.modules.sys.service.SysRoleService;
import com.gyy.modules.sys.service.SysUserRoleService;
import com.gyy.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyy.modules.sys.vo.resp.LoginRespVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GYY
 * @since 2020-07-06
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TokenSetting tokenSetting;

    @Override
    public LoginRespVO login(SysLoginForm form) {
        //通过用户名查询
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username", form.getUsername());
        SysUserEntity sysUserEntity = sysUserMapper.selectOne(wrapper);
        //判断用户信息
        if (sysUserEntity == null) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
        }
        if (sysUserEntity.getStatus() == 2) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_LOCK);
        }
        if (!PasswordUtils.matches(sysUserEntity.getSalt(), form.getPassword(), sysUserEntity.getPassword())) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }

        LoginRespVO respVO = new LoginRespVO();
        //生成accessToken
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.JWT_ROLES_KEY, getRolesByUserId(sysUserEntity.getId()));
        claims.put(Constant.JWT_PERMISSIONS_KEY, getPermissionsByUserId(sysUserEntity.getId()));
        claims.put(Constant.JWT_USER_NAME, sysUserEntity.getUsername());
        String accessToken = JwtTokenUtils.getAccessToken(sysUserEntity.getId(), claims);

        //生成refreshToken
        Map<String, Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put(Constant.JWT_USER_NAME, sysUserEntity.getUsername());
        String refreshToken;
        //根据类型生成刷新Token
        if (form.getType().equals("1")) {
            refreshToken = JwtTokenUtils.getRefreshToken(sysUserEntity.getId(), refreshTokenClaims);
        } else {
            refreshToken = JwtTokenUtils.getRefreshAppToken(sysUserEntity.getId(), refreshTokenClaims);
        }
        respVO.setAccessToken(accessToken);
        respVO.setRefreshToken(refreshToken);
        return respVO;
    }

    @Override
    public SysUserEntity queryById(String id) {
        SysUserEntity sysUserEntity = sysUserMapper.selectById(id);
        if (sysUserEntity == null) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
        }
        return sysUserEntity;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        String createId = (String) params.get("createId");

        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .eq(createId != null, "create_id", createId)
        );
        return new PageUtils(page);
    }

    @Override
    public void deleteBatch(String[] userIds) {
        //删除用户与角色的关联
        deleteUserRoles(userIds);

        //删除用户
        this.removeByIds(Arrays.asList(userIds));

        for (String userId : userIds) {
            //标记这些用户
            redisUtils.set(Constant.DELETED_USER_KEY + userId, userId, tokenSetting.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
            //清除用户shiro缓存
            redisUtils.delete(Constant.IDENTIFY_CACHE_KEY + userId);
        }
    }

    @Override
    @Transactional
    public void saveUser(SysUserEntity user) {
        user.setCreateTime(new Date());
        //盐值加密
        String salt = PasswordUtils.getSalt();
        String password = PasswordUtils.encode(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        //保存用户
        this.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色的关联
        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUserEntity user,String accessToken) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else {
            //盐值加密
            String salt = PasswordUtils.getSalt();
            String password = PasswordUtils.encode(user.getPassword(), salt);
            user.setSalt(salt);
            user.setPassword(password);
        }
        this.updateById(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());

        //标记用户，使得accessToken不能在访问系统
        redisUtils.set(Constant.JWT_ACCESS_TOKEN_UPDATE + accessToken,user.getId(),tokenSetting.getAccessTokenExpireTime().toMillis(),TimeUnit.MILLISECONDS);

        //删除shiro缓存
        redisUtils.delete(Constant.IDENTIFY_CACHE_KEY + user.getId());

    }

    @Override
    public boolean updatePassword(String password, String newPassword, String userId) {
        SysUserEntity user = baseMapper.selectById(userId);
        //原密码错误
        if (!PasswordUtils.matches(user.getSalt(),password,user.getPassword())){
            return false;
        }
        String encodePassword = PasswordUtils.encode(newPassword, user.getSalt());
        user.setPassword(encodePassword);
        return this.update(user,
                new QueryWrapper<SysUserEntity>().eq("id",userId));
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user) {
        //超级管理员拥有所有权限
        if (sysRoleService.isAdmin(user.getCreateId())) {
            return;
        }
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }

        //如果不是超级管理员，则需要判断新增的用户角色是否为当前用户创建的
        //查询用户创建的角色列表
        List<String> roleIdList = sysRoleService.queryRoleIdList(user.getCreateId());

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new BusinessException(BaseResponseCode.ROLE_ERROR);
        }
    }

    /**
     * 删除用户和角色的关联
     */
    public void deleteUserRoles(String[] userIds) {
        sysUserRoleService.deleteUserRoles(userIds);
    }

    private List<String> getRolesByUserId(String userId) {
        List<SysRoleEntity> rolesList;

        //判断是否是管理员
        boolean isAdmin = sysRoleService.isAdmin(userId);
        if (isAdmin) {
            rolesList = sysRoleService.getRolesById(null);
        } else {
            rolesList = sysRoleService.getRolesById(userId);
        }
        //遍历查询的角色
        List<String> roles = new ArrayList<>(rolesList.size());
        for (SysRoleEntity sysRoleEntity : rolesList) {
            if (sysRoleEntity.getName() != null) {
                roles.add(sysRoleEntity.getName());
            }
        }
        return roles;
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return sysMenuService.getUserPermissions(userId);
    }
}
