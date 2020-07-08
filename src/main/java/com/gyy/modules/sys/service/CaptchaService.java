package com.gyy.modules.sys.service;

/**
 * 验证码业务
 * @author GYY
 * @version 1.0
 * @date 2020/7/8 13:14
 */
public interface CaptchaService {

    /**
     * 校验验证码有效性
     */
    boolean validate(String uuid,String code);
}
