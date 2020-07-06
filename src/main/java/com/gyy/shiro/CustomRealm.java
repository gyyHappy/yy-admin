package com.gyy.shiro;

import com.gyy.utils.RedisUtils;
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

    @Autowired
    private RedisUtils redisService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String token= (String) principalCollection.getPrimaryPrincipal();
        String userId= (String) redisService.get(token);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRoles(getRoleByUserId(userId));
        info.addStringPermissions(getPermissionsByUserId(userId));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomUsernamePasswordToken token= (CustomUsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(token.getPrincipal(),token.getCredentials(),getName());
        return info;
    }

    private List<String> getRoleByUserId(String userId){
        List<String> roles=new ArrayList<>();
        if(userId.equals("fcf34b56-a7a2-4719-9236-867495e74c31")){
            roles.add("admin");
        }
        roles.add("test");
        return roles;
    }

    private List<String> getPermissionsByUserId(String userId){
        List<String> permissions=new ArrayList<>();
        if(userId.equals("fcf34b56-a7a2-4719-9236-867495e74c31")){
            permissions.add("*");
        }
        permissions.add("user:list");
        permissions.add("user:edit");
        return permissions;
    }
}
