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
 * @title: IncomeController
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/4/15 19:36
 */

@Controller
@RequestMapping("/income")
public class IncomeController {

    private final static Logger logger = LoggerFactory.getLogger(IncomeController.class);

    @GetMapping
    public String listIncome(Model model,
                            HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "进货管理");
        model.addAttribute("active", "income");

        return "income/income_list";
    }
}
