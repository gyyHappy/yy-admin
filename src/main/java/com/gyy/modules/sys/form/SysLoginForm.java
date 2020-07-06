package com.gyy.modules.sys.form;

import lombok.Data;

/**
 * 表单登录
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 13:09
 */
@Data
public class SysLoginForm {

    private String username;

    private String password;

    private String captcha;

    private String uuid;
}
