package com.gyy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyy.modules.sys.entity.SysUserRoleEntity;
import com.gyy.modules.sys.mapper.SysUserRoleMapper;
import com.gyy.modules.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GYY
 * @since 2020-07-13
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {


    @Override
    public void deleteUserRoles(String[] userIds) {
        QueryWrapper<SysUserRoleEntity> wrapper = new QueryWrapper<>();
        for (String userId : userIds) {
            if (null != userId){
                baseMapper.delete(wrapper.eq("user_id",userId));
            }
        }
    }

    @Override
    public void saveOrUpdate(String userId, List<String> roleIdList) {
        //先删除用户与角色的关联
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",userId);
        this.removeByMap(map);

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }

        //保存用户与角色关系
        for (String roleId : roleIdList) {
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setCreateTime(new Date());
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);

            this.save(sysUserRoleEntity);
        }
    }

    @Override
    public int deleteBatch(String[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }

    @Override
    public List<String> getUserId(String[] roleIds) {
        return baseMapper.getUserId(roleIds);
    }

    @Override
    public List<String> queryRoleIdList(String userId) {
        return baseMapper.queryRoleIdList(userId);
    }
}
