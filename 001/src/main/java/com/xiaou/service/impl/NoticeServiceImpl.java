package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Notice;
import com.xiaou.entity.User;
import com.xiaou.mapper.NoticeMapper;
import com.xiaou.service.NoticeService;
import com.xiaou.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公告Service实现类
 * @author xiaou
 */
@Slf4j
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void publishNotice(Notice notice) {
        // 初始化浏览次数和置顶状态
        if (notice.getViewCount() == null) {
            notice.setViewCount(0);
        }
        if (notice.getIsTop() == null) {
            notice.setIsTop(0);
        }
        save(notice);
    }
    
    @Override
    public Page<Notice> getNoticePage(int pageNum, int pageSize, String category, String keyword) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Notice::getCategory, category);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Notice::getTitle, keyword)
                    .or().like(Notice::getContent, keyword);
        }
        
        wrapper.orderByDesc(Notice::getIsTop)
                .orderByDesc(Notice::getCreateTime);
        
        Page<Notice> resultPage = page(page, wrapper);
        
        // 填充发布者姓名
        for (Notice record : resultPage.getRecords()) {
            User author = userService.getById(record.getAuthorId());
            if (author != null) {
                record.setAuthorName(author.getRealName() != null ? author.getRealName() : author.getUsername());
            }
        }
        
        return resultPage;
    }
    
    @Override
    public void incrementViewCount(Long id) {
        Notice notice = getById(id);
        if (notice != null) {
            notice.setViewCount(notice.getViewCount() + 1);
            updateById(notice);
        }
    }
    
    @Override
    public void toggleTop(Long id) {
        Notice notice = getById(id);
        if (notice == null) {
            throw new BusinessException("公告不存在");
        }
        
        notice.setIsTop(notice.getIsTop() == 1 ? 0 : 1);
        updateById(notice);
    }
}

