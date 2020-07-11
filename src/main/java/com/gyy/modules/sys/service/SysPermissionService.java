package com.gyy.modules.sys.service;

import com.gyy.modules.sys.entity.SysPermissionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GYY
 * @since 2020-07-10
 */
public interface SysPermissionService extends IService<SysPermissionEntity> {

    /**
     * 获取菜单-权限
     */
    List<SysPermissionEntity> getUserMenuList(String userId);

    /**
     * 获取用户所有权限
     */
    Set<String> getUserPermissions(String userId);
}
