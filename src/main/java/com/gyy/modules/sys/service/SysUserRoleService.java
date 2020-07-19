package com.gyy.modules.sys.service;

import com.gyy.modules.sys.entity.SysUserRoleEntity;
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
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    /**
     * 删除用户与角色的关联
     */
    void deleteUserRoles(String[] userIds);

    /**
     * 修改或保存用户与角色的关联
     */
    void saveOrUpdate(String userId, List<String> roleIdList);

    /**
     * 删除用户与角色的关联
     */
    int deleteBatch(String[] roleIds);

    /**
     * 获取与角色相关的用户id
     */
    List<String> getUserId(String[] roleIds);

    /**
     * 获取用户所拥有的角色id
     */
    List<String> queryRoleIdList(String userId);
}
