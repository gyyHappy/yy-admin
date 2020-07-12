package com.gyy.modules.sys.controller;


import com.gyy.modules.sys.entity.SysUserEntity;
import com.gyy.modules.sys.service.SysUserService;
import com.gyy.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GYY
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "用户模块")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/user/{id}")
    @ApiOperation(value = "获取用户详情接口")
    @RequiresPermissions("sys:user:detail")
    public R queryById(@PathVariable String id){
        SysUserEntity sysUserEntity = sysUserService.queryById(id);
        return R.ok(sysUserEntity);
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息")
    public R info(){
        SysUserEntity sysUserEntity = sysUserService.queryById(getUserId());
        return R.ok(sysUserEntity);
    }
}
