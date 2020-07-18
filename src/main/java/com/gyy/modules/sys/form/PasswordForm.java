package com.gyy.modules.sys.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author GYY
 * @version 1.0
 * @date 2020/7/18 11:02
 */
@Data
public class PasswordForm {

    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空")
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
