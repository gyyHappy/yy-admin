package com.gyy.modules.sys.controller;


import com.gyy.common.utils.PageUtils;
import com.gyy.common.utils.R;
import com.gyy.modules.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author GYY
 * @since 2020-07-15
 */
@RestController
@RequestMapping("/sys")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/log/list")
    @RequiresPermissions("sys:log:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysLogService.queryPage(params);

        return R.ok(page);
    }
}
