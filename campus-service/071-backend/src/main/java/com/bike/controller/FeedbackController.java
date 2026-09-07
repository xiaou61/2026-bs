package com.bike.controller;

import com.bike.common.Result;
import com.bike.entity.Feedback;
import com.bike.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public Result<?> add(HttpServletRequest request, @RequestBody Feedback feedback) {
        Long userId = (Long) request.getAttribute("userId");
        feedback.setUserId(userId);
        feedbackService.add(feedback);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> getList(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) Integer status,
                             @RequestParam(required = false) Integer type) {
        return Result.success(feedbackService.getList(pageNum, pageSize, status, type));
    }

    @GetMapping("/my")
    public Result<?> getMyList(HttpServletRequest request,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(feedbackService.getMyList(userId, pageNum, pageSize));
    }

    @PutMapping("/reply/{id}")
    public Result<?> reply(@PathVariable Long id, @RequestBody Map<String, String> params) {
        feedbackService.reply(id, params.get("reply"));
        return Result.success();
    }
}
