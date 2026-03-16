package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.entity.SystemNotice;
import com.railway.mapper.NoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    public PageResult<SystemNotice> page(Integer pageNum, Integer pageSize, String title, Integer publishStatus) {
        Page<SystemNotice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SystemNotice> wrapper = new LambdaQueryWrapper<SystemNotice>()
                .like(StringUtils.hasText(title), SystemNotice::getTitle, title == null ? null : title.trim())
                .eq(publishStatus != null, SystemNotice::getPublishStatus, publishStatus)
                .orderByDesc(SystemNotice::getSortNo)
                .orderByDesc(SystemNotice::getPublishTime);
        Page<SystemNotice> resultPage = noticeMapper.selectPage(page, wrapper);
        PageResult<SystemNotice> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<SystemNotice> publicList() {
        return noticeMapper.selectList(new LambdaQueryWrapper<SystemNotice>()
                .eq(SystemNotice::getPublishStatus, 1)
                .orderByDesc(SystemNotice::getSortNo)
                .orderByDesc(SystemNotice::getPublishTime));
    }

    public void save(SystemNotice notice) {
        if (notice == null || !StringUtils.hasText(notice.getTitle()) || !StringUtils.hasText(notice.getContent())) {
            throw new BusinessException("公告标题和内容不能为空");
        }
        if (notice.getId() == null) {
            notice.setTitle(notice.getTitle().trim());
            notice.setContent(notice.getContent().trim());
            notice.setPublishStatus(notice.getPublishStatus() == null ? 1 : notice.getPublishStatus());
            notice.setSortNo(notice.getSortNo() == null ? 0 : notice.getSortNo());
            notice.setPublishTime(notice.getPublishStatus() != null && notice.getPublishStatus() == 1 ? LocalDateTime.now() : null);
            noticeMapper.insert(notice);
            return;
        }
        SystemNotice db = noticeMapper.selectById(notice.getId());
        if (db == null) {
            throw new BusinessException("公告不存在");
        }
        db.setTitle(notice.getTitle().trim());
        db.setContent(notice.getContent().trim());
        db.setSortNo(notice.getSortNo() == null ? 0 : notice.getSortNo());
        db.setPublishStatus(notice.getPublishStatus() == null ? db.getPublishStatus() : notice.getPublishStatus());
        if (db.getPublishStatus() != null && db.getPublishStatus() == 1 && db.getPublishTime() == null) {
            db.setPublishTime(LocalDateTime.now());
        }
        noticeMapper.updateById(db);
    }

    public void deleteById(Long id) {
        noticeMapper.deleteById(id);
    }
}
