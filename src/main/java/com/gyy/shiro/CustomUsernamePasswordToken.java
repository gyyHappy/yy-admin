package com.gyy.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义token，原生UsernamePasswordToken返回的token是用户名和密码的组合。
 * 自定义token，只要返回token进行校验
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 10:07
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {

    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public CustomUsernamePasswordToken(String token) {
        this.token = token;
    }
}
