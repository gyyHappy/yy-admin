package com.gyy.modules.sys.controller;

import com.google.code.kaptcha.Producer;
import com.gyy.modules.sys.form.SysLoginForm;
import com.gyy.modules.sys.service.SysUserService;
import com.gyy.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 13:01
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "登录模块")
public class SysLoginController {

    @Autowired
    private Producer producer;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public Map<String,Object> login(@RequestBody SysLoginForm form){
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("data",sysUserService.queryByUsername(form));
        return result;
    }

    @GetMapping("/captcha.jpg")
    @ApiOperation(value = "获取验证码图片")
    public void getCaptcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String code = producer.createText();
        //存储验证码到redis,设置过期时间,5分钟
        redisUtils.set(uuid,code,5, TimeUnit.MINUTES);
        //生成图形验证码
        BufferedImage image = producer.createImage(code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

}
