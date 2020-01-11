package com.goodluncky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SsoServerController {
    @RequestMapping("/index")
    public String index(){
        return "login";
    }
    @RequestMapping("/login")
    @ResponseBody
    public String login(String username,String password,String redirectUrl){
        System.out.println("username:"+username+",password:"+password);
        return "login ok";
    }
    //检测login
    @RequestMapping("/checkLogin")
    public String checkLogin(String redirectUrl, HttpSession session, Model model){
        //1.判断这个用户是否登录，是否拥有全局会话  token
        String token = (String) session.getAttribute("token");
        if (StringUtils.isEmpty(token)){
            //没有全局会话，去登录页面,我从哪里来不能丢
            model.addAttribute("redirectUrl",redirectUrl);
            return "login";
        }else{
            //存在全局会话，返回原来的地方
            model.addAttribute("token",token);
            return  "redirect:"+redirectUrl;

        }
    }
}
