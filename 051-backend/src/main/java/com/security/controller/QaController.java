package com.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.security.common.Result;
import com.security.dto.QaPostDTO;
import com.security.dto.QaReplyDTO;
import com.security.service.QaService;
import com.security.vo.QaPostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/qa")
public class QaController {

    @Autowired
    private QaService qaService;

    @GetMapping("/list")
    public Result<Page<QaPostVO>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(qaService.getPostList(page, size));
    }

    @GetMapping("/{id}")
    public Result<QaPostVO> detail(@PathVariable Long id) {
        return Result.success(qaService.getPostDetail(id));
    }

    @PostMapping("/post")
    public Result<?> createPost(@RequestBody QaPostDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        qaService.createPost(dto, userId);
        return Result.success();
    }

    @PostMapping("/reply")
    public Result<?> createReply(@RequestBody QaReplyDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        qaService.createReply(dto, userId);
        return Result.success();
    }

    @PostMapping("/like/{replyId}")
    public Result<?> likeReply(@PathVariable Long replyId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        qaService.likeReply(replyId, userId);
        return Result.success();
    }
}
