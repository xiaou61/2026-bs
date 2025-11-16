package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.CategoryService;
import com.xiaou.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<CategoryVO>> getCategoryList() {
        return Result.success(categoryService.getCategoryList());
    }
}