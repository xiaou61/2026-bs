package com.xiaou.campusclub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.common.Result;
import com.xiaou.campusclub.dto.CircleRequest;
import com.xiaou.campusclub.entity.Circle;
import com.xiaou.campusclub.service.CircleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/circles")
public class CircleController {
    
    @Autowired
    private CircleService circleService;
    
    @GetMapping
    public Result<IPage<Circle>> getCircleList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category) {
        return Result.success(circleService.getCircleList(page, size, category));
    }
    
    @GetMapping("/{id}")
    public Result<Circle> getCircleDetail(@PathVariable Long id) {
        return Result.success(circleService.getCircleDetail(id));
    }
    
    @PostMapping
    public Result<Long> createCircle(HttpServletRequest request, @RequestBody CircleRequest circleRequest) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(circleService.createCircle(userId, circleRequest));
    }
    
    @PostMapping("/{id}/join")
    public Result<Void> joinCircle(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        circleService.joinCircle(id, userId);
        return Result.success();
    }
    
    @DeleteMapping("/{id}/leave")
    public Result<Void> leaveCircle(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        circleService.leaveCircle(id, userId);
        return Result.success();
    }
    
    @GetMapping("/my")
    public Result<List<Circle>> getMyCircles(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(circleService.getMyCircles(userId));
    }
    
    @GetMapping("/recommend")
    public Result<List<Circle>> getRecommendCircles(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(circleService.getRecommendCircles(userId));
    }
}

