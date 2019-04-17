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
 * @title: StatisticController
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/4/15 19:36
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController {

    private final static Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @GetMapping
    public String Statistic(Model model,
                             HttpServletRequest request) {
        String id = CookieUtil.getCookieValue("loginID", request);
        if (id == null)
            return "redirect:/login";

        model.addAttribute("title", "统计数据");
        model.addAttribute("active", "statistic");

        return "statistic";
    }
}
