package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.ShoppingGenerateDTO;
import com.xiaou.service.ShoppingListService;
import com.xiaou.vo.ShoppingListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingListService shoppingListService;

    @PostMapping("/generate")
    public Result<String> generate(@RequestAttribute("userId") Long userId,
                                   @RequestBody ShoppingGenerateDTO dto) {
        shoppingListService.generateShoppingList(userId, dto);
        return Result.success("生成购物清单成功");
    }

    @GetMapping("/list")
    public Result<List<ShoppingListVO>> getList(@RequestAttribute("userId") Long userId) {
        List<ShoppingListVO> list = shoppingListService.getShoppingList(userId);
        return Result.success(list);
    }

    @PutMapping("/{id}/check")
    public Result<String> check(@PathVariable Long id,
                                @RequestAttribute("userId") Long userId) {
        shoppingListService.checkItem(id, userId);
        return Result.success("操作成功");
    }

    @PostMapping("/add-to-stock")
    public Result<String> addToStock(@RequestAttribute("userId") Long userId) {
        shoppingListService.addToStock(userId);
        return Result.success("已添加到库存");
    }

    @DeleteMapping("/clear")
    public Result<String> clear(@RequestAttribute("userId") Long userId) {
        shoppingListService.clearList(userId);
        return Result.success("已清空购物清单");
    }
}

