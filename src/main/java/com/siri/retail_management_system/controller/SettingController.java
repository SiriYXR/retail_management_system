package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author SiriYang
 * @title: SettingController
 * @projectName retail_management_system
 * @description: 系统设置控制类
 * @date 2019/4/15 19:55
 */
@Controller
@RequestMapping("/setting")
public class SettingController {

    private final static Logger logger = LoggerFactory.getLogger(SettingController.class);

    @GetMapping
    public String Setting(Model model,
                             HttpServletRequest request) {
        String id = CookieUtil.getCookieValue("loginID", request);
        if (id == null)
            return "redirect:/login";

        model.addAttribute("title", "系统设置");
        model.addAttribute("active", "setting");

        return "setting";
    }
}
