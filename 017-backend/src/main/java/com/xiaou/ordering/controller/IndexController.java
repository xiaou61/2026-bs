package com.xiaou.ordering.controller;

import com.xiaou.ordering.entity.Dish;
import com.xiaou.ordering.entity.Merchant;
import com.xiaou.ordering.service.DishService;
import com.xiaou.ordering.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private DishService dishService;

    @GetMapping("/index")
    public String index(Model model) {
        List<Merchant> merchants = merchantService.getRecommendMerchants();
        List<Dish> dishes = dishService.getRecommendDishes();
        
        model.addAttribute("merchants", merchants);
        model.addAttribute("dishes", dishes);
        
        return "index";
    }
}

