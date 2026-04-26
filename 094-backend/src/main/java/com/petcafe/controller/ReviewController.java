package com.petcafe.controller;

import com.petcafe.common.Result;
import com.petcafe.entity.ReviewRecord;
import com.petcafe.service.ReviewService;
import com.petcafe.utils.AuthUtils;
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
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String reviewStatus,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(reviewService.page(pageNum, pageSize, reviewStatus, userId, role));
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody ReviewRecord review, HttpServletRequest request) {
        reviewService.add((Long) request.getAttribute("userId"), review);
        return Result.success();
    }

    @PutMapping("/reply/{id}")
    public Result<?> reply(@PathVariable Long id, @RequestBody Map<String, String> params, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        reviewService.reply(id, (Long) request.getAttribute("userId"), params.get("replyContent"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        reviewService.deleteById(id);
        return Result.success();
    }
}
