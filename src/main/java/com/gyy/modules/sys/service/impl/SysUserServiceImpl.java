package com.gyy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gyy.common.constants.Constant;
import com.gyy.common.exception.BusinessException;
import com.gyy.common.exception.code.BaseResponseCode;
import com.gyy.common.utils.PageUtils;
import com.gyy.common.utils.Query;
import com.gyy.modules.sys.entity.SysRoleEntity;
import com.gyy.modules.sys.entity.SysUserEntity;
import com.gyy.modules.sys.form.SysLoginForm;
import com.gyy.modules.sys.mapper.SysUserMapper;
import com.gyy.modules.sys.service.SysMenuService;
import com.gyy.modules.sys.service.SysRoleService;
import com.gyy.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyy.modules.sys.vo.resp.LoginRespVO;
import com.gyy.common.utils.JwtTokenUtils;
import com.gyy.common.utils.PasswordUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
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

    @Override
    public LoginRespVO login(SysLoginForm form) {
        //通过用户名查询
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username",form.getUsername());
        wrapper.eq("deleted",1);
        SysUserEntity sysUserEntity = sysUserMapper.selectOne(wrapper);
        //判断用户信息
        if (sysUserEntity == null) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
        }
        if (sysUserEntity.getStatus() == 2){
            throw new BusinessException(BaseResponseCode.ACCOUNT_LOCK);
        }
        if (!PasswordUtils.matches(sysUserEntity.getSalt(),form.getPassword(), sysUserEntity.getPassword())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }

        LoginRespVO respVO = new LoginRespVO();
        //生成Token
        Map<String,Object> claims = new HashMap<>();
        claims.put(Constant.JWT_ROLES_KEY,getRolesByUserId(sysUserEntity.getId()));
        claims.put(Constant.JWT_PERMISSIONS_KEY,getPermissionsByUserId(sysUserEntity.getId()));
        claims.put(Constant.JWT_USER_NAME, sysUserEntity.getUsername());
        String accessToken= JwtTokenUtils.getAccessToken(sysUserEntity.getId(),claims);
        Map<String,Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put(Constant.JWT_USER_NAME, sysUserEntity.getUsername());
        String refreshToken;
        //根据类型生成刷新Token
        if(form.getType().equals("1")){
            refreshToken= JwtTokenUtils.getRefreshToken(sysUserEntity.getId(),refreshTokenClaims);
        }else {
            refreshToken= JwtTokenUtils.getRefreshAppToken(sysUserEntity.getId(),refreshTokenClaims);
        }
        respVO.setAccessToken(accessToken);
        respVO.setRefreshToken(refreshToken);
        return respVO;
    }

    @Override
    public SysUserEntity queryById(String id) {
        SysUserEntity sysUserEntity = sysUserMapper.selectById(id);
        if (sysUserEntity == null){
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
                .like(StringUtils.isNotBlank(username),"username",username)
                .eq(createId != null,"create_id",createId)
        );
        return new PageUtils(page);
    }

    private List<String> getRolesByUserId(String userId){
        List<SysRoleEntity> rolesList;

        //判断是否是管理员
        boolean isAdmin = sysRoleService.isAdmin(userId);
        if (isAdmin){
            rolesList = sysRoleService.getRolesById(null);
        }else {
            rolesList = sysRoleService.getRolesById(userId);
        }
        //遍历查询的角色
        List<String> roles = new ArrayList<>(rolesList.size());
        for (SysRoleEntity sysRoleEntity : rolesList) {
            if (sysRoleEntity.getName() != null){
                roles.add(sysRoleEntity.getName());
            }
        }
        return roles;
    }
    private Set<String> getPermissionsByUserId(String userId){
       return sysMenuService.getUserPermissions(userId);
    }
}
