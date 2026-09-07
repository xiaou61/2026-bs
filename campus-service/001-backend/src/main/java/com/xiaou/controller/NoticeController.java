package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Notice;
import com.xiaou.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 公告管理控制器
 * @author xiaou
 */
@Slf4j
@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    
    @Autowired
    private NoticeService noticeService;
    
    /**
     * 发布公告
     */
    @PostMapping("/add")
    public Result<Void> publishNotice(@RequestBody Notice notice, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 只有教师和管理员可以发布公告
        if (!"teacher".equals(role) && !"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        notice.setAuthorId(userId);
        noticeService.publishNotice(notice);
        return Result.success();
    }
    
    /**
     * 分页查询公告列表
     */
    @GetMapping("/list")
    public Result<Page<Notice>> getNoticeList(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(required = false) String category,
                                              @RequestParam(required = false) String keyword) {
        Page<Notice> page = noticeService.getNoticePage(pageNum, pageSize, category, keyword);
        return Result.success(page);
    }
    
    /**
     * 根据ID查询公告详情
     */
    @GetMapping("/detail/{id}")
    public Result<Notice> getNoticeById(@PathVariable Long id) {
        Notice notice = noticeService.getById(id);
        if (notice != null) {
            // 增加浏览次数
            noticeService.incrementViewCount(id);
        }
        return Result.success(notice);
    }
    
    /**
     * 更新公告
     */
    @PutMapping("/update")
    public Result<Void> updateNotice(@RequestBody Notice notice, HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 检查权限：只有发布者本人或管理员可以修改
        Notice existNotice = noticeService.getById(notice.getId());
        if (existNotice == null) {
            return Result.error("公告不存在");
        }
        
        if (!"admin".equals(role) && !currentUserId.equals(existNotice.getAuthorId())) {
            return Result.error(403, "权限不足");
        }
        
        noticeService.updateById(notice);
        return Result.success();
    }
    
    /**
     * 删除公告
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteNotice(@PathVariable Long id, HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 检查权限：只有发布者本人或管理员可以删除
        Notice existNotice = noticeService.getById(id);
        if (existNotice == null) {
            return Result.error("公告不存在");
        }
        
        if (!"admin".equals(role) && !currentUserId.equals(existNotice.getAuthorId())) {
            return Result.error(403, "权限不足");
        }
        
        noticeService.removeById(id);
        return Result.success();
    }
    
    /**
     * 置顶/取消置顶公告
     */
    @PostMapping("/toggle-top/{id}")
    public Result<Void> toggleTop(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        
        // 只有管理员可以置顶公告
        if (!"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        noticeService.toggleTop(id);
        return Result.success();
    }
}
