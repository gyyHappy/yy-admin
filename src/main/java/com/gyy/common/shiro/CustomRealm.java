package com.gyy.common.shiro;

import com.gyy.common.constants.Constant;
import com.gyy.common.utils.JwtTokenUtils;
import com.gyy.common.utils.RedisUtils;
import com.gyy.modules.sys.entity.SysRoleEntity;
import com.gyy.modules.sys.service.SysMenuService;
import com.gyy.modules.sys.service.SysRoleService;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 11:37
 */
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomUsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String accessToken = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String userId = JwtTokenUtils.getUserId(accessToken);
        /*
         * 通过剩余的过期时间比较如果token的剩余过期时间大与标记key的剩余过期时间
         * 就说明这个tokne是在这个标记key之后生成的
         */
        if (redisUtils.hasKey(Constant.JWT_REFRESH_KEY + userId) && redisUtils.getExpire(Constant.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS) > JwtTokenUtils.getRemainingTime(accessToken)) {
            List<SysRoleEntity> roles = sysRoleService.getRolesById(userId);
            List<String> roleNames = new ArrayList<>(roles.size());
            for (SysRoleEntity role : roles) {
                roleNames.add(role.getName());
            }
            if (roleNames != null && !roleNames.isEmpty()) {
                info.addRoles(roleNames);
            }
            Set<String> permissions = sysMenuService.getUserPermissions(userId);
            if (permissions != null) {
                info.addStringPermissions(permissions);
            }

        } else {
            Claims claims = JwtTokenUtils.getClaimsFromToken(accessToken);
            /*
             * 返回该用户的角色信息给授权期
             */
            if (claims.get(Constant.JWT_ROLES_KEY) != null) {
                info.addRoles((Collection<String>) claims.get(Constant.JWT_ROLES_KEY));
            }

            /*
             * 返回该用户的权限信息给授权器
             */
            if (claims.get(Constant.JWT_PERMISSIONS_KEY) != null) {
                info.addStringPermissions((Collection<String>) claims.get(Constant.JWT_PERMISSIONS_KEY));
            }
        }


        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(customUsernamePasswordToken.getPrincipal(), customUsernamePasswordToken.getCredentials(), CustomRealm.class.getName());
        return info;
    }
}
