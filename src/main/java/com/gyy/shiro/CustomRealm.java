package com.gyy.shiro;

import com.gyy.constants.Constant;
import com.gyy.utils.JwtTokenUtil;
import com.gyy.utils.RedisUtils;
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

/**
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 11:37
 */
public class CustomRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomUsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String accessToken = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Claims claims = JwtTokenUtil.getClaimsFromToken(accessToken);
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

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(customUsernamePasswordToken.getPrincipal(), customUsernamePasswordToken.getCredentials(), CustomRealm.class.getName());
        return info;
    }
}
