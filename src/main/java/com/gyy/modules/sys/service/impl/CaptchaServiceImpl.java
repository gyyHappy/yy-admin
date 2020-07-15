package com.gyy.modules.sys.service.impl;

import com.gyy.modules.sys.service.CaptchaService;
import com.gyy.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author GYY
 * @version 1.0
 * @date 2020/7/8 13:16
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean validate(String uuid, String code) {
        if (redisUtils.hasKey(uuid)){
            return redisUtils.get(uuid).equals(code);
        }
        return false;
    }
}
