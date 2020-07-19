package com.gyy.modules.sys.mapper;

import com.gyy.modules.sys.entity.SysUserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GYY
 * @since 2020-07-13
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {

    /**
     * 删除用户与角色的关联
     */
    int deleteBatch(String[] roleIds);

    /**
     * 查询与角色关联的用户id
     */
    List<String> getUserId(String[] roleIds);

    /**
     * 查询用户拥有的角色id
     */
    List<String> queryRoleIdList(String userId);
}
