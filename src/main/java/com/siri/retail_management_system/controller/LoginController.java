package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.constant.WebConstant;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.AdminService;
import com.siri.retail_management_system.service.SystemLogService;
import com.siri.retail_management_system.utils.CookieUtil;
import com.siri.retail_management_system.utils.EncryptUtil;
import com.siri.retail_management_system.utils.IpUtil;
import com.siri.retail_management_system.utils.RandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SiriYang
 * @title: LoginController
 * @projectName retail_management_system
 * @description: 登陆控制类
 * @date 2019/4/16 23:57
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    SystemLogService systemLogService;

    /**
     * 登陆页面
     *
     * @param model
     * @return
     */
    @GetMapping
    public String login(Model model) {

        model.addAttribute("title", "管理员登陆");
        model.addAttribute("pic", RandUtil.getIntFromOne(WebConstant.MAX_LOGIN_BG) + ".png");

        return "login";
    }


    /**
     * 登陆验证
     *
     * @param username
     * @param password
     * @param model
     * @param response
     * @return
     */
    @PostMapping()
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        HttpServletResponse response,
                        HttpServletRequest request) {

        logger.info("username:" + username);
        logger.info("password:" + password);
        logger.info("passwordMD5:" + EncryptUtil.stringMD5(password));

        Result<Integer> result = adminService.login(username, EncryptUtil.stringMD5(password));

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            CookieUtil.setCookie("loginID", result.getData().toString(), response);
            systemLogService.login(result.getData(),IpUtil.getIpAddr(request));
            return "redirect:/index";
        } else {
            return "redirect:/login/error/" + result.getErrCode();
        }
    }

    /**
     * 登陆错误
     *
     * @param model
     * @return
     */
    @GetMapping("/error/{errcode}")
    public String loginError(@PathVariable("errcode") Integer errcode,
                             Model model) {

        model.addAttribute("title", "管理员登陆");
        model.addAttribute("pic", RandUtil.getIntFromOne(WebConstant.MAX_LOGIN_BG) + ".png");
        model.addAttribute("loginError", true);

        String errmsg;

        if (errcode == ResultEnum.USERNAME_WRONG.getCode())
            errmsg = ResultEnum.USERNAME_WRONG.getMsg();
        else if (errcode == ResultEnum.PASSWORD_WRONG.getCode())
            errmsg = ResultEnum.PASSWORD_WRONG.getMsg();
        else
            errmsg = ResultEnum.UNKONW_ERROR.getMsg();

        model.addAttribute("errorMsg", errmsg);
        return "login";
    }

}


