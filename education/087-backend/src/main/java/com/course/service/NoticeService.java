package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.SystemNotice;
import com.course.mapper.SystemNoticeMapper;
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

    public PageInfo<SystemNotice> list(String title, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SystemNotice> list = noticeMapper.selectList(title, status);
        return new PageInfo<>(list);
    }

    public List<SystemNotice> publicList() {
        return noticeMapper.selectPublicList();
    }

    public void add(SystemNotice entity, Long userId, String role) {
        authService.assertAdmin(role);
        validate(entity);
        entity.setPublisherId(userId);
        if (entity.getPublishTime() == null) {
            entity.setPublishTime(LocalDateTime.now());
        }
        if (entity.getViewCount() == null) {
            entity.setViewCount(0);
        }
        noticeMapper.insert(entity);
    }

    public void update(SystemNotice entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("公告ID不能为空");
        }
        validate(entity);
        noticeMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        noticeMapper.deleteById(id);
    }

    private void validate(SystemNotice entity) {
        if (entity == null || !StringUtils.hasText(entity.getTitle()) || !StringUtils.hasText(entity.getContent())) {
            throw new BusinessException("公告标题和内容不能为空");
        }
    }
}
