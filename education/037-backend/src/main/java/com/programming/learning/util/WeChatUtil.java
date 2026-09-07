package com.programming.learning.util;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信工具类
 */
@Slf4j
@Component
public class WeChatUtil {

    private final WxMaService wxMaService;
    private final boolean mockEnabled;

    public WeChatUtil(WxMaService wxMaService,
                      @Value("${wechat.mock.enabled:false}") boolean mockEnabled) {
        this.wxMaService = wxMaService;
        this.mockEnabled = mockEnabled;
    }

    /**
     * 通过code获取session信息
     */
    public WxMaJscode2SessionResult code2Session(String code) {
        if (mockEnabled) {
            WxMaJscode2SessionResult mockSession = buildMockSession(code);
            if (mockSession != null) {
                return mockSession;
            }
        }

        try {
            return wxMaService.getUserService().getSessionInfo(code);
        } catch (Exception e) {
            log.error("微信登录失败: {}", e.getMessage());
            return null;
        }
    }

    private WxMaJscode2SessionResult buildMockSession(String code) {
        if (code == null || !(code.startsWith("mock_") || code.startsWith("demo_"))) {
            return null;
        }

        WxMaJscode2SessionResult session = new WxMaJscode2SessionResult();
        switch (code) {
            case "mock_admin":
            case "demo_admin":
                session.setOpenid("admin_open_id");
                break;
            case "mock_teacher":
            case "demo_teacher":
                session.setOpenid("teacher_open_id");
                break;
            case "mock_user1":
            case "demo_user1":
                session.setOpenid("user1_open_id");
                break;
            default:
                session.setOpenid(code + "_open_id");
                break;
        }
        session.setSessionKey("mock-session-key");
        return session;
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
