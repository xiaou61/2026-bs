package com.xiaou.controller;

import com.xiaou.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/lost")
    public String lost() {
        return "lost";
    }

    @GetMapping("/found")
    public String found() {
        return "found";
    }

    @GetMapping("/lost/detail")
    public String lostDetail() {
        return "lost-detail";
    }

    @GetMapping("/found/detail")
    public String foundDetail() {
        return "found-detail";
    }

    @GetMapping("/lost/publish")
    public String publishLost() {
        return "publish-lost";
    }

    @GetMapping("/found/publish")
    public String publishFound() {
        return "publish-found";
    }

    @GetMapping("/my/lost")
    public String myLost() {
        return "my-lost";
    }

    @GetMapping("/my/found")
    public String myFound() {
        return "my-found";
    }

    @GetMapping("/claims/sent")
    public String claimsSent() {
        return "claims-sent";
    }

    @GetMapping("/claims/received")
    public String claimsReceived() {
        return "claims-received";
    }

    @GetMapping("/favorites")
    public String favorites() {
        return "favorites";
    }

    @GetMapping("/notifications")
    public String notifications() {
        return "notifications";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/admin/users")
    public String adminUsers(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            return "redirect:/home";
        }
        return "admin-users";
    }

    @GetMapping("/admin/categories")
    public String adminCategories(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            return "redirect:/home";
        }
        return "admin-categories";
    }
}

