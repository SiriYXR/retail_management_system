package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.Member;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.MemberService;
import com.siri.retail_management_system.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author SiriYang
 * @title: MemberController
 * @projectName retail_management_system
 * @description: 会员控制类
 * @date 2019/4/15 19:37
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    MemberService memberService;

    @GetMapping
    public String listMember(Model model,
                             HttpServletRequest request) {
        String id = CookieUtil.getCookieValue("loginID", request);
        if (id == null)
            return "redirect:/login";

        model.addAttribute("active", "member");

        Result<List<Member>> result = memberService.findAll();

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "会员管理");
            model.addAttribute("memberList", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "member/member_list";
    }

    @GetMapping("/edit/{id}")
    public String editmember(Model model,
                             @PathVariable("id") Integer id,
                             HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("active", "member");

        Result<Member> result = memberService.findOne(id);

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "会员管理");
            model.addAttribute("member", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "member/member_edit";
    }

    @GetMapping("/add")
    public String addMember(Model model,
                            HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "会员管理");
        model.addAttribute("active", "member");

        return "member/member_edit";
    }

    @PostMapping("/save")
    public String saveMember(Model model,
                             @RequestParam("id") Integer id,
                             @RequestParam("membername") String membername,
                             @RequestParam("telephon") String telephon,
                             @RequestParam("points") Integer points) {

        model.addAttribute("title", "会员管理");
        model.addAttribute("active", "member");

        if (id != null) {
            Result<Member> result = memberService.findOne(id);
            if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
                Member member = result.getData();
                member.setMembername(membername);
                member.setTelephon(telephon);
                member.setPoints(points);

                result = memberService.addOrUpdate(member);
                if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                    model.addAttribute("title", "错误");
                    model.addAttribute("errormsg", result.getErrMessage());
                    return "errors";
                }
            } else {
                model.addAttribute("errormsg", result.getErrMessage());
                return "errors";
            }
        } else {
            Member member = new Member(membername, telephon);
            Result<Member> result = memberService.addOrUpdate(member);
            if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                model.addAttribute("title", "错误");
                model.addAttribute("errormsg", result.getErrMessage());
                return "errors";
            }
        }

        logger.info("save:" + id + " " + membername + " " + telephon);

        return "redirect:/member";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(Model model,
                               @PathVariable("id") Integer id,
                               HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        memberService.delete(id);

        return "redirect:/member";
    }
}
