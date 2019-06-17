package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.Member;
import com.siri.retail_management_system.domain.Merchandise;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.domain.Sale;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.MemberService;
import com.siri.retail_management_system.service.MerchandiseServiceImpl;
import com.siri.retail_management_system.service.SaleServiceImpl;
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

    @GetMapping
    public String listSale(Model model,
                           HttpServletRequest request) {
        String id = CookieUtil.getCookieValue("loginID", request);
        if (id == null)
            return "redirect:/login";


        model.addAttribute("active", "sale");

        Result<List<Sale>> result = saleService.findAll();

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "出货管理");
            model.addAttribute("saleList", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "sale/sale_list";
    }

    @GetMapping("/add/{name}")
    public String addSale(Model model,
                          @PathVariable("name") String name,
                          HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "出货管理");
        model.addAttribute("active", "sale");

        model.addAttribute("merchandisename", name);

        return "sale/sale_edit";
    }

    @PostMapping("/save")
    public String saveSale(Model model,
                           @RequestParam("id") Integer id,
                           @RequestParam("merchandisename") String merchandisename,
                           @RequestParam("number") Integer number,
                           @RequestParam("telephone") String telephone) {

        model.addAttribute("title", "出货管理");
        model.addAttribute("active", "sale");

        Double sale_price = 0.0;
        String member_name = null;

        Result<Merchandise> merchandiseResult = merchandiseService.findByName(merchandisename);
        if (merchandiseResult.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", merchandiseResult.getErrMessage());
            return "errors";
        }

        //检查商品库存是否足够
        int curentNumber = merchandiseResult.getData().getNumber();
        if (curentNumber < number) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", "库存不足！");
            return "errors";
        } else {
            //扣除相应数量商品
            merchandiseResult.getData().setNumber(curentNumber - number);
            Result<Merchandise> result = merchandiseService.addOrUpdate(merchandiseResult.getData());
            if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                model.addAttribute("title", "错误");
                model.addAttribute("errormsg", result.getErrMessage());
                return "errors";
            }
        }

        sale_price = merchandiseResult.getData().getSale_price();

        //查询会员信息
        Result<Member> memberResult;
        if (telephone.length() != 0) {
            memberResult = memberService.findByTelephone(telephone);
            if (memberResult.getErrCode() == ResultEnum.SUCCESS.getCode()) {
                member_name = memberResult.getData().getMembername();
                sale_price = merchandiseResult.getData().getMember_price();

                //添加积分
                int point = memberResult.getData().getPoints() + (int) (sale_price * number);
                memberResult.getData().setPoints(point);
                Result<Member> result = memberService.addOrUpdate(memberResult.getData());
                if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                    model.addAttribute("title", "错误");
                    model.addAttribute("errormsg", result.getErrMessage());
                    return "errors";
                }
            }
        }


        if (id != null) {
            Result<Sale> result = saleService.findOne(id);
            if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
                Sale sale = result.getData();
                sale.setMerchandisename(merchandisename);
                sale.setMembername(member_name);
                sale.setNumber(number);
                sale.setSale_price(sale_price);

                result = saleService.addOrUpdate(sale);
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

            Sale sale = new Sale(merchandisename, number, sale_price);
            sale.setMembername(member_name);
            Result<Sale> result = saleService.addOrUpdate(sale);
            if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                model.addAttribute("title", "错误");
                model.addAttribute("errormsg", result.getErrMessage());
                return "errors";
            }
        }

        return "redirect:/sale";
    }

    @GetMapping("/withdraw/{id}")
    public String withdraw(Model model,
                           @PathVariable("id") Integer id,
                           HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        Result<Sale> saleResult = saleService.findOne(id);
        if (saleResult.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            double price = saleResult.getData().getSale_price();

            Result<Merchandise> merchandiseResult = merchandiseService.findByName(saleResult.getData().getMerchandisename());
            if (merchandiseResult.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                model.addAttribute("title", "错误");
                model.addAttribute("errormsg", merchandiseResult.getErrMessage());
                return "errors";
            }
            Merchandise merchandise = merchandiseResult.getData();
            int number = saleResult.getData().getNumber() + merchandise.getNumber();
            merchandise.setNumber(number);
            Result<Merchandise> result = merchandiseService.addOrUpdate(merchandise);
            if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                model.addAttribute("title", "错误");
                model.addAttribute("errormsg", result.getErrMessage());
                return "errors";
            }

            String memberName = saleResult.getData().getMembername();
            if (memberName != null && memberName.length() > 0) {
                Result<Member> memberResult = memberService.findByMembername(memberName);
                if (memberResult.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                    model.addAttribute("title", "错误");
                    model.addAttribute("errormsg", memberResult.getErrMessage());
                    return "errors";
                }
                Member member = memberResult.getData();
                int point = member.getPoints() - (int) (price * number);
                point = point > 0 ? point : 0;
                member.setPoints(point);
                Result<Member> memberResult1 = memberService.addOrUpdate(member);
                if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
                    model.addAttribute("title", "错误");
                    model.addAttribute("errormsg", memberResult1.getErrMessage());
                    return "errors";
                }
            }

            saleService.delete(id);
        }

        return "redirect:/sale";
    }

    @GetMapping("/delete/{id}")
    public String deleteSale(Model model,
                             @PathVariable("id") Integer id,
                             HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        saleService.delete(id);

        return "redirect:/sale";
    }
}
