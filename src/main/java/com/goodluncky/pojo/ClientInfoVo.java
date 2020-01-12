package com.goodluncky.pojo;

public class ClientInfoVo {
    private String clientUrl; //用户登出地址
    private String jsessionId; //用户jessionId

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getJsessionId() {
        return jsessionId;
    }

    public void setJsessionId(String jsessionId) {
        this.jsessionId = jsessionId;
    }
}
