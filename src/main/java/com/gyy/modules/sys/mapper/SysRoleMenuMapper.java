package com.gyy.modules.sys.mapper;

import com.gyy.modules.sys.entity.SysRoleMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

}
