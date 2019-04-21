package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.Merchandise;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.MemberService;
import com.siri.retail_management_system.service.MerchandiseServiceImpl;
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
    MerchandiseServiceImpl merchandiseService;

    @Autowired
    MemberService memberService;

    @GetMapping
    private String index(Model model,
                        HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "仪表盘");
        model.addAttribute("active", "panel");

        Result<Integer> resultCountMember=memberService.countMember();
        if(resultCountMember.getErrCode()!= ResultEnum.SUCCESS.getCode()){
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", resultCountMember.getErrMessage());
            logger.error(resultCountMember.getErrMessage());
            return "errors";
        }

        Result<List<Merchandise>> resultMerchandise = merchandiseService.findAll();
        int merchandiseNum,merchandiseClass;

        if (resultMerchandise.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            merchandiseClass=resultMerchandise.getData().size();
            merchandiseNum=0;
            for(Merchandise i : resultMerchandise.getData()){
                merchandiseNum+=i.getNumber();
            }

        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", resultMerchandise.getErrMessage());
            logger.error(resultMerchandise.getErrMessage());
            return "errors";
        }

        model.addAttribute("merchandiseNum", merchandiseNum);
        model.addAttribute("merchandiseClass", merchandiseClass);
        model.addAttribute("promotionNum", 0);
        model.addAttribute("memberNum", resultCountMember.getData());



        logger.info("cookie loginID:" + loginID);


        return "index";
    }

}
