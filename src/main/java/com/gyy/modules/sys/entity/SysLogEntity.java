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

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author GYY
 * @since 2020-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_log")
@ApiModel(value="SysLogEntity对象", description="系统日志")
public class SysLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户操作")
    private String operation;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "执行时长(毫秒)")
    private Long time;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;


}
