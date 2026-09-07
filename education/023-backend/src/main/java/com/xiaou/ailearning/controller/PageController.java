package com.xiaou.ailearning.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
     * 课程列表页面
     */
    @GetMapping("/courses")
    public String courses() {
        return "courses";
    }

    /**
     * 课程详情页面
     */
    @GetMapping("/courses/{courseId}")
    public String courseDetail(@PathVariable Long courseId) {
        return "course-detail";
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
     * 学习路径详情页复用学习路径主页面
     */
    @GetMapping("/learning-path/{pathId}")
    public String learningPathDetail(@PathVariable Long pathId) {
        return "learning-path";
    }

    /**
     * 学习路径编辑页复用学习路径主页面
     */
    @GetMapping("/learning-path/{pathId}/edit")
    public String learningPathEdit(@PathVariable Long pathId) {
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

    /**
     * 设置页复用个人信息页
     */
    @GetMapping("/settings")
    public String settings() {
        return "profile";
    }

    /**
     * 学习入口统一回落到课程详情
     */
    @GetMapping("/learn/{courseId}")
    public String learnCourse(@PathVariable Long courseId) {
        return "redirect:/courses/" + courseId;
    }

    /**
     * 知识点学习入口统一回落到课程详情
     */
    @GetMapping("/learn/{courseId}/knowledge-point/{pointId}")
    public String learnKnowledgePoint(@PathVariable Long courseId, @PathVariable Long pointId) {
        return "redirect:/courses/" + courseId;
    }

    /**
     * 路径步骤入口统一回落到学习路径页面
     */
    @GetMapping("/learn/step/{stepId}")
    public String learnPathStep(@PathVariable Long stepId) {
        return "redirect:/learning-path";
    }

    /**
     * QA历史页复用智能问答页面
     */
    @GetMapping("/qa/history")
    public String qaHistory() {
        return "qa";
    }

    /**
     * 知识点详情暂回落到分析页
     */
    @GetMapping("/knowledge/{knowledgeId}")
    public String knowledgeDetail(@PathVariable Long knowledgeId) {
        return "redirect:/analytics";
    }

    /**
     * 测验入口暂回落到智能问答页
     */
    @GetMapping("/quiz/{quizId}")
    public String quizEntry(@PathVariable Long quizId) {
        return "redirect:/qa";
    }

    /**
     * 复习入口暂回落到学习分析页
     */
    @GetMapping("/review/{targetId}")
    public String reviewEntry(@PathVariable Long targetId) {
        return "redirect:/analytics";
    }

    /**
     * 注销
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
