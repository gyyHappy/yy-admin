package com.gyy.modules.sys.service;

import com.gyy.modules.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 获取用户角色
     */
    List<SysRoleEntity> getRolesById(String userId);


    /**
     * 查询用户创建的角色ID列表
     */
    List<String> queryRoleIdList(String createId);
}
