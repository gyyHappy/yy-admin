package com.gyy.modules.sys.controller;

import com.gyy.common.utils.JwtTokenUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 * @author GYY
 * @version 1.0
 * @date 2020/7/9 19:42
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 获取用户id
     */
    protected String getUserId() {
        String accessToken = (String) SecurityUtils.getSubject().getPrincipal();
        return JwtTokenUtils.getUserId(accessToken);
    }
}