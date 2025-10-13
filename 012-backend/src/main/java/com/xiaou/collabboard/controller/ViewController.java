package com.xiaou.collabboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("page", "dashboard");
        model.addAttribute("pageTitle", "工作台");
        return "dashboard";
    }

    @GetMapping("/documents")
    public String documents(Model model) {
        model.addAttribute("page", "documents");
        model.addAttribute("pageTitle", "我的文档");
        return "documents";
    }

    @GetMapping("/document/{id}")
    public String documentEdit(@PathVariable Long id, Model model) {
        model.addAttribute("documentId", id);
        return "document-edit";
    }

    @GetMapping("/teams")
    public String teams(Model model) {
        model.addAttribute("page", "teams");
        model.addAttribute("pageTitle", "团队空间");
        return "teams";
    }

    @GetMapping("/templates")
    public String templates(Model model) {
        model.addAttribute("page", "templates");
        model.addAttribute("pageTitle", "模板市场");
        return "templates";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("page", "profile");
        model.addAttribute("pageTitle", "个人中心");
        return "profile";
    }
}

