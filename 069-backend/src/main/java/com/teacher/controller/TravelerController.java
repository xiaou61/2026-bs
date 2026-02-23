package com.teacher.controller;

import com.teacher.common.Result;
import com.teacher.entity.Traveler;
import com.teacher.service.TravelerService;
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
@RequestMapping("/api/traveler")
public class TravelerController {

    @Resource
    private TravelerService travelerService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(travelerService.page(userId, pageNum, pageSize, name));
    }

    @GetMapping("/list")
    public Result<?> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(travelerService.list(userId));
    }

    @PostMapping
    public Result<?> add(@RequestBody Traveler traveler, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        travelerService.save(userId, traveler);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Traveler traveler, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        travelerService.save(userId, traveler);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        travelerService.deleteById(userId, id);
        return Result.success();
    }
}


