package com.folksong.platform.controller;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.common.Result;
import com.folksong.platform.dto.UserUpdateDTO;
import com.folksong.platform.service.FolkSongService;
import com.folksong.platform.service.UserService;
import com.folksong.platform.vo.FolkSongVO;
import com.folksong.platform.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FolkSongService folkSongService;

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<UserVO> getCurrentUser(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(userService.getCurrentUser(userId));
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/info")
    public Result<Void> updateUser(Authentication authentication, @RequestBody UserUpdateDTO dto) {
        Long userId = (Long) authentication.getPrincipal();
        userService.updateUser(userId, dto);
        return Result.success();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public Result<Void> changePassword(Authentication authentication, 
                                        @RequestParam String oldPassword, 
                                        @RequestParam String newPassword) {
        Long userId = (Long) authentication.getPrincipal();
        userService.changePassword(userId, oldPassword, newPassword);
        return Result.success();
    }

    @Operation(summary = "获取我的作品列表")
    @GetMapping("/songs")
    public Result<PageResult<FolkSongVO>> getMySongs(Authentication authentication,
                                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(folkSongService.getUserSongs(userId, pageNum, pageSize));
    }

    @Operation(summary = "获取我的收藏列表")
    @GetMapping("/favorites")
    public Result<PageResult<FolkSongVO>> getMyFavorites(Authentication authentication,
                                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(folkSongService.getUserFavorites(userId, pageNum, pageSize));
    }
}
