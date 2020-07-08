package com.gyy.utils;

import org.springframework.stereotype.Component;

/**
 * 初始化 将配置文件的值注入 JwtUtil中
 * @author GYY
 * @version 1.0
 * @date 2020/7/8 11:16
 */
@Component
public class InitializerUtil {

    public InitializerUtil(TokenSetting tokenSetting){
        JwtTokenUtil.setTokenSettings(tokenSetting);
    }
}