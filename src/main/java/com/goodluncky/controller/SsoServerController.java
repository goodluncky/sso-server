package com.goodluncky.controller;

import com.goodluncky.db.MockDB;
import com.goodluncky.pojo.ClientInfoVo;
import com.sun.deploy.util.SessionState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class SsoServerController {
    @RequestMapping("/index")
    public String index(){
        return "login";
    }
    @RequestMapping("/login")
    public String login(String username,String password,String redirectUrl,HttpSession session,Model model){
        System.out.println("username:"+username+",password:"+password);
        //模拟数据
        if("admin".equals(username)&&"123456".equals(password)){
            //1.给用户创建一个token令牌：唯一，保存到数据库，模拟数据库
            String token = UUID.randomUUID().toString();
            System.out.println("生成token成功--》"+token+":--->"+ session.getId()+"-------------->"+ session.toString());

            //2.保存到数据库
            MockDB.T_TOKEN.add(token);
            //3.在服务器中存在会话信息
            session.setAttribute("token",token);
           // System.out.println(session.getAttribute("token"));
            //4.返回给客户端
            model.addAttribute("token",token);
            return "redirect:"+redirectUrl; //从哪里来回到那里去
        }
        //登录不成功
        System.out.println("用户名密码不正确");
        model.addAttribute("redirectUrl",redirectUrl);
        return "login";
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
    //验证token是否ok
    @RequestMapping("/verify")
    @ResponseBody
    public String verifyToken(String token,String clientUrl,String jsessionId){
        if (MockDB.T_TOKEN.contains(token)){
            System.out.println("服务器端校验通过");
            //保存用户的登出和session信息
            List<ClientInfoVo> clientInfoVoList = MockDB.T_CLIENT_INFO.get(token);
            if (clientInfoVoList==null){
                clientInfoVoList = new ArrayList<ClientInfoVo>();
                //将用户的信息放入
                MockDB.T_CLIENT_INFO.put(token,clientInfoVoList);
            }
            ClientInfoVo clientInfoVo = new ClientInfoVo();
            clientInfoVo.setClientUrl(clientUrl);
            clientInfoVo.setJsessionId(jsessionId);
            clientInfoVoList.add(clientInfoVo);
            return "true";
        }
        return "false";
    }
    //注销请求,只有点击注销按钮才会注销session,服务端会seession过期自动过期
    @RequestMapping("/logOut")
    public String logOut(HttpSession session){
        session.invalidate();
        //通知淘宝和天猫销毁session,监听器
        return "login";
    }
}
