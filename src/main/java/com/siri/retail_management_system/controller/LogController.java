package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.domain.SystemLog;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.SystemLogService;
import com.siri.retail_management_system.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author SiriYang
 * @title: PromotionController
 * @projectName retail_management_system
 * @description: 促销控制类
 * @date 2019/4/15 19:36
 */
@Controller
@RequestMapping("/systemlog")
public class LogController {

    private final static Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    SystemLogService systemLogService;

    @GetMapping
    public String listPromotion(Model model,
                             HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "日志管理");
        model.addAttribute("active", "systemlog");

        Result<List<SystemLog>> result=systemLogService.findNew(-1);

        if(result.getErrCode()!= ResultEnum.SUCCESS.getCode()){
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),result.getErrMessage());
            return "errors";
        }

        model.addAttribute("logList", result.getData());

        return "systemlog/systemlog_list";
    }

    @GetMapping("/delete/{id}")
    public String deleteInCome(Model model,
                               @PathVariable("id") Integer id,
                               HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        systemLogService.delete(id);
        systemLogService.newlog(Integer.valueOf(loginID),"删除了系统日志");
        return "redirect:/systemlog";
    }
}
