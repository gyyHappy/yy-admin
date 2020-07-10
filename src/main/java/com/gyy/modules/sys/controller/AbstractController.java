package com.gyy.modules.sys.controller;

import com.gyy.modules.sys.entity.SysUserEntity;
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
     * 获取用户
     */
    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取用户id
     */
    protected String getUserId() {
        return getUser().getId();
    }
}