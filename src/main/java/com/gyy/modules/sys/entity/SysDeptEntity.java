package com.gyy.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
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
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dept")
@ApiModel(value="SysDeptEntity对象", description="")
public class SysDeptEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(type = ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "部门编号")
    private String deptNo;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "父级id")
    private String pid;

    @ApiModelProperty(value = "状态(1:正常；0:弃用)")
    private Integer status;

    @ApiModelProperty(value = "为了维护更深层级关系(规则：父级关系编码+自己的编码)")
    private String relationCode;

    @ApiModelProperty(value = "部门经理user_id")
    private String deptManagerId;

    @ApiModelProperty(value = "部门经理名称")
    private String managerName;

    @ApiModelProperty(value = "部门经理联系电话")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除(1未删除；0已删除)")
    private Integer deleted;


}
