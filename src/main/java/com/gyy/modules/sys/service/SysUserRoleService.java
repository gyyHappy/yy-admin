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


    void saveOrUpdate(String userId, List<String> roleIdList);
}
