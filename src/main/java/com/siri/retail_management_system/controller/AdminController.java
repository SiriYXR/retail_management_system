package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.Admin;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.AdminService;
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
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "admin/admin_list";
    }

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
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "admin/admin_edit";
    }

    @GetMapping("/add")
    public String addAdmin(Model model,
                           HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "管理员管理");
        model.addAttribute("active", "admin");

        return "admin/admin_edit";
    }

    @PostMapping("/save")
    public String saveAdmin(Model model,
                            @RequestParam("id") Integer id,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password) {

        model.addAttribute("title", "管理员管理");
        model.addAttribute("active", "admin");

        if (id != null) {
            Result<Admin> result = adminService.findOne(id);
            if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
                Admin admin = result.getData();
                admin.setUsername(username);
                admin.setPassword(password);
                result = adminService.addOrUpdate(admin);
                if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                    model.addAttribute("title", "错误");
                    model.addAttribute("errormsg",result.getErrMessage());
                    return "errors";
                }
            } else {
                model.addAttribute("errormsg",result.getErrMessage());
                return "errors";
            }
        } else {
            Admin admin = new Admin(username, password);
            Result<Admin> result = adminService.addOrUpdate(admin);
            if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                model.addAttribute("title", "错误");
                model.addAttribute("errormsg",result.getErrMessage());
                return "errors";
            }
        }

        logger.info("save:" + id + " " + username + " " + password);

        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdmin(Model model,
                              @PathVariable("id") Integer id,
                           HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        adminService.delete(id);

        return "redirect:/admin";
    }
}
