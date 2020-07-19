package com.gyy.modules.sys.controller;


import com.gyy.common.annotation.SysLog;
import com.gyy.common.utils.PageUtils;
import com.gyy.common.utils.R;
import com.gyy.modules.sys.entity.SysRoleEntity;
import com.gyy.modules.sys.service.SysRoleMenuService;
import com.gyy.modules.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GYY
 * @since 2020-07-10
 */
@RestController
@Api(tags = "角色模块")
@RequestMapping("/sys")
public class SysRoleController extends AbstractController{

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @GetMapping("/role/select")
    @ApiOperation(value = "查询角色列表,用于修改用户或新增用户")
    @RequiresPermissions("sys:role:select")
    public R select(){
        HashMap<String, Object> hashMap = new HashMap<>();

        //如果不是管理员，只查询和自己所拥有的角色列表
        if (!sysRoleService.isAdmin(getUserId())){
            hashMap.put("create_user_id",getUserId());
        }

        List<SysRoleEntity> list = sysRoleService.listByMap(hashMap);

        return R.ok(list);
    }

    @GetMapping("/role/list")
    @ApiOperation(value = "查询角色列表")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String,Object> params){
        //如果不是超级管理员，就只查询自己所拥有的角色列表
        if (!sysRoleService.isAdmin(getUserId())){
            params.put("createUserId",getUserId());
        }

        PageUtils page = sysRoleService.queryPage(params);

        return R.ok(page);
    }

    @GetMapping("/role/info/{id}")
    @ApiOperation(value = "通过id查询角色信息")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable String id){
        SysRoleEntity sysRoleEntity = sysRoleService.getById(id);

        //查询角色对应的菜单
        List<String> menuIds = sysRoleMenuService.queryMenuIds(id);
        sysRoleEntity.setMenuIdList(menuIds);

        return R.ok(sysRoleEntity);
    }

    @SysLog("保存角色")
    @PostMapping("/role/save")
    @ApiOperation(value = "保存角色")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody @Valid SysRoleEntity role){
        role.setCreateUserId(getUserId());
        sysRoleService.saveRole(role);

        return R.ok();
    }

    @SysLog("修改角色")
    @PostMapping("/role/update")
    @ApiOperation(value = "修改角色")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody @Valid SysRoleEntity role){

        role.setCreateUserId(getUserId());
        sysRoleService.update(role);

        return R.ok();
    }

    @SysLog("删除角色")
    @PostMapping("/role/delete")
    @RequiresPermissions("sys:role:delete")
    @ApiOperation(value = "删除角色")
    public R delete(@RequestBody String[] roleIds){
        sysRoleService.deleteBatch(roleIds);

        return R.ok();
    }
}
