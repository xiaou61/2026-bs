package com.wallpaper.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wallpaper.common.BusinessException;
import com.wallpaper.entity.SystemNotice;
import com.wallpaper.mapper.SystemNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private SystemNoticeMapper noticeMapper;

    @Autowired
    private AuthService authService;

    public IPage<SystemNotice> list(String title, Integer status, Integer pageNum, Integer pageSize, Long userId) {
        authService.assertAdmin(userId);
        Page<SystemNotice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SystemNotice> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(title), SystemNotice::getTitle, title)
                .eq(status != null, SystemNotice::getStatus, status)
                .orderByDesc(SystemNotice::getId);
        return noticeMapper.selectPage(page, wrapper);
    }

    public List<SystemNotice> publicList() {
        LambdaQueryWrapper<SystemNotice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemNotice::getStatus, 1).orderByDesc(SystemNotice::getPublishTime).orderByDesc(SystemNotice::getId);
        return noticeMapper.selectList(wrapper);
    }

    public void add(SystemNotice notice, Long userId) {
        authService.assertAdmin(userId);
        validate(notice);
        notice.setPublisherId(userId);
        if (notice.getStatus() == null) {
            notice.setStatus(0);
        }
        if (notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        if (notice.getViewCount() == null) {
            notice.setViewCount(0);
        }
        noticeMapper.insert(notice);
    }

    public void update(SystemNotice notice, Long userId) {
        authService.assertAdmin(userId);
        if (notice.getId() == null) {
            throw new BusinessException("公告ID不能为空");
        }
        validate(notice);
        if (notice.getStatus() != null && notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        noticeMapper.updateById(notice);
    }

    public void delete(Long id, Long userId) {
        authService.assertAdmin(userId);
        noticeMapper.deleteById(id);
    }

    private void validate(SystemNotice notice) {
        if (notice == null || !StringUtils.hasText(notice.getTitle()) || !StringUtils.hasText(notice.getContent())) {
            throw new BusinessException("公告标题和内容不能为空");
        }
    }
}
