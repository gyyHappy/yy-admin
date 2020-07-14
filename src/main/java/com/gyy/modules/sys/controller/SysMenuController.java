package com.gyy.modules.sys.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gyy.constants.Constant;
import com.gyy.exception.BusinessException;
import com.gyy.exception.code.BaseResponseCode;
import com.gyy.modules.sys.entity.SysMenuEntity;
import com.gyy.modules.sys.service.SysMenuService;
import com.gyy.modules.sys.vo.resp.NavRespVO;
import com.gyy.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    /**
     * 菜单信息
     */
    @GetMapping("/menu/info/{id}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable String id) {
        SysMenuEntity menu = sysMenuService.getById(id);
        return R.ok(menu);
    }

    /**
     * 选择菜单 （新增/修改）
     */
    @GetMapping("/menu/select")
    @RequiresPermissions("sys:menu:select")
    public R select() {
        //查询不包含按钮的列表
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setId("0");
        root.setName("一级菜单");
        root.setParentId("-1");
        root.setOpen(true);
        menuList.add(root);

        return R.ok(menuList);
    }

    /**
     * 保存
     */
    @PostMapping("/menu/save")
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody SysMenuEntity menu) {
        //验证表单数据
        verifyForm(menu);
        //保存
        sysMenuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/menu/update")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenuEntity menu){
        //数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);

        return R.ok();
    }


    private void verifyForm(SysMenuEntity menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new BusinessException(BaseResponseCode.METHOD_IDENTITY_ERROR.getCode(), "菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new BusinessException(BaseResponseCode.METHOD_IDENTITY_ERROR.getCode(), "上级菜单不能为空");
        }

        //菜单
        if (menu.getType().equals(Constant.MENU)) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new BusinessException(BaseResponseCode.METHOD_IDENTITY_ERROR.getCode(), "菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.CATALOG;
        if (!menu.getParentId().equals("0")) {
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType().equals(Constant.CATALOG) ||
                menu.getType().equals(Constant.MENU)) {
            if (parentType != Constant.CATALOG) {
                throw new BusinessException(BaseResponseCode.METHOD_IDENTITY_ERROR.getCode(), "上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType().equals(Constant.BUTTON)) {
            if (parentType != Constant.MENU) {
                throw new BusinessException(BaseResponseCode.METHOD_IDENTITY_ERROR.getCode(), "上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
