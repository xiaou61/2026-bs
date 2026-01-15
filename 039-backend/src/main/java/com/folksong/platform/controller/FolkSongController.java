package com.folksong.platform.controller;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.common.Result;
import com.folksong.platform.dto.FolkSongDTO;
import com.folksong.platform.service.FolkSongService;
import com.folksong.platform.vo.FolkSongVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "民歌管理")
@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class FolkSongController {

    private final FolkSongService folkSongService;

    @Operation(summary = "获取已审核的民歌列表")
    @GetMapping
    public Result<PageResult<FolkSongVO>> getApprovedSongs(@RequestParam(defaultValue = "1") Integer pageNum,
                                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(folkSongService.getApprovedSongs(pageNum, pageSize));
    }

    @Operation(summary = "根据分类获取民歌列表")
    @GetMapping("/category/{categoryId}")
    public Result<PageResult<FolkSongVO>> getSongsByCategory(@PathVariable Long categoryId,
                                                              @RequestParam(defaultValue = "1") Integer pageNum,
                                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(folkSongService.getSongsByCategory(categoryId, pageNum, pageSize));
    }

    @Operation(summary = "搜索民歌")
    @GetMapping("/search")
    public Result<PageResult<FolkSongVO>> searchSongs(@RequestParam String keyword,
                                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(folkSongService.searchSongs(keyword, pageNum, pageSize));
    }

    @Operation(summary = "获取热门民歌")
    @GetMapping("/hot")
    public Result<List<FolkSongVO>> getHotSongs(@RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(folkSongService.getHotSongs(limit));
    }

    @Operation(summary = "获取最新民歌")
    @GetMapping("/latest")
    public Result<List<FolkSongVO>> getLatestSongs(@RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(folkSongService.getLatestSongs(limit));
    }

    @Operation(summary = "获取民歌详情")
    @GetMapping("/{id}")
    public Result<FolkSongVO> getSongDetail(@PathVariable Long id, Authentication authentication) {
        Long currentUserId = authentication != null ? (Long) authentication.getPrincipal() : null;
        return Result.success(folkSongService.getSongDetail(id, currentUserId));
    }

    @Operation(summary = "发布民歌")
    @PostMapping
    public Result<Void> createSong(Authentication authentication, @Valid @RequestBody FolkSongDTO dto) {
        Long userId = (Long) authentication.getPrincipal();
        folkSongService.createSong(userId, dto);
        return Result.success();
    }

    @Operation(summary = "更新民歌")
    @PutMapping
    public Result<Void> updateSong(Authentication authentication, @Valid @RequestBody FolkSongDTO dto) {
        Long userId = (Long) authentication.getPrincipal();
        folkSongService.updateSong(userId, dto);
        return Result.success();
    }

    @Operation(summary = "删除民歌")
    @DeleteMapping("/{id}")
    public Result<Void> deleteSong(Authentication authentication, @PathVariable Long id) {
        Long userId = (Long) authentication.getPrincipal();
        folkSongService.deleteSong(userId, id);
        return Result.success();
    }

    @Operation(summary = "点赞民歌")
    @PostMapping("/{id}/like")
    public Result<Void> likeSong(Authentication authentication, @PathVariable Long id) {
        Long userId = (Long) authentication.getPrincipal();
        folkSongService.likeSong(userId, id);
        return Result.success();
    }

    @Operation(summary = "取消点赞")
    @DeleteMapping("/{id}/like")
    public Result<Void> unlikeSong(Authentication authentication, @PathVariable Long id) {
        Long userId = (Long) authentication.getPrincipal();
        folkSongService.unlikeSong(userId, id);
        return Result.success();
    }

    @Operation(summary = "收藏民歌")
    @PostMapping("/{id}/collect")
    public Result<Void> collectSong(Authentication authentication, @PathVariable Long id) {
        Long userId = (Long) authentication.getPrincipal();
        folkSongService.collectSong(userId, id);
        return Result.success();
    }

    @Operation(summary = "取消收藏")
    @DeleteMapping("/{id}/collect")
    public Result<Void> uncollectSong(Authentication authentication, @PathVariable Long id) {
        Long userId = (Long) authentication.getPrincipal();
        folkSongService.uncollectSong(userId, id);
        return Result.success();
    }
}
