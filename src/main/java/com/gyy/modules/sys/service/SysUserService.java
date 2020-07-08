package com.gyy.modules.sys.service;

import com.gyy.modules.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gyy.modules.sys.form.SysLoginForm;
import com.gyy.modules.sys.vo.LoginRespVO;

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
     * 登录，并生成token
     */
    LoginRespVO login(SysLoginForm form);

    /**
     * 获取用户详情接口
     */
    SysUser queryById(String id);
}
