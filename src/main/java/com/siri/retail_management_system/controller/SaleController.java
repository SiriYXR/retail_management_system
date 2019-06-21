package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.Member;
import com.siri.retail_management_system.domain.Merchandise;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.domain.Sale;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.MemberService;
import com.siri.retail_management_system.service.MerchandiseServiceImpl;
import com.siri.retail_management_system.service.SaleServiceImpl;
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
 * @title: SaleController
 * @projectName retail_management_system
 * @description: 出货控制类
 * @date 2019/4/15 19:36
 */
@Controller
@RequestMapping("/sale")
public class SaleController {

    private final static Logger logger = LoggerFactory.getLogger(SaleController.class);

    @Autowired
    SaleServiceImpl saleService;

    @Autowired
    MerchandiseServiceImpl merchandiseService;

    @Autowired
    MemberService memberService;

    @Autowired
    SystemLogService systemLogService;

    @GetMapping
    public String listSale(Model model,
                           HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";


        model.addAttribute("active", "sale");

        Result<List<Sale>> result = saleService.findAll();

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "出货管理");
            model.addAttribute("saleList", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID), result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "sale/sale_list";
    }

    @GetMapping("/add/{id}")
    public String addSale(Model model,
                          @PathVariable("id") Integer id,
                          HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "出货管理");
        model.addAttribute("active", "sale");

        Result<Merchandise> result = merchandiseService.findOne(id);
        if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID), result.getErrMessage());
            return "errors";
        }

        model.addAttribute("merchandise", result.getData());

        return "sale/sale_edit";
    }

    @GetMapping("/edit/{id}")
    public String editSale(Model model,
                           @PathVariable("id") Integer id,
                           HttpServletRequest request){
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "出货管理");
        model.addAttribute("active", "sale");

        Result<Sale> result=saleService.findOne(id);
        if(result.getErrCode()==ResultEnum.SUCCESS.getCode()){
            Sale sale=result.getData();
            model.addAttribute("sale",sale);
            model.addAttribute("merchandisename",sale.getMerchandisename());

        }else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID), result.getErrMessage());
            return "errors";
        }

        return "sale/sale_edit";
    }

    @PostMapping("/save")
    public String saveSale(Model model,
                           @RequestParam("id") Integer id,
                           @RequestParam("merchandiseid") Integer merchandiseid,
                           @RequestParam("merchandisename") String merchandisename,
                           @RequestParam("number") Integer number,
                           @RequestParam("telephone") String telephone,
                           @RequestParam("membername") String membername,
                           HttpServletRequest request){
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "出货管理");
        model.addAttribute("active", "sale");


        Result<Sale> result = null;
        if (id == null) {
            result = saleService.add(merchandiseid, merchandisename, number, telephone);
            systemLogService.newlog(Integer.valueOf(loginID), "创建了销售记录");
        } else {
            result = saleService.update(id, merchandisename, number, membername);
            systemLogService.newlog(Integer.valueOf(loginID), "修改了销售记录");
        }

        if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            systemLogService.errorlog(Integer.valueOf(loginID), result.getErrMessage());
            return "errors";
        }

        return "redirect:/sale";
    }

//    @GetMapping("/withdraw/{id}")
//    public String withdraw(Model model,
//                           @PathVariable("id") Integer id,
//                           HttpServletRequest request) {
//        String loginID = CookieUtil.getCookieValue("loginID", request);
//        if (loginID == null)
//            return "redirect:/login";
//
//        Result<Boolean> result=saleService.withdraw(id);
//        if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
//            model.addAttribute("title", "错误");
//            model.addAttribute("errormsg", result.getErrMessage());
//            return "errors";
//        }
//
//        return "redirect:/sale";
//    }

    @GetMapping("/delete/{id}")
    public String deleteSale(Model model,
                             @PathVariable("id") Integer id,
                             HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        saleService.delete(id);
        systemLogService.newlog(Integer.valueOf(loginID), "删除了销售记录");

        return "redirect:/sale";
    }
}
