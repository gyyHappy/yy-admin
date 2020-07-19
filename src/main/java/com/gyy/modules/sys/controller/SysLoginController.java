package com.gyy.modules.sys.controller;

import com.google.code.kaptcha.Producer;
import com.gyy.common.constants.Constant;
import com.gyy.common.exception.BusinessException;
import com.gyy.modules.sys.form.SysLoginForm;
import com.gyy.modules.sys.service.CaptchaService;
import com.gyy.modules.sys.service.SysUserService;
import com.gyy.modules.sys.vo.resp.LoginRespVO;
import com.gyy.common.utils.R;
import com.gyy.common.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 13:01
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "登录模块")
public class SysLoginController extends AbstractController{

    @Autowired
    private Producer producer;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public R login(@RequestBody @Valid SysLoginForm form){
        //校验验证码
        boolean validate = captchaService.validate(form.getUuid(), form.getCaptcha());
        if (!validate){
            throw new BusinessException(4000003,"验证码错误");
        }
        //删除验证码
        redisUtils.delete(form.getUuid());
        //查询用户并创建token
        LoginRespVO vo = sysUserService.login(form);
        //返回vo
        return R.ok(vo);
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

    @PostMapping("/logout")
    @ApiOperation(value = "登出")
    public R logout(HttpServletRequest request){
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        sysUserService.logout(getUserId(),accessToken);

        return R.ok();
    }

}
