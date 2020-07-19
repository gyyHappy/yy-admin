package com.gyy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gyy.common.constants.Constant;
import com.gyy.common.exception.BusinessException;
import com.gyy.common.exception.code.BaseResponseCode;
import com.gyy.common.utils.PageUtils;
import com.gyy.common.utils.Query;
import com.gyy.common.utils.RedisUtils;
import com.gyy.common.utils.TokenSetting;
import com.gyy.modules.sys.entity.SysRoleEntity;
import com.gyy.modules.sys.mapper.SysRoleMapper;
import com.gyy.modules.sys.service.SysRoleMenuService;
import com.gyy.modules.sys.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyy.modules.sys.service.SysUserRoleService;
import com.gyy.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GYY
 * @since 2020-07-10
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TokenSetting tokenSetting;

    @Override
    public boolean isAdmin(String userId) {
        List<SysRoleEntity> sysRoleEntityList = sysRoleMapper.queryRolesByUserId(userId);
        for (SysRoleEntity sysRoleEntity : sysRoleEntityList) {
            if (sysRoleEntity.getName().equals(Constant.ADMIN_ACCOUNT)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SysRoleEntity> getRolesById(String userId) {
        if (userId == null){
            return baseMapper.selectList(null);
        }
        return sysRoleMapper.queryRolesByUserId(userId);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        String createUserId = (String) params.get("createUserId");

        IPage<SysRoleEntity> page = this.page(
                new Query<SysRoleEntity>().getPage(params),
                new QueryWrapper<SysRoleEntity>()
                    .like(StringUtils.isNotBlank(roleName),"name",roleName)
                    .eq(StringUtils.isNotBlank(createUserId),"create_user_id",createUserId)
        );
        return new PageUtils(page);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity role) {
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());

        this.save(role);

        //检查是否越权
        checkPrems(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单的关联
        sysRoleMenuService.deleteBatch(roleIds);

        //标记用户和清除shiro缓存
        refreshTagsUser(roleIds);

        //删除角色与用户的关联
        sysUserRoleService.deleteBatch(roleIds);


    }

    @Override
    public List<String> queryRoleIdList(String createId) {
        return baseMapper.queryRoleIdList(createId);
    }

    @Override
    public void update(SysRoleEntity role) {
        //修改角色
        this.updateById(role);

        //检查是否越权
        checkPrems(role);

        //标记用户和清除shiro缓存
        refreshTagsUser(new String[]{role.getId()});

        //更新角色与菜单的关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    /**
     * 标记用户和清除shiro缓存
     */
    private void refreshTagsUser(String[] roleIds) {
        //查询与角色关联的用户id
        List<String> userList = sysUserRoleService.getUserId(roleIds);
        Set<String> userSet = new HashSet<>(userList);

        for (String userId : userSet) {
            //标记用户
            redisUtils.set(Constant.JWT_REFRESH_KEY + userId,userId,tokenSetting.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);

            //删除shiro缓存
            redisUtils.delete(Constant.IDENTIFY_CACHE_KEY + userId);
        }
    }

    private void checkPrems(SysRoleEntity role) {
        //如果不是超级管理员则要判断是否越权
        if (isAdmin(role.getCreateUserId())){
            return;
        }

        //查询用户拥有的权限列表
        List<String> menuIdList = sysRoleMenuService.queryAllMenuId(role.getCreateUserId());

        //判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())){
            throw new BusinessException(BaseResponseCode.MENU_ERROR);
        }
    }

}
