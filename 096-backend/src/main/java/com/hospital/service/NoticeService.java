package com.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.common.BusinessException;
import com.hospital.entity.NewsNotice;
import com.hospital.mapper.NewsNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NewsNoticeMapper newsNoticeMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    public PageInfo<NewsNotice> page(String keyword, Integer status, Integer pageNum, Integer pageSize, String role) {
        authService.assertAdmin(role);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(newsNoticeMapper.selectPage(keyword, status));
    }

    public List<NewsNotice> publicList() {
        return newsNoticeMapper.selectPublicList();
    }

    public void save(NewsNotice entity, Long userId, String role) {
        authService.assertAdmin(role);
        if (entity == null || !StringUtils.hasText(entity.getTitle()) || !StringUtils.hasText(entity.getContent())) {
            throw new BusinessException("公告标题和内容不能为空");
        }
        if (entity.getId() == null) {
            entity.setAdminId(userId);
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setSort(entity.getSort() == null ? 0 : entity.getSort());
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            newsNoticeMapper.insert(entity);
            operationLogService.record(userId, role, "公告", "新增", "新增公告");
            return;
        }
        entity.setUpdateTime(LocalDateTime.now());
        newsNoticeMapper.update(entity);
        operationLogService.record(userId, role, "公告", "编辑", "编辑公告");
    }

    public void delete(Long id, Long userId, String role) {
        authService.assertAdmin(role);
        newsNoticeMapper.deleteById(id);
        operationLogService.record(userId, role, "公告", "删除", "删除公告");
    }
}
