package com.xiaou.confession.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.confession.entity.*;
import com.xiaou.confession.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final ReportMapper reportMapper;
    private final SensitiveWordMapper sensitiveWordMapper;
    
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalUsers", userMapper.selectCount(null));
        stats.put("totalPosts", postMapper.selectCount(null));
        stats.put("totalComments", commentMapper.selectCount(null));
        
        LambdaQueryWrapper<Report> reportWrapper = new LambdaQueryWrapper<>();
        reportWrapper.eq(Report::getStatus, 0);
        stats.put("pendingReports", reportMapper.selectCount(reportWrapper));
        
        LambdaQueryWrapper<User> authWrapper = new LambdaQueryWrapper<>();
        authWrapper.eq(User::getAuthStatus, 1);
        stats.put("pendingAuth", userMapper.selectCount(authWrapper));
        
        LambdaQueryWrapper<Post> auditWrapper = new LambdaQueryWrapper<>();
        auditWrapper.eq(Post::getStatus, 0);
        stats.put("pendingAudit", postMapper.selectCount(auditWrapper));
        
        return stats;
    }
    
    public IPage<User> getUserList(Integer page, Integer size, String keyword) {
        Page<User> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.and(w -> w.like(User::getStudentId, keyword)
                    .or().like(User::getRealName, keyword)
                    .or().like(User::getPhone, keyword));
        }
        
        wrapper.orderByDesc(User::getCreateTime);
        
        IPage<User> userPage = userMapper.selectPage(pageParam, wrapper);
        userPage.getRecords().forEach(user -> user.setPassword(null));
        
        return userPage;
    }
    
    @Transactional
    public void updateUserStatus(Long userId, Integer status, String reason, Integer days) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        user.setStatus(status);
        user.setBanReason(reason);
        
        if (status == 1 && days != null) {
            user.setBanEndTime(LocalDateTime.now().plusDays(days));
        }
        
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }
    
    public IPage<User> getPendingAuth(Integer page, Integer size) {
        Page<User> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAuthStatus, 1);
        wrapper.orderByDesc(User::getUpdateTime);
        
        IPage<User> userPage = userMapper.selectPage(pageParam, wrapper);
        userPage.getRecords().forEach(user -> user.setPassword(null));
        
        return userPage;
    }
    
    @Transactional
    public void auditAuth(Long userId, Integer status, String reason) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        user.setAuthStatus(status);
        user.setUpdateTime(LocalDateTime.now());
        
        if (status == 2) {
            user.setPoints(user.getPoints() + 10);
        }
        
        userMapper.updateById(user);
    }
    
    public IPage<Post> getPendingPosts(Integer page, Integer size) {
        Page<Post> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 0);
        wrapper.orderByDesc(Post::getCreateTime);
        
        return postMapper.selectPage(pageParam, wrapper);
    }
    
    @Transactional
    public void auditPost(Long postId, Integer status, String reason) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new RuntimeException("帖子不存在");
        }
        
        post.setStatus(status);
        post.setAuditReason(reason);
        post.setUpdateTime(LocalDateTime.now());
        
        postMapper.updateById(post);
    }
    
    @Transactional
    public void deletePost(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new RuntimeException("帖子不存在");
        }
        
        post.setStatus(2);
        post.setUpdateTime(LocalDateTime.now());
        postMapper.updateById(post);
    }
    
    @Transactional
    public void topPost(Long postId, Integer isTop) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new RuntimeException("帖子不存在");
        }
        
        post.setIsTop(isTop);
        post.setUpdateTime(LocalDateTime.now());
        postMapper.updateById(post);
    }
    
    public IPage<Report> getReportList(Integer page, Integer size, Integer status) {
        Page<Report> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Report::getStatus, status);
        }
        
        wrapper.orderByAsc(Report::getStatus);
        wrapper.orderByDesc(Report::getCreateTime);
        
        return reportMapper.selectPage(pageParam, wrapper);
    }
    
    @Transactional
    public void handleReport(Long reportId, Long adminId, Integer status, String result) {
        Report report = reportMapper.selectById(reportId);
        if (report == null) {
            throw new RuntimeException("举报不存在");
        }
        
        report.setStatus(status);
        report.setHandleResult(result);
        report.setHandleAdminId(adminId);
        report.setHandleTime(LocalDateTime.now());
        
        reportMapper.updateById(report);
    }
    
    public IPage<SensitiveWord> getSensitiveWords(Integer page, Integer size, String category) {
        Page<SensitiveWord> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<SensitiveWord> wrapper = new LambdaQueryWrapper<>();
        
        if (category != null && !category.trim().isEmpty()) {
            wrapper.eq(SensitiveWord::getCategory, category);
        }
        
        wrapper.orderByDesc(SensitiveWord::getCreateTime);
        
        return sensitiveWordMapper.selectPage(pageParam, wrapper);
    }
    
    @Transactional
    public void addSensitiveWord(String word, Integer level, String category) {
        SensitiveWord sensitiveWord = new SensitiveWord();
        sensitiveWord.setWord(word);
        sensitiveWord.setLevel(level);
        sensitiveWord.setCategory(category);
        sensitiveWord.setIsEnabled(1);
        sensitiveWord.setCreateTime(LocalDateTime.now());
        
        sensitiveWordMapper.insert(sensitiveWord);
    }
    
    @Transactional
    public void deleteSensitiveWord(Long id) {
        sensitiveWordMapper.deleteById(id);
    }
}

