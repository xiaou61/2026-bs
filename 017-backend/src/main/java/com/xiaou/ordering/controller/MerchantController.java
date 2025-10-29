package com.xiaou.ordering.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.ordering.entity.Dish;
import com.xiaou.ordering.entity.Merchant;
import com.xiaou.ordering.service.DishService;
import com.xiaou.ordering.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private DishService dishService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(required = false) String category,
                       Model model) {
        Page<Merchant> merchantPage = merchantService.getMerchantList(page, 12, category);
        model.addAttribute("page", merchantPage);
        model.addAttribute("category", category);
        return "merchant/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Merchant merchant = merchantService.getMerchantById(id);
        List<Dish> dishes = dishService.getDishesByMerchant(id);
        
        model.addAttribute("merchant", merchant);
        model.addAttribute("dishes", dishes);
        
        return "merchant/detail";
    }
}

