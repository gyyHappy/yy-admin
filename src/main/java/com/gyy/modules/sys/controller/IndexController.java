package com.gyy.modules.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 19:33
 */
@Controller
@RequestMapping("/index")
@Api(tags = "视图")
public class IndexController {

    @GetMapping("/404")
    @ApiOperation(value = "跳转404错误页面")
    public String error404(){
        return "error/404";
    }

    @GetMapping("/login")
    @ApiOperation(value = "跳转登陆页面")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    @ApiOperation(value = "跳转首页页面")
    public String home(){
        return "home";
    }

    @GetMapping("/main")
    @ApiOperation(value = "跳转主页页面")
    public String main(){
        return "main";
    }
}
