package com.gyy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gyy.common.constants.Constant;
import com.gyy.common.exception.BusinessException;
import com.gyy.common.exception.code.BaseResponseCode;
import com.gyy.common.utils.PageUtils;
import com.gyy.common.utils.Query;
import com.gyy.modules.sys.entity.SysRoleEntity;
import com.gyy.modules.sys.mapper.SysRoleMapper;
import com.gyy.modules.sys.service.SysRoleMenuService;
import com.gyy.modules.sys.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyy.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public List<String> queryRoleIdList(String createId) {
        return sysRoleMapper.queryRoleIdList(createId);
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
