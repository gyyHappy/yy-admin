package com.gyy.modules.sys.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 表单登录
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 13:09
 */
@Data
public class SysLoginForm {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @ApiModelProperty(value = "验证码uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

    @ApiModelProperty(value = "登录类型 1：pc；2：App")
    @NotBlank(message = "登录类型不能为空")
    private String type;
}
