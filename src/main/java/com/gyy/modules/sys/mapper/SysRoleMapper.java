package com.gyy.modules.sys.mapper;

import com.gyy.modules.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GYY
 * @since 2020-07-10
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    /**
     * 通过用户id查询用户角色
     */
    List<SysRoleEntity> queryRolesByUserId(String userId);

    /**
     * 查询用户创建的角色列表
     */
    List<String> queryRoleIdList(String createId);
}
