package com.gyy.modules.sys.service;

import com.gyy.modules.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gyy.modules.sys.form.SysLoginForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GYY
 * @since 2020-07-06
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名查找用户
     */
    SysUser queryByUsername(String username);
}
