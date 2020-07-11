package com.gyy.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gyy.constants.Constant;
import com.gyy.modules.sys.entity.SysPermissionEntity;
import com.gyy.modules.sys.mapper.SysPermissionMapper;
import com.gyy.modules.sys.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyy.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GYY
 * @since 2020-07-10
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermissionEntity> implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取菜单 1
     */
    @Override
    public List<SysPermissionEntity> getUserMenuList(String userId) {
        //超级管理员，拥有最高权限
        if (sysRoleService.isAdmin(userId)) {
            return getAllMenuList(null);
        }
        //普通用户,获取所拥有的菜单id
        List<String> menuIds = sysPermissionMapper.queryMenuIds(userId);
        return getAllMenuList(menuIds);
    }

    /**
     * 获取菜单 2
     */
    private List<SysPermissionEntity> getAllMenuList(List<String> menuIds) {
        //查询根菜单列表
        List<SysPermissionEntity> menuList = queryListParentId("0", menuIds);
        //递归获取子菜单
        getMenuTreeList(menuList,menuIds);
        return menuList;
    }

    /**
     * 获取菜单 3
     */
    private List<SysPermissionEntity> queryListParentId(String parentId, List<String> menuIds) {
        List<SysPermissionEntity> menuList = baseMapper.queryParentId(parentId);
        //超级管理员
        if (menuIds == null) {
            return menuList;
        }
        //普通用户
        List<SysPermissionEntity> userMenuList = new ArrayList<>();
        for (SysPermissionEntity entity : menuList) {
            if (menuIds.contains(entity.getId())) {
                userMenuList.add(entity);
            }
        }
        return userMenuList;
    }

    /**
     * 递归获取子菜单 4
     */
    private List<SysPermissionEntity> getMenuTreeList(List<SysPermissionEntity> menuList, List<String> menuIds) {
        List<SysPermissionEntity> subMenuList =  new ArrayList<>();
        for (SysPermissionEntity entity : menuList) {
            //判断是否为目录，是则递归查询子菜单
            if (entity.getType().equals(Constant.CATALOG)){
                entity.setList(getMenuTreeList(queryListParentId(entity.getId(),menuIds),menuIds));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }

    /**
     * 获取用户所有权限
     */
    @Override
    public Set<String> getUserPermissions(String userId) {
        List<String> permList;
        //超级管理员拥有所有权限
        if (sysRoleService.isAdmin(userId)){
            List<SysPermissionEntity> menuList = baseMapper.selectList(null);
            permList = new ArrayList<>(menuList.size());
            for (SysPermissionEntity entity : menuList) {
                permList.add(entity.getPerms());
            }
        }else {
            permList = sysPermissionMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permSet = new HashSet<>();
        for (String perm : permList) {
            if (StrUtil.isBlank(perm)){
                continue;
            }
            permSet.addAll(Arrays.asList(perm.trim().split(",")));
        }
        return permSet;
    }
}
