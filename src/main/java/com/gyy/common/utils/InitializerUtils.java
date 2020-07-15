package com.gyy.common.utils;

import org.springframework.stereotype.Component;

/**
 * 初始化 将配置文件的值注入 JwtUtil中
 * @author GYY
 * @version 1.0
 * @date 2020/7/8 11:16
 */
@Component
public class InitializerUtils {

    public InitializerUtils(TokenSetting tokenSetting){
        JwtTokenUtils.setTokenSettings(tokenSetting);
    }
}