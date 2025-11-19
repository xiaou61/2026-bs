package com.xiaou.ailearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器 - 处理页面跳转
 */
@Controller
public class PageController {

    /**
     * 首页
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 注册页面
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 学生主页
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    /**
     * AI推荐页面
     */
    @GetMapping("/recommend")
    public String recommend() {
        return "recommend";
    }

    /**
     * 智能问答页面
     */
    @GetMapping("/qa")
    public String qa() {
        return "qa";
    }

    /**
     * 学习路径页面
     */
    @GetMapping("/learning-path")
    public String learningPath() {
        return "learning-path";
    }

    /**
     * 学习分析页面
     */
    @GetMapping("/analytics")
    public String analytics() {
        return "analytics";
    }

    /**
     * 个人设置页面
     */
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}