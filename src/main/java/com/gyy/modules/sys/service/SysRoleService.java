package com.gyy.modules.sys.service;

import com.gyy.modules.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GYY
 * @since 2020-07-10
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 判断是不是管理员
     */
    boolean isAdmin(String userId);
}
