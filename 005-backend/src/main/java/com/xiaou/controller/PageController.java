package com.xiaou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/survey/create")
    public String createSurvey() {
        return "survey_create";
    }

    @GetMapping("/survey/edit")
    public String editSurvey() {
        return "survey_edit";
    }

    @GetMapping("/survey/fill")
    public String fillSurvey() {
        return "survey_fill";
    }

    @GetMapping("/survey/stat")
    public String surveyStats() {
        return "survey_stat";
    }
}

