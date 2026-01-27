package com.online.controller;

import com.online.common.Result;
import com.online.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    public Result<?> getList(@RequestParam Long courseId,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(commentService.getList(courseId, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<?> addComment(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long courseId = Long.valueOf(params.get("courseId").toString());
        String content = (String) params.get("content");
        Integer score = (Integer) params.get("score");
        commentService.addComment(userId, courseId, content, score);
        return Result.success();
    }
}
