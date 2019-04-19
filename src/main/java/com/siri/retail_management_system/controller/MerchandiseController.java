package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.Merchandise;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.MerchandiseServiceImpl;
import com.siri.retail_management_system.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author SiriYang
 * @title: MerchandiseController
 * @projectName retail_management_system
 * @description: 商品控制类
 * @date 2019/4/15 19:35
 */
@Controller
@RequestMapping("/merchandise")
public class MerchandiseController {

    private final static Logger logger = LoggerFactory.getLogger(MerchandiseController.class);

    @Autowired
    MerchandiseServiceImpl merchandiseService;

    @GetMapping
    public String listMerchandise(Model model,
                                  HttpServletRequest request) {
        String id = CookieUtil.getCookieValue("loginID", request);
        if (id == null)
            return "redirect:/login";


        model.addAttribute("active", "merchandise");

        Result<List<Merchandise>> result = merchandiseService.findAll();

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "商品管理");
            model.addAttribute("merchandiseList", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "merchandise/merchandise_list";
    }

    @GetMapping("/edit/{id}")
    public String editMerchandise(Model model,
                                  @PathVariable("id") Integer id,
                                  HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("active", "merchandise");

        Result<Merchandise> result = merchandiseService.findOne(id);

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "商品管理");
            model.addAttribute("merchandise", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "merchandise/merchandise_edit";
    }

    @GetMapping("/add")
    public String addMerchandise(Model model,
                                 HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "商品管理");
        model.addAttribute("active", "merchandise");

        return "merchandise/merchandise_edit";
    }

    @PostMapping("/save")
    public String saveMerchandise(Model model,
                                  @RequestParam("id") Integer id,
                                  @RequestParam("name") String name,
                                  @RequestParam("number") Integer number,
                                  @RequestParam("income_price") Double income_price,
                                  @RequestParam("sale_price") Double sale_price,
                                  @RequestParam("member_price") Double member_price) {

        model.addAttribute("title", "商品管理");
        model.addAttribute("active", "merchandise");

        if (id != null) {
            Result<Merchandise> result = merchandiseService.findOne(id);
            if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
                Merchandise merchandise = result.getData();
                merchandise.setName(name);
                merchandise.setNumber(number);
                merchandise.setIncome_price(income_price);
                merchandise.setSale_price(sale_price);
                merchandise.setMember_price(member_price);

                result = merchandiseService.addOrUpdate(merchandise);
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
            Merchandise merchandise = new Merchandise(name,income_price, sale_price,member_price);
            Result<Merchandise> result = merchandiseService.addOrUpdate(merchandise);
            if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                model.addAttribute("title", "错误");
                model.addAttribute("errormsg", result.getErrMessage());
                return "errors";
            }
        }

        logger.info("save:" + id + " " + name+" "+income_price+" "+sale_price+" "+member_price);

        return "redirect:/merchandise";
    }

    @GetMapping("/delete/{id}")
    public String deleteMerchandise(Model model,
                                    @PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        merchandiseService.delete(id);

        return "redirect:/merchandise";
    }

    @GetMapping("/info/{id}")
    public String infoMerchandise(Model model,
                                    @PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        Result<Merchandise> result = merchandiseService.findOne(id);

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "商品详情");
            model.addAttribute("merchandise", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "merchandise/merchandise_info";
    }

}
