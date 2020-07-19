package com.gyy.modules.sys.controller;


import com.gyy.common.annotation.SysLog;
import com.gyy.common.constants.Constant;
import com.gyy.common.exception.BusinessException;
import com.gyy.common.exception.code.BaseResponseCode;
import com.gyy.common.utils.PageUtils;
import com.gyy.common.utils.R;
import com.gyy.modules.sys.entity.SysUserEntity;
import com.gyy.modules.sys.form.PasswordForm;
import com.gyy.modules.sys.service.SysRoleService;
import com.gyy.modules.sys.service.SysUserRoleService;
import com.gyy.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
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

    @Autowired
    private SysUserRoleService sysUserRoleService;

//    @GetMapping("/user/{id}")
//    @ApiOperation(value = "获取用户详情接口")
//    @RequiresPermissions("sys:user:detail")
//    public R queryById(@PathVariable String id){
//        SysUserEntity sysUserEntity = sysUserService.queryById(id);
//        return R.ok(sysUserEntity);
//    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取登录用户信息")
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
        //验证删除用户
        verify(userIds);

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }

    @SysLog("修改用户信息")
    @PostMapping("/user/update")
    @RequiresPermissions("sys:user:update")
    @ApiOperation(value = "修改用户信息")
    public R update(@RequestBody @Valid SysUserEntity user){
        //或许这个字段应该叫做当前操作者比较好一些,这里用到createId是因为后面要判断是否越权
        user.setCreateId(getUserId());
        sysUserService.update(user);

        return R.ok();
    }


    @SysLog("新增用户")
    @PostMapping("/user/save")
    @RequiresPermissions("sys:user:save")
    @ApiOperation(value = "新增用户")
    public R save(@RequestBody @Valid SysUserEntity user){
        user.setCreateId(getUserId());

        sysUserService.saveUser(user);

        return R.ok();
    }

    @GetMapping("/user/info/{id}")
    @ApiOperation(value = "获取用户详细信息")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable String id){
        SysUserEntity user = sysUserService.getById(id);

        //获取用户所属角色列表
        List<String> roleList = sysUserRoleService.queryRoleIdList(id);
        user.setRoleIdList(roleList);

        return R.ok(user);
    }

    @PostMapping("/user/password")
    @ApiOperation(value = "修改密码")
    public R password(@RequestBody @Valid PasswordForm form){
        boolean flag = sysUserService.updatePassword(form.getPassword(),form.getNewPassword(),getUserId());
        if (!flag){
            return R.error("原密码错误");
        }
        return R.ok();
    }


    /**
     * 验证删除用户
     */
    public void verify(String[] userIds){
        for (String userId : userIds) {
            //管理员不能删除
            if (sysRoleService.isAdmin(userId)){
                throw new BusinessException(BaseResponseCode.ACCOUNT_DELETED_ADMIN_ERROR);
            }
            //不能删除当前操作用户
            if (userId.equals(getUserId())){
                throw new BusinessException(BaseResponseCode.ACCOUNT_DELETED_ERROR);
            }
        }
    }



}
