package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.Admin;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.AdminService;
import com.siri.retail_management_system.service.SystemLogService;
import com.siri.retail_management_system.utils.CookieUtil;
import com.siri.retail_management_system.utils.EncryptUtil;
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
 * @title: AdminController
 * @projectName retail_management_system
 * @description: 管理员控制类
 * @date 2019/4/15 16:41
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    SystemLogService systemLogService;

    /**
     * 跳转到管理员主页，列出所有未删除的管理员
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping
    public String listAdmin(Model model,
                            HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("active", "admin");

        Result<List<Admin>> result = adminService.findAll();

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "管理员管理");
            model.addAttribute("adminList", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID), result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "admin/admin_list";
    }

    /**
     * 进入编辑界面，修改管理员
     *
     * @param model
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/edit/{id}")
    public String editAdmin(Model model,
                            @PathVariable("id") Integer id,
                            HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("active", "admin");

        Result<Admin> result = adminService.findOne(id);

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "管理员管理");
            model.addAttribute("admin", result.getData());
            model.addAttribute("isadd", false);
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID), result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "admin/admin_edit";
    }

    /**
     * 进入编辑界面，添加管理员
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/add")
    public String addAdmin(Model model,
                           HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "管理员管理");
        model.addAttribute("active", "admin");
        model.addAttribute("isadd", true);

        return "admin/admin_add";
    }

    @PostMapping("/add")
    public String addAdmin(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("newpassword1") String newpassword1,
                           @RequestParam("newpassword2") String newpassword2,
                           HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        logger.info("add:" + username + " " + newpassword1);

        Result<Admin> result = null;

        result = adminService.add(username, EncryptUtil.stringMD5(newpassword1));
        systemLogService.newlog(Integer.valueOf(loginID), "创建了管理员");


        if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID), result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "redirect:/admin";
    }


    /**
     * 获取前端编辑界面数据，保存管理员
     *
     * @param model
     * @param id
     * @param username
     * @return
     */
    @PostMapping("/save")
    public String saveAdmin(Model model,
                            @RequestParam("id") Integer id,
                            @RequestParam("username") String username,
                            @RequestParam("oldpassword") String oldpassword,
                            @RequestParam("newpassword1") String newpassword1,
                            @RequestParam("newpassword2") String newpassword2,
                            HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        logger.info("save:" + id + " " + username + " " + oldpassword);

        Result<Admin> result = null;


        Result<Integer> checkpassword = adminService.login(username, EncryptUtil.stringMD5(oldpassword));

        if (checkpassword.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", ResultEnum.PASSWORD_WRONG.getMsg());
            logger.error(ResultEnum.PASSWORD_WRONG.getMsg());
            return "errors";
        }

        if ((newpassword1 != null && newpassword2 != null && newpassword1.length() != 0 && newpassword2.length() != 0))
            result = adminService.update(id, username, EncryptUtil.stringMD5(newpassword1));
        else
            result = adminService.update(id, username, EncryptUtil.stringMD5(oldpassword));
        systemLogService.newlog(Integer.valueOf(loginID), "修改了管理员");


        if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID), result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "redirect:/admin";
    }

    /**
     * 删除管理员，再跳转到管理员主页
     *
     * @param model
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteAdmin(Model model,
                              @PathVariable("id") Integer id,
                              HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        adminService.delete(id);
        systemLogService.newlog(Integer.valueOf(loginID), "删除了管理员");

        return "redirect:/admin";
    }
}
