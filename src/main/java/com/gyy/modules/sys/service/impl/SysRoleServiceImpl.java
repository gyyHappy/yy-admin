package com.gyy.modules.sys.service.impl;

import com.gyy.constants.Constant;
import com.gyy.modules.sys.entity.SysRoleEntity;
import com.gyy.modules.sys.mapper.SysRoleMapper;
import com.gyy.modules.sys.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
