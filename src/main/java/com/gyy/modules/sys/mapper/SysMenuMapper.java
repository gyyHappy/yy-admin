package com.gyy.modules.sys.mapper;

import com.gyy.modules.sys.entity.SysMenuEntity;
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
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    /**
     * 获取用户拥有的所有菜单id
     */
    List<String> queryMenuIds(String userId);

    /**
     * 通过父id查询菜单
     */
    List<SysMenuEntity> queryParentId(String parentId);

    /**
     * 通过id查询所有权限
     */
    List<String> queryAllPerms(String userId);

    /**
     * 查询不包含列表的按钮
     */
    List<SysMenuEntity> queryNotButtonList();
}
