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
 * @title: MemberController
 * @projectName retail_management_system
 * @description: TODO
 * @date 2019/4/15 19:37
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

    @GetMapping
    public String listMember(Model model,
                             HttpServletRequest request) {
        String id = CookieUtil.getCookieValue("loginID", request);
        if (id == null)
            return "redirect:/login";

        model.addAttribute("title", "会员管理");
        model.addAttribute("active", "member");

        return "member/member_list";
    }
}
