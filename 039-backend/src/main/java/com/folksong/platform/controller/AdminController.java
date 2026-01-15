package com.folksong.platform.controller;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.common.Result;
import com.folksong.platform.dto.AnnouncementDTO;
import com.folksong.platform.dto.CategoryDTO;
import com.folksong.platform.entity.Announcement;
import com.folksong.platform.entity.Category;
import com.folksong.platform.service.*;
import com.folksong.platform.vo.CommentVO;
import com.folksong.platform.vo.FolkSongVO;
import com.folksong.platform.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "管理员接口")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final FolkSongService folkSongService;
    private final CommentService commentService;
    private final AnnouncementService announcementService;

    @Operation(summary = "获取用户列表")
    @GetMapping("/users")
    public Result<PageResult<UserVO>> getUserList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(required = false) Integer status) {
        return Result.success(userService.getUserList(pageNum, pageSize, status));
    }

    @Operation(summary = "更新用户状态")
    @PutMapping("/users/{id}/status")
    public Result<Void> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateUserStatus(id, status);
        return Result.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/users/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }

    @Operation(summary = "获取所有分类")
    @GetMapping("/categories")
    public Result<List<Category>> getAllCategories() {
        return Result.success(categoryService.getAllCategories());
    }

    @Operation(summary = "创建分类")
    @PostMapping("/categories")
    public Result<Void> createCategory(@Valid @RequestBody CategoryDTO dto) {
        categoryService.createCategory(dto);
        return Result.success();
    }

    @Operation(summary = "更新分类")
    @PutMapping("/categories")
    public Result<Void> updateCategory(@Valid @RequestBody CategoryDTO dto) {
        categoryService.updateCategory(dto);
        return Result.success();
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/categories/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }

    @Operation(summary = "获取所有民歌")
    @GetMapping("/songs")
    public Result<PageResult<FolkSongVO>> getAllSongs(@RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(folkSongService.getAllSongs(pageNum, pageSize));
    }

    @Operation(summary = "获取待审核民歌")
    @GetMapping("/songs/pending")
    public Result<PageResult<FolkSongVO>> getPendingSongs(@RequestParam(defaultValue = "1") Integer pageNum,
                                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(folkSongService.getPendingSongs(pageNum, pageSize));
    }

    @Operation(summary = "审核民歌")
    @PutMapping("/songs/{id}/audit")
    public Result<Void> auditSong(@PathVariable Long id, @RequestParam Integer auditStatus) {
        folkSongService.auditSong(id, auditStatus);
        return Result.success();
    }

    @Operation(summary = "获取所有评论")
    @GetMapping("/comments")
    public Result<PageResult<CommentVO>> getAllComments(@RequestParam(defaultValue = "1") Integer pageNum,
                                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(commentService.getAllComments(pageNum, pageSize));
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/comments/{id}")
    public Result<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteCommentByAdmin(id);
        return Result.success();
    }

    @Operation(summary = "获取所有公告")
    @GetMapping("/announcements")
    public Result<PageResult<Announcement>> getAllAnnouncements(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(announcementService.getAllAnnouncements(pageNum, pageSize));
    }

    @Operation(summary = "创建公告")
    @PostMapping("/announcements")
    public Result<Void> createAnnouncement(Authentication authentication, @Valid @RequestBody AnnouncementDTO dto) {
        Long publisherId = (Long) authentication.getPrincipal();
        announcementService.createAnnouncement(publisherId, dto);
        return Result.success();
    }

    @Operation(summary = "更新公告")
    @PutMapping("/announcements")
    public Result<Void> updateAnnouncement(@Valid @RequestBody AnnouncementDTO dto) {
        announcementService.updateAnnouncement(dto);
        return Result.success();
    }

    @Operation(summary = "删除公告")
    @DeleteMapping("/announcements/{id}")
    public Result<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return Result.success();
    }
}
