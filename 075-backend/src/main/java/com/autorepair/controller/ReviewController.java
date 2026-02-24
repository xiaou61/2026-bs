package com.autorepair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.autorepair.common.BusinessException;
import com.autorepair.common.Result;
import com.autorepair.entity.Review;
import com.autorepair.service.ReviewService;
import com.autorepair.vo.ReviewVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    @GetMapping("/item/{serviceId}")
    public Result<?> listByItem(@PathVariable Long serviceId) {
        return Result.success(reviewService.listByItem(serviceId));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long serviceId,
                          @RequestParam(required = false) Long userId,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        Page<ReviewVO> page = reviewService.page(pageNum, pageSize, serviceId, userId);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> add(@RequestBody Review review, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        reviewService.add(review, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        reviewService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}



