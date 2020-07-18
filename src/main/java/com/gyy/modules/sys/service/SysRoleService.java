package com.gyy.modules.sys.service;

import com.gyy.common.utils.PageUtils;
import com.gyy.modules.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询角色列表
     */
    PageUtils queryPage(Map<String, Object> params);


    /**
     * 保存角色
     */
    void saveRole(SysRoleEntity role);
}
