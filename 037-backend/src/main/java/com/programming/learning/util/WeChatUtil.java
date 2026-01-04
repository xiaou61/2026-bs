package com.programming.learning.util;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信工具类
 */
@Slf4j
@Component
public class WeChatUtil {

    @Autowired
    private WxMaService wxMaService;

    /**
     * 通过code获取session信息
     */
    public WxMaJscode2SessionResult code2Session(String code) {
        try {
            return wxMaService.getUserService().getSessionInfo(code);
        } catch (Exception e) {
            log.error("微信登录失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 解密用户信息
     */
    public WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String iv) {
        try {
            return wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        } catch (Exception e) {
            log.error("解密用户信息失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取手机号
     */
    public String getPhoneNumber(String sessionKey, String encryptedData, String iv) {
        try {
            return wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv).getPhoneNumber();
        } catch (Exception e) {
            log.error("获取手机号失败: {}", e.getMessage());
            return null;
        }
    }
}
