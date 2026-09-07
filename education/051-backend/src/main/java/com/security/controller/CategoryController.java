package com.security.controller;

import com.security.common.Result;
import com.security.entity.KnowledgeCategory;
import com.security.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result<List<KnowledgeCategory>> list() {
        return Result.success(articleService.getCategoryList());
    }
}
