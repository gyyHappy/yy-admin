package com.gyy.modules.sys.service;

import com.gyy.modules.sys.entity.SysRoleMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GYY
 * @since 2020-07-13
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

    /**
     * 删除菜单与角色关联信息
     */
    void deleteByMenuId(String id);
}
