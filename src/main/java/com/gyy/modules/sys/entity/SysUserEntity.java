package com.gyy.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "加密盐值")
    private String salt;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "用户密码密文")
    private String password;

    @NotBlank(message = "手机号码不能为空")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @NotBlank(message = "真实名称不能为空")
    @ApiModelProperty(value = "真实名称")
    private String realName;

    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱(唯一)")
    private String email;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定 )")
    private Integer status;

    @ApiModelProperty(value = "性别(1.男 2.女)")
    private Integer sex;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(exist=false)
    private List<String> roleIdList;


}
