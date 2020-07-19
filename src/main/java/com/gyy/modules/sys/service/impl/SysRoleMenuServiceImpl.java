package com.gyy.modules.sys.service.impl;

import com.gyy.modules.sys.entity.SysRoleMenuEntity;
import com.gyy.modules.sys.mapper.SysRoleMenuMapper;
import com.gyy.modules.sys.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<String> queryMenuIds(String id) {
        return baseMapper.queryMenuIds(id);
    }

    @Override
    public void saveOrUpdate(String roleId, List<String> menuIdList) {
        //先删除角色与菜单的关联
        deleteBatch(new String[]{roleId});

        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        for(String menuId : menuIdList){
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);
            sysRoleMenuEntity.setCreateTime(new Date());

            this.save(sysRoleMenuEntity);
        }
    }

    @Override
    public List<String> queryAllMenuId(String userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public int deleteBatch(String[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
