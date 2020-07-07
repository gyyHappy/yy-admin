package com.gyy.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyy.exception.BusinessException;
import com.gyy.modules.sys.entity.SysUser;
import com.gyy.modules.sys.mapper.SysUserMapper;
import com.gyy.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GYY
 * @since 2020-07-06
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser queryByUsername(String username) {
        if (StrUtil.isBlank(username)){
            throw new BusinessException(4000001,"用户名不能为空");
        }
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username));
    }
}
