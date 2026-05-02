package com.eldercare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.common.Result;
import com.eldercare.entity.CheckItem;
import com.eldercare.service.CheckItemService;
import com.eldercare.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/item")
public class CheckItemController {

    @Autowired
    private CheckItemService checkItemService;

    @GetMapping("/list")
    public Result<Page<CheckItem>> list(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(required = false) String itemName,
                                        @RequestParam(required = false) Integer status,
                                        HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "doctor", "nurse", "reception");
        return Result.success(checkItemService.page(pageNum, pageSize, itemName, status));
    }

    @GetMapping("/all")
    public Result<List<CheckItem>> all(HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "doctor", "nurse", "reception");
        return Result.success(checkItemService.listAll());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CheckItem checkItem, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        checkItemService.add(checkItem);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CheckItem checkItem, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        checkItemService.update(checkItem);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        checkItemService.delete(id);
        return Result.success();
    }
}
