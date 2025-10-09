package com.xiaou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login.html")
    public String login() {
        return "login";
    }

    @GetMapping("/register.html")
    public String register() {
        return "register";
    }

    @GetMapping("/products.html")
    public String products() {
        return "products";
    }

    @GetMapping("/product_detail.html")
    public String productDetail() {
        return "product_detail";
    }

    @GetMapping("/cart.html")
    public String cart() {
        return "cart";
    }

    @GetMapping("/order.html")
    public String order() {
        return "order";
    }

    @GetMapping("/farmer_dashboard.html")
    public String farmerDashboard() {
        return "farmer_dashboard";
    }

    @GetMapping("/admin_dashboard.html")
    public String adminDashboard() {
        return "admin_dashboard";
    }
}

