package com.gyy.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author GYY
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel(value="SysPermissionEntity对象", description="")
public class SysPermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "菜单权限编码")
    private String code;

    @ApiModelProperty(value = "菜单权限名称")
    private String name;

    @ApiModelProperty(value = "授权(如：sys:user:add)")
    private String perms;

    @ApiModelProperty(value = "访问地址URL")
    private String url;

    @ApiModelProperty(value = "资源请求类型")
    private String method;

    @ApiModelProperty(value = "父级菜单权限名称")
    private String pid;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "菜单权限类型(1:目录;2:菜单;3:按钮)")
    private Integer type;

    @ApiModelProperty(value = "状态1:正常 0：禁用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * ztree属性
     */
    @TableField(exist=false)
    private Boolean open;

    @TableField(exist=false)
    private List<?> list;
}
