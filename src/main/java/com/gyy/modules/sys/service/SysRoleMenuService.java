package com.gyy.modules.sys.service;

import com.gyy.modules.sys.entity.SysRoleMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 查询角色对应的菜单id
     */
    List<String> queryMenuIds(String id);

    /**
     * 保存角色与菜单关系
     */
    void saveOrUpdate(String roleId, List<String> menuIdList);

    /**
     * 查询用户拥有的菜单id
     */
    List<String> queryAllMenuId(String userId);
}
