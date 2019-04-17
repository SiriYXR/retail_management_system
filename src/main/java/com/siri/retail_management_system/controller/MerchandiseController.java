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
 * @title: MerchandiseController
 * @projectName retail_management_system
 * @description: 商品控制类
 * @date 2019/4/15 19:35
 */
@Controller
@RequestMapping("/merchandise")
public class MerchandiseController {

    private final static Logger logger = LoggerFactory.getLogger(MerchandiseController.class);

    @GetMapping
    public String listMerchandise(Model model,
                             HttpServletRequest request) {
        String id = CookieUtil.getCookieValue("loginID", request);
        if (id == null)
            return "redirect:/login";

        model.addAttribute("title", "商品管理");
        model.addAttribute("active", "merchandise");

        return "merchandise/merchandise_list";
    }
}
