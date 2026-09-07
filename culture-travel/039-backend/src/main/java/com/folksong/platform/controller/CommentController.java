package com.folksong.platform.controller;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.common.Result;
import com.folksong.platform.dto.CommentDTO;
import com.folksong.platform.service.CommentService;
import com.folksong.platform.vo.CommentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "评论管理")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "获取民歌评论列表")
    @GetMapping("/song/{songId}")
    public Result<PageResult<CommentVO>> getCommentsBySongId(@PathVariable Long songId,
                                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(commentService.getCommentsBySongId(songId, pageNum, pageSize));
    }

    @Operation(summary = "发表评论")
    @PostMapping
    public Result<Void> createComment(Authentication authentication, @Valid @RequestBody CommentDTO dto) {
        Long userId = (Long) authentication.getPrincipal();
        commentService.createComment(userId, dto);
        return Result.success();
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/{id}")
    public Result<Void> deleteComment(Authentication authentication, @PathVariable Long id) {
        Long userId = (Long) authentication.getPrincipal();
        commentService.deleteComment(userId, id);
        return Result.success();
    }
}
