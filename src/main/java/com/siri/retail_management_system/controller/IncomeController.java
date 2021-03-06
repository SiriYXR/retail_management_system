package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.InCome;
import com.siri.retail_management_system.domain.Merchandise;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.InComeServiceImpl;
import com.siri.retail_management_system.service.MerchandiseServiceImpl;
import com.siri.retail_management_system.service.SystemLogService;
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
 * @title: IncomeController
 * @projectName retail_management_system
 * @description: 入货控制类
 * @date 2019/4/15 19:36
 */

@Controller
@RequestMapping("/income")
public class IncomeController {

    private final static Logger logger = LoggerFactory.getLogger(IncomeController.class);

    @Autowired
    InComeServiceImpl inComeService;

    @Autowired
    MerchandiseServiceImpl merchandiseService;

    @Autowired
    SystemLogService systemLogService;

    @GetMapping
    public String listIncome(Model model,
                             HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";


        model.addAttribute("active", "income");

        Result<List<InCome>> result = inComeService.findAll();

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "进货管理");
            model.addAttribute("incomeList", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "income/income_list";
    }

    @GetMapping("/edit/{id}")
    public String editInCome(Model model,
                             @PathVariable("id") Integer id,
                             HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("active", "income");

        Result<InCome> result = inComeService.findOne(id);

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "进货管理");
            model.addAttribute("income", result.getData());
            model.addAttribute("merchandisename", result.getData().getMerchandisename());

        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "income/income_edit";
    }

    @GetMapping("/add/{id}")
    public String addInCome(Model model,
                            @PathVariable("id") Integer id,
                            HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "进货管理");
        model.addAttribute("active", "income");

        Result<Merchandise> result = merchandiseService.findOne(id);
        if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),result.getErrMessage());
            return "errors";
        }

        model.addAttribute("merchandise", result.getData());

        return "income/income_edit";
    }

    @PostMapping("/save")
    public String saveInCome(Model model,
                             @RequestParam("id") Integer id,
                             @RequestParam("merchandiseid") Integer merchandiseid,
                             @RequestParam("merchandisename") String merchandisename,
                             @RequestParam("number") Integer number,
                             @RequestParam("income_price") Double income_price,
                             @RequestParam("supplier") String supplier,
                             HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "进货管理");
        model.addAttribute("active", "income");

        Result<InCome> result = null;
        if (id != null) {

            result = inComeService.update(id, merchandisename, number, income_price, supplier);
            systemLogService.newlog(Integer.valueOf(loginID),"修改了进货记录");

        } else {
            result = inComeService.add(merchandiseid, merchandisename, number, income_price, supplier);
            systemLogService.newlog(Integer.valueOf(loginID),"创建了进货记录");
        }

        if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID),result.getErrMessage());
            return "errors";
        }

        return "redirect:/income";
    }

    @GetMapping("/delete/{id}")
    public String deleteInCome(Model model,
                               @PathVariable("id") Integer id,
                               HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        inComeService.delete(id);
        systemLogService.newlog(Integer.valueOf(loginID),"删除了进货记录");
        return "redirect:/income";
    }
}
