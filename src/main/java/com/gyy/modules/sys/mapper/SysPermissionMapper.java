package com.gyy.modules.sys.mapper;

import com.gyy.modules.sys.entity.SysPermissionEntity;
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
public interface SysPermissionMapper extends BaseMapper<SysPermissionEntity> {

    /**
     * 获取用户拥有的所有菜单id
     */
    List<String> queryMenuIds(String userId);

    /**
     * 通过父id查询菜单
     */
    List<SysPermissionEntity> queryParentId(String parentId);

    /**
     * 通过id查询所有权限
     */
    List<String> queryAllPerms(String userId);
}
