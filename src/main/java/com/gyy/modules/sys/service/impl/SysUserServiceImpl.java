package com.gyy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyy.constants.Constant;
import com.gyy.exception.BusinessException;
import com.gyy.exception.code.BaseResponseCode;
import com.gyy.modules.sys.entity.SysUserEntity;
import com.gyy.modules.sys.form.SysLoginForm;
import com.gyy.modules.sys.mapper.SysUserMapper;
import com.gyy.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyy.modules.sys.vo.resp.LoginRespVO;
import com.gyy.utils.JwtTokenUtil;
import com.gyy.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        respVO.setId(sysUser.getId());
//        respVO.setPhone(sysUser.getPhone());
//        respVO.setUsername(sysUser.getUsername());
        //生成Token
        Map<String,Object> claims = new HashMap<>();
        claims.put(Constant.JWT_ROLES_KEY,getRolesByUserId(sysUserEntity.getId()));
        claims.put(Constant.JWT_PERMISSIONS_KEY,getPermissionsByUserId(sysUserEntity.getId()));
        claims.put(Constant.JWT_USER_NAME, sysUserEntity.getUsername());
        String accessToken= JwtTokenUtil.getAccessToken(sysUserEntity.getId(),claims);
        Map<String,Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put(Constant.JWT_USER_NAME, sysUserEntity.getUsername());
        String refreshToken;
        //根据类型生成刷新Token
        if(form.getType().equals("1")){
            refreshToken=JwtTokenUtil.getRefreshToken(sysUserEntity.getId(),refreshTokenClaims);
        }else {
            refreshToken= JwtTokenUtil.getRefreshAppToken(sysUserEntity.getId(),refreshTokenClaims);
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

    private List<String> getRolesByUserId(String userId){

        List<String> roles=new ArrayList<>();
        if("fcf34b56-a7a2-4719-9236-867495e74c31".equals(userId)){
            roles.add("admin");
        }else {
            roles.add("test");
        }
        return roles;
    }
    private List<String> getPermissionsByUserId(String userId){
        List<String> permissions=new ArrayList<>();
        if("fcf34b56-a7a2-4719-9236-867495e74c31".equals(userId)){
            permissions.add("sys:user:list");
            permissions.add("sys:user:add");
            permissions.add("sys:user:update");
            permissions.add("sys:user:detail");
        }else {
            permissions.add("sys:user:add");
        }
        return permissions;
    }
}
