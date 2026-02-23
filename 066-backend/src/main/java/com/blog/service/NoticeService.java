package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.BusinessException;
import com.blog.common.PageResult;
import com.blog.entity.Notice;
import com.blog.mapper.NoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    public List<Notice> list() {
        return noticeMapper.selectList(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getStatus, 1)
                .orderByDesc(Notice::getId));
    }

    public PageResult<Notice> page(Integer pageNum, Integer pageSize, String title, Integer status) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        Page<Notice> resultPage = noticeMapper.selectPage(page, new LambdaQueryWrapper<Notice>()
                .like(StringUtils.hasText(title), Notice::getTitle, title)
                .eq(status != null, Notice::getStatus, status)
                .orderByDesc(Notice::getId));
        PageResult<Notice> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void save(Notice notice, Long adminId) {
        if (notice == null || !StringUtils.hasText(notice.getTitle()) || !StringUtils.hasText(notice.getContent())) {
            throw new BusinessException("公告标题和内容不能为空");
        }
        notice.setTitle(notice.getTitle().trim());
        notice.setContent(notice.getContent().trim());
        notice.setStatus(notice.getStatus() == null ? 1 : notice.getStatus());
        notice.setAdminId(adminId);
        if (notice.getId() == null) {
            noticeMapper.insert(notice);
        } else {
            Notice db = noticeMapper.selectById(notice.getId());
            if (db == null) {
                throw new BusinessException("公告不存在");
            }
            noticeMapper.updateById(notice);
        }
    }

    public void deleteById(Long id) {
        noticeMapper.deleteById(id);
    }
}
