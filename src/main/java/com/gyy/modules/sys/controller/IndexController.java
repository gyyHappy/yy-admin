package com.gyy.modules.sys.controller;

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
public class IndexController {

    @GetMapping("/loginTest")
    public String loginTest(){
        return "loginTest";
    }
}
