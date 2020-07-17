package com.gyy.modules.sys.controller;


import com.gyy.common.utils.R;
import com.gyy.modules.sys.entity.SysRoleEntity;
import com.gyy.modules.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

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

    @GetMapping("/role/select")
    @ApiOperation(value = "查询角色列表")
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
}
