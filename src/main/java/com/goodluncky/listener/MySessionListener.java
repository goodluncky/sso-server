package com.goodluncky.listener;

import com.goodluncky.db.MockDB;
import com.goodluncky.pojo.ClientInfoVo;
import com.goodluncky.util.HttpUtil;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

public class MySessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }
    //销毁事件
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String token = (String) session.getAttribute("token");

        //销毁用户
        MockDB.T_TOKEN.remove(token);
        List<ClientInfoVo> clientInfoList = MockDB.T_CLIENT_INFO.remove(token);
        for (ClientInfoVo vo:clientInfoList){
            try {
                //遍历通知客户端注销
                HttpUtil.sendHttpRequest(vo.getClientUrl(),vo.getJsessionId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
