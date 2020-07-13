package com.gyy.modules.sys.service.impl;

import com.gyy.modules.sys.entity.SysRoleMenuEntity;
import com.gyy.modules.sys.mapper.SysRoleMenuMapper;
import com.gyy.modules.sys.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  SysRoleMenuServiceImpl 服务实现类
 * </p>
 *
 * @author GYY
 * @since 2020-07-13
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public void deleteByMenuId(String id) {
        sysRoleMenuMapper.deleteByMenuId(id);
    }
}
