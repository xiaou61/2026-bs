package com.classic.controller;

import com.classic.common.BusinessException;
import com.classic.common.Result;
import com.classic.entity.Ingredient;
import com.classic.service.IngredientService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

    @Resource
    private IngredientService ingredientService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(ingredientService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) Integer status) {
        return Result.success(ingredientService.page(pageNum, pageSize, name, categoryId, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody Ingredient ingredient, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        ingredientService.save(ingredient);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Ingredient ingredient, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        ingredientService.save(ingredient);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        ingredientService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
