package com.gyy.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

/**
 * <p>
 * 
 * </p>
 *
 * @author GYY
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="User对象", description="")
public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "账户名称")
    private String username;

    @ApiModelProperty(value = "加密盐值")
    private String salt;

    @ApiModelProperty(value = "用户密码密文")
    private String password;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "真实名称")
    private String realName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱(唯一)")
    private String email;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定 )")
    private Integer status;

    @ApiModelProperty(value = "性别(1.男 2.女)")
    private Integer sex;

    @ApiModelProperty(value = "是否删除(1未删除；0已删除)")
    private Integer deleted;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String updateId;

    @ApiModelProperty(value = "创建来源(1.web 2.android 3.ios )")
    private Integer createWhere;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
