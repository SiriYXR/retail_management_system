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
 * @title: PromotionController
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/4/15 19:36
 */
@Controller
@RequestMapping("/promotion")
public class PromotionController {

    private final static Logger logger = LoggerFactory.getLogger(PromotionController.class);

    @GetMapping
    public String listPromotion(Model model,
                             HttpServletRequest request) {
        String id = CookieUtil.getCookieValue("loginID", request);
        if (id == null)
            return "redirect:/login";

        model.addAttribute("title", "促销管理");
        model.addAttribute("active", "promotion");

        return "promotion/promotion_list";
    }
}
