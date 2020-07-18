package com.gyy.modules.sys.mapper;

import com.gyy.modules.sys.entity.SysRoleMenuEntity;
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
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {


    void deleteByMenuId(String id);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<String> queryMenuIds(String id);

    /**
     * 根据角色id 批量删除
     */
    int deleteBatch(String[] roleIds);

    /**
     * 根据用户id查询拥有的菜单id
     */
    List<String> queryAllMenuId(String userId);
}
