package com.gyy.modules.sys.controller;


import com.gyy.common.annotation.SysLog;
import com.gyy.common.exception.BusinessException;
import com.gyy.common.exception.code.BaseResponseCode;
import com.gyy.common.utils.PageUtils;
import com.gyy.common.utils.R;
import com.gyy.modules.sys.entity.SysUserEntity;
import com.gyy.modules.sys.service.SysRoleService;
import com.gyy.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @Autowired
    private SysRoleService sysRoleService;

//    @GetMapping("/user/{id}")
//    @ApiOperation(value = "获取用户详情接口")
//    @RequiresPermissions("sys:user:detail")
//    public R queryById(@PathVariable String id){
//        SysUserEntity sysUserEntity = sysUserService.queryById(id);
//        return R.ok(sysUserEntity);
//    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息")
    public R info(){
        SysUserEntity sysUserEntity = sysUserService.queryById(getUserId());
        return R.ok(sysUserEntity);
    }

    @GetMapping("/user/list")
    @RequiresPermissions("sys:user:list")
    @ApiOperation(value = "获取用户信息列表")
    public R list(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有用户列表
        if (!sysRoleService.isAdmin(getUserId())){
            params.put("createId", getUserId());
        }
        PageUtils page = sysUserService.queryPage(params);

        return R.ok(page);
    }

    @SysLog("删除用户")
    @PostMapping("/user/delete")
    @RequiresPermissions("sys:user:delete")
    @ApiOperation(value = "删除用户")
    public R delete(@RequestBody String[] userIds){
        verify(userIds);
        //TODO
    }

    public void verify(String[] userIds){
        for (String userId : userIds) {
            //管理员不能删除
            if (sysRoleService.isAdmin(userId)){
                throw new BusinessException(BaseResponseCode.ACCOUNT_DELETED_ADMIN_ERROR);
            }

        }
    }
}
