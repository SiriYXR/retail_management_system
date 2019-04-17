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
 * @title: IndexController
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/4/15 14:08
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping
    private String index(Model model,
                        HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "仪表盘");
        model.addAttribute("active", "panel");

        model.addAttribute("articles", 0);
        model.addAttribute("comments", 0);
        model.addAttribute("attachs", 0);
        model.addAttribute("links", 0);


        logger.info("cookie loginID:" + loginID);


        return "index";
    }

}
