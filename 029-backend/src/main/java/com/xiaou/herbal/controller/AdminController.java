package com.xiaou.herbal.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.herbal.common.Constants;
import com.xiaou.herbal.common.Result;
import com.xiaou.herbal.entity.*;
import com.xiaou.herbal.mapper.*;
import com.xiaou.herbal.service.CreatorAuthService;
import com.xiaou.herbal.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    private final UserMapper userMapper;
    private final RecipeMapper recipeMapper;
    private final TopicMapper topicMapper;
    private final CommentMapper commentMapper;
    private final CreatorAuthService creatorAuthService;

    public AdminController(UserMapper userMapper, RecipeMapper recipeMapper, TopicMapper topicMapper,
                         CommentMapper commentMapper, CreatorAuthService creatorAuthService) {
        this.userMapper = userMapper;
        this.recipeMapper = recipeMapper;
        this.topicMapper = topicMapper;
        this.commentMapper = commentMapper;
        this.creatorAuthService = creatorAuthService;
    }

    private boolean isAdmin(Long userId) {
        User user = userMapper.selectById(userId);
        return user != null && user.getUserType().equals(Constants.UserType.ADMIN);
    }

    @GetMapping("/users")
    public Result<List<User>> getUsers() {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            List<User> users = userMapper.selectList(null);
            return Result.success(users);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/user/{id}/status")
    public Result<Void> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            User user = userMapper.selectById(id);
            user.setStatus(status);
            user.setUpdateTime(LocalDateTime.now());
            userMapper.updateById(user);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/recipes/pending")
    public Result<List<Recipe>> getPendingRecipes() {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Recipe::getStatus, Constants.RecipeStatus.REVIEWING);
            List<Recipe> recipes = recipeMapper.selectList(wrapper);
            return Result.success(recipes);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/recipe/{id}/audit")
    public Result<Void> auditRecipe(@PathVariable Long id, @RequestParam Integer status) {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            Recipe recipe = recipeMapper.selectById(id);
            recipe.setStatus(status);
            recipe.setUpdateTime(LocalDateTime.now());
            recipeMapper.updateById(recipe);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/auth/pending")
    public Result<List<CreatorAuth>> getPendingAuths() {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            List<CreatorAuth> auths = creatorAuthService.getPendingAuths();
            return Result.success(auths);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/auth/{id}/approve")
    public Result<Void> approveAuth(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            creatorAuthService.approveAuth(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/auth/{id}/reject")
    public Result<Void> rejectAuth(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            creatorAuthService.rejectAuth(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/topics")
    public Result<List<Topic>> getTopics() {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            List<Topic> topics = topicMapper.selectList(null);
            return Result.success(topics);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/topic/{id}")
    public Result<Void> deleteTopic(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            topicMapper.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/comments")
    public Result<List<Comment>> getComments() {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            List<Comment> comments = commentMapper.selectList(null);
            return Result.success(comments);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/comment/{id}")
    public Result<Void> deleteComment(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null || !isAdmin(userId)) {
            return Result.error(401, "无权限");
        }
        try {
            commentMapper.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
