package com.siri.retail_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author SiriYang
 * @title: RootController
 * @projectName retail_management_system
 * @description: 根路径控制类
 * @date 2019/4/15 16:21
 */
@Controller
public class RootController {

    @GetMapping("/")
    public String root(){
        return "redirect:/login";
    }
}
