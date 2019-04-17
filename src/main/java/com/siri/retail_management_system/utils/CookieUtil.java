package com.siri.retail_management_system.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SiriYang
 * @title: CookieUtils
 * @projectName siriblog
 * @description: Cookie工具
 * @date 2019/4/9 22:58
 */
@Component
public class CookieUtil {

    public static final int COOKIE_MAX_AGE = 7 * 24 * 3600;
    public static final int COOKIE_HALF_HOUR = 30 * 60;

    /**
     * 根据Cookie名称得到Cookie对象，不存在该对象则返回Null
     * @param name
     * @param request
     * @return
     */
    public static Cookie getCookie(String name,HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null||cookies.length<1) {
            return null;
        }
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) {
                cookie = c;
                break;
            }
        }
        return cookie;
    }

    /**
     * 根据Cookie名称直接得到Cookie值
     * @param name
     * @param request
     * @return
     */
    public static String getCookieValue(String name,HttpServletRequest request) {
        Cookie cookie = getCookie(name,request);
        if(cookie != null){
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 移除cookie
     * @param name
     * @param response
     * @param request
     */
    public static void removeCookie(String name,
                                    HttpServletResponse response,
                                    HttpServletRequest request) {
        if (null == name) {
            return;
        }
        Cookie cookie = getCookie(name,request);
        if(null != cookie){
            cookie.setPath("/");
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 添加一条新的Cookie，可以指定过期时间(单位：秒)
     * @param name
     * @param value
     * @param maxValue
     * @param response
     */
    public static void setCookie(String name,
                                 String value,
                                 int maxValue,
                                 HttpServletResponse response) {
        if (StringUtils.isBlank(name)) {
            return;
        }
        if (null == value) {
            value = "";
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxValue != 0 ) {
            if(maxValue>COOKIE_MAX_AGE)
                cookie.setMaxAge(COOKIE_MAX_AGE);
            else
                cookie.setMaxAge(maxValue);
        } else {
            cookie.setMaxAge(COOKIE_HALF_HOUR);
        }
        response.addCookie(cookie);

    }

    /**
     * 添加一条新的Cookie，默认30分钟过期时间
     * @param name
     * @param value
     * @param response
     */
    public static void setCookie(String name,
                                 String value,
                                 HttpServletResponse response) {
        setCookie(name, value, COOKIE_HALF_HOUR,response);
    }

    /**
     * 将cookie封装到Map里面
     * @return
     */
    public static Map<String,Cookie> getCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length>1){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}