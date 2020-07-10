package com.gyy.modules.sys.controller;


import com.gyy.modules.sys.entity.SysPermissionEntity;
import com.gyy.modules.sys.service.SysPermissionService;
import com.gyy.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  权限目录
 * </p>
 *
 * @author GYY
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "菜单权限模块")
public class SysPermissionController extends AbstractController{

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 获取侧边栏目录
     */
    @GetMapping("/perm/nav")
    @ApiOperation(value = "获取侧边栏目录")
    public R nav(){
        List<SysPermissionEntity> permissionList =  sysPermissionService.getUserMenuList(getUserId());
    }
}
