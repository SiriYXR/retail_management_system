package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.InCome;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.domain.Sale;
import com.siri.retail_management_system.domain.SystemLog;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.*;
import com.siri.retail_management_system.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author SiriYang
 * @title: IndexController
 * @projectName retail_management_system
 * @description: 首页控制类
 * @date 2019/4/15 14:08
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    MerchandiseService merchandiseService;

    @Autowired
    MemberService memberService;

    @Autowired
    SaleService saleService;

    @Autowired
    InComeService inComeService;

    @Autowired
    SystemLogService systemLogService;

    @GetMapping
    private String index(Model model,
                        HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "仪表盘");
        model.addAttribute("active", "panel");

        Result<List<Integer>> resultMerchandiseNumber=merchandiseService.countNumber();
        if (resultMerchandiseNumber.getErrCode()!=ResultEnum.SUCCESS.getCode()){
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", resultMerchandiseNumber.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),resultMerchandiseNumber.getErrMessage());
            return "errors";
        }

        Result<Integer> resultMemberNumber=memberService.countNumber();
        if(resultMemberNumber.getErrCode()!= ResultEnum.SUCCESS.getCode()){
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", resultMemberNumber.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),resultMemberNumber.getErrMessage());
            return "errors";
        }

        Result<Integer> resultSystemLogNumber=systemLogService.countNumber();
        if (resultSystemLogNumber.getErrCode()!=ResultEnum.SUCCESS.getCode()){
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", resultSystemLogNumber.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),resultSystemLogNumber.getErrMessage());
            return "errors";
        }

        Result<List<Sale>> salelog=saleService.findNew(5);
        if (salelog.getErrCode()!=ResultEnum.SUCCESS.getCode()){
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", salelog.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),salelog.getErrMessage());
            return "errors";
        }

        Result<List<InCome>> incomelog=inComeService.findNew(5);
        if (incomelog.getErrCode()!=ResultEnum.SUCCESS.getCode()){
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", incomelog.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),incomelog.getErrMessage());
            return "errors";
        }

        Result<List<SystemLog>> systemlog=systemLogService.findNew(5);
        if (incomelog.getErrCode()!=ResultEnum.SUCCESS.getCode()){
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", systemlog.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),systemlog.getErrMessage());
            return "errors";
        }

        model.addAttribute("merchandiseNum", resultMerchandiseNumber.getData().get(0));
        model.addAttribute("merchandiseClass", resultMerchandiseNumber.getData().get(1));
        model.addAttribute("memberNum", resultMemberNumber.getData());
        model.addAttribute("logNum", resultSystemLogNumber.getData());

        model.addAttribute("salelog", salelog.getData());
        model.addAttribute("incomelog", incomelog.getData());
        model.addAttribute("systemlog", systemlog.getData());

        logger.info("cookie loginID:" + loginID);

        return "index";
    }

}
