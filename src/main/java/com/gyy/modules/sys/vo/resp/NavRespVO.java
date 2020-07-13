package com.gyy.modules.sys.vo.resp;

import com.gyy.modules.sys.entity.SysMenuEntity;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author GYY
 * @version 1.0
 * @date 2020/7/11 11:19
 */
@Data
public class NavRespVO {

    /**
     * 目录
     */
    private List<SysMenuEntity> menuList;

    /**
     * 权限
     */
    private Set<String> permissions;
}
