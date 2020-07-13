package com.gyy.modules.sys.controller;


import com.gyy.modules.sys.entity.SysMenuEntity;
import com.gyy.modules.sys.service.SysMenuService;
import com.gyy.modules.sys.vo.resp.NavRespVO;
import com.gyy.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限目录
 * </p>
 *
 * @author GYY
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "菜单权限模块")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取侧边栏目录
     */
    @GetMapping("/menu/nav")
    @ApiOperation(value = "获取侧边栏目录")
    public R nav() {
        //获取菜单栏
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
        //获取拥有的权限
        Set<String> permissions = sysMenuService.getUserPermissions(getUserId());
        NavRespVO navRespVO = new NavRespVO();
        navRespVO.setMenuList(menuList);
        navRespVO.setPermissions(permissions);
        return R.ok(navRespVO);
    }

    /**
     * 查询所有菜单列表
     */
    @GetMapping("/menu/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList = sysMenuService.list();
        for (SysMenuEntity sysMenuEntity : menuList) {
            SysMenuEntity parentEntity = sysMenuService.getById(sysMenuEntity.getParentId());
            if (parentEntity != null) {
                sysMenuEntity.setParentName(parentEntity.getName());
            }
        }
        return menuList;
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/menu/delete/{id}")
    @RequiresPermissions("sys:menu:delete")
    public R delete(@PathVariable String id) {
        List<SysMenuEntity> list = sysMenuService.queryListParentId(id);
        if (list.size() > 0) {
            return R.error("请先删除子菜单，或者按钮");
        }
        sysMenuService.delete(id);
        return R.ok();
    }
}
