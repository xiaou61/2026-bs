package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.UserIngredient;
import com.xiaou.service.UserIngredientService;
import com.xiaou.vo.UserIngredientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-ingredient")
public class UserIngredientController {

    @Autowired
    private UserIngredientService userIngredientService;

    @GetMapping("/list")
    public Result<List<UserIngredientVO>> getList(@RequestAttribute("userId") Long userId) {
        List<UserIngredientVO> list = userIngredientService.getUserIngredientList(userId);
        return Result.success(list);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestAttribute("userId") Long userId,
                              @RequestBody UserIngredient userIngredient) {
        userIngredient.setUserId(userId);
        userIngredient.setStatus(1);
        userIngredientService.addUserIngredient(userIngredient);
        return Result.success("添加成功");
    }

    @PutMapping("/{id}")
    public Result<String> update(@PathVariable Long id,
                                 @RequestBody UserIngredient userIngredient) {
        userIngredientService.updateUserIngredient(id, userIngredient);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId) {
        userIngredientService.deleteUserIngredient(id, userId);
        return Result.success("删除成功");
    }
}

