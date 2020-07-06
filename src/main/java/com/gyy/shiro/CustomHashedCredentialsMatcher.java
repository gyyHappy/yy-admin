package com.gyy.shiro;

import com.gyy.exception.BusinessException;
import com.gyy.utils.RedisUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义校验器
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 10:27
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) token;
        String sessionId = (String) customUsernamePasswordToken.getPrincipal();
        if (!redisUtils.hasKey(sessionId)){
            throw new BusinessException(4010002,"授权信息已经过期");
        }
        return true;
    }
}
