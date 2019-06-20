package com.siri.retail_management_system.controller;

import com.siri.retail_management_system.domain.Member;
import com.siri.retail_management_system.domain.Result;
import com.siri.retail_management_system.enums.ResultEnum;
import com.siri.retail_management_system.service.MemberService;
import com.siri.retail_management_system.utils.CookieUtil;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author SiriYang
 * @title: MemberController
 * @projectName retail_management_system
 * @description: 会员控制类
 * @date 2019/4/15 19:37
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    MemberService memberService;

    /**
     * 跳转到会员主页，列出所有未删除的会员
     * @param model
     * @param request
     * @return
     */
    @GetMapping
    public String listMember(Model model,
                             HttpServletRequest request) {
        String id = CookieUtil.getCookieValue("loginID", request);
        if (id == null)
            return "redirect:/login";

        model.addAttribute("active", "member");

        Result<List<Member>> result = memberService.findAll();

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "会员管理");
            model.addAttribute("memberList", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "member/member_list";
    }

    /**
     * 跳转到编辑界面，修改会员
     * @param model
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/edit/{id}")
    public String editmember(Model model,
                             @PathVariable("id") Integer id,
                             HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("active", "member");

        Result<Member> result = memberService.findOne(id);

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "会员管理");
            model.addAttribute("member", result.getData());
        } else {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "member/member_edit";
    }

    /**
     * 跳转到编辑界面，添加会员
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/add")
    public String addMember(Model model,
                            HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        model.addAttribute("title", "会员管理");
        model.addAttribute("active", "member");

        return "member/member_edit";
    }

    /**
     * 获取编辑界面数据，保存会员
     * @param model
     * @param id
     * @param membername
     * @param telephon
     * @param points
     * @return
     */
    @PostMapping("/save")
    public String saveMember(Model model,
                             @RequestParam("id") Integer id,
                             @RequestParam("membername") String membername,
                             @RequestParam("telephon") String telephon,
                             @RequestParam("points") Integer points) {

        Result<Member> result=null;

        if (id!=null){
            result=memberService.update(id,membername,telephon,points);
        }else {
            result=memberService.add(membername, telephon);
        }

        logger.info("save:" + id + " " + membername + " " + telephon);

        if (result.getErrCode() != ResultEnum.SUCCESS.getCode()) {
            model.addAttribute("title", "错误");
            model.addAttribute("errormsg", result.getErrMessage());
            logger.error(result.getErrMessage());
            return "errors";
        }

        return "redirect:/member";
    }

    /**
     * 删除会员，再返回会员首页
     * @param model
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteMember(Model model,
                               @PathVariable("id") Integer id,
                               HttpServletRequest request) {
        String loginID = CookieUtil.getCookieValue("loginID", request);
        if (loginID == null)
            return "redirect:/login";

        memberService.delete(id);

        return "redirect:/member";
    }

    /**
     * 异步查询会员手机号
     * @param param
     * @param resp
     * @throws Exception
     */
    @PostMapping("/telephone")
    public void ajaxFindByTelephone(@RequestBody Map<String, Object> param, HttpServletResponse resp) throws Exception {

        logger.info(param.toString());

        JSONObject jsonObject = new JSONObject();

        Result<Member> result = memberService.findByTelephone(param.get("telephone").toString());

        if (result.getErrCode() == ResultEnum.SUCCESS.getCode()) {
            jsonObject.put("flag", "success");
            jsonObject.put("hintMessage", result.getData().getMembername());
            jsonObject.put("memberID", result.getData().getId());

        } else {
            jsonObject.put("flag", "failure");
            jsonObject.put("hintMessage", "无");
            jsonObject.put("memberID", "");
        }

        logger.info(jsonObject.toJSONString());
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println(jsonObject.toJSONString());
        resp.getWriter().close();
    }
}
