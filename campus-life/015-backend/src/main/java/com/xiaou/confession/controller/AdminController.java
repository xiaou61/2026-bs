package com.xiaou.confession.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.confession.common.Result;
import com.xiaou.confession.entity.*;
import com.xiaou.confession.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final AdminService adminService;
    
    @GetMapping("/stats/overview")
    public Result<Map<String, Object>> getStatistics() {
        try {
            Map<String, Object> stats = adminService.getStatistics();
            return Result.success(stats);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/users")
    public Result<IPage<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String keyword) {
        try {
            IPage<User> users = adminService.getUserList(page, size, keyword);
            return Result.success(users);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/users/{id}/status")
    public Result<Void> updateUserStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Object> params) {
        try {
            Integer status = (Integer) params.get("status");
            String reason = (String) params.get("reason");
            Integer days = (Integer) params.get("days");
            
            adminService.updateUserStatus(id, status, reason, days);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/auth/pending")
    public Result<IPage<User>> getPendingAuth(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        try {
            IPage<User> users = adminService.getPendingAuth(page, size);
            return Result.success(users);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/auth/{id}/audit")
    public Result<Void> auditAuth(
            @PathVariable Long id,
            @RequestBody Map<String, Object> params) {
        try {
            Integer status = (Integer) params.get("status");
            String reason = (String) params.get("reason");
            
            adminService.auditAuth(id, status, reason);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/posts/pending")
    public Result<IPage<Post>> getPendingPosts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        try {
            IPage<Post> posts = adminService.getPendingPosts(page, size);
            return Result.success(posts);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/posts/{id}/audit")
    public Result<Void> auditPost(
            @PathVariable Long id,
            @RequestBody Map<String, Object> params) {
        try {
            Integer status = (Integer) params.get("status");
            String reason = (String) params.get("reason");
            
            adminService.auditPost(id, status, reason);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/posts/{id}")
    public Result<Void> deletePost(@PathVariable Long id) {
        try {
            adminService.deletePost(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/posts/{id}/top")
    public Result<Void> topPost(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        try {
            Integer isTop = params.get("isTop");
            adminService.topPost(id, isTop);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/reports")
    public Result<IPage<Report>> getReportList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) Integer status) {
        try {
            IPage<Report> reports = adminService.getReportList(page, size, status);
            return Result.success(reports);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/reports/{id}/handle")
    public Result<Void> handleReport(
            @PathVariable Long id,
            @RequestBody Map<String, Object> params,
            HttpServletRequest request) {
        try {
            Long adminId = (Long) request.getAttribute("userId");
            Integer status = (Integer) params.get("status");
            String result = (String) params.get("result");
            
            adminService.handleReport(id, adminId, status, result);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/sensitive-words")
    public Result<IPage<SensitiveWord>> getSensitiveWords(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String category) {
        try {
            IPage<SensitiveWord> words = adminService.getSensitiveWords(page, size, category);
            return Result.success(words);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping("/sensitive-words")
    public Result<Void> addSensitiveWord(@RequestBody Map<String, Object> params) {
        try {
            String word = (String) params.get("word");
            Integer level = (Integer) params.get("level");
            String category = (String) params.get("category");
            
            adminService.addSensitiveWord(word, level, category);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/sensitive-words/{id}")
    public Result<Void> deleteSensitiveWord(@PathVariable Long id) {
        try {
            adminService.deleteSensitiveWord(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

