package com.teacher.new.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.new.common.BusinessException;
import com.teacher.new.common.PageResult;
import com.teacher.new.entity.EvaluationNotice;
import com.teacher.new.mapper.EvaluationNoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    @Resource
    private EvaluationNoticeMapper evaluationNoticeMapper;

    public List<EvaluationNotice> list() {
        return evaluationNoticeMapper.selectList(new LambdaQueryWrapper<EvaluationNotice>()
                .eq(EvaluationNotice::getStatus, 1)
                .orderByDesc(EvaluationNotice::getPublishTime)
                .orderByDesc(EvaluationNotice::getId));
    }

    public PageResult<EvaluationNotice> page(Integer pageNum, Integer pageSize, String title, Integer status) {
        Page<EvaluationNotice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<EvaluationNotice> wrapper = new LambdaQueryWrapper<EvaluationNotice>()
                .like(StringUtils.hasText(title), EvaluationNotice::getTitle, title == null ? null : title.trim())
                .eq(status != null, EvaluationNotice::getStatus, status)
                .orderByDesc(EvaluationNotice::getId);
        Page<EvaluationNotice> resultPage = evaluationNoticeMapper.selectPage(page, wrapper);
        PageResult<EvaluationNotice> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void save(EvaluationNotice notice, Long creatorId) {
        if (notice == null || !StringUtils.hasText(notice.getTitle()) || !StringUtils.hasText(notice.getContentText())) {
            throw new BusinessException("公告参数不完整");
        }
        if (notice.getId() == null) {
            add(notice, creatorId);
        } else {
            update(notice);
        }
    }

    private void add(EvaluationNotice notice, Long creatorId) {
        notice.setTitle(notice.getTitle().trim());
        notice.setContentText(notice.getContentText().trim());
        notice.setStatus(notice.getStatus() == null ? 1 : (notice.getStatus() == 0 ? 0 : 1));
        notice.setCreatorId(creatorId);
        if (notice.getStatus() == 1) {
            notice.setPublishTime(LocalDateTime.now());
        }
        evaluationNoticeMapper.insert(notice);
    }

    private void update(EvaluationNotice notice) {
        EvaluationNotice db = evaluationNoticeMapper.selectById(notice.getId());
        if (db == null) {
            throw new BusinessException("公告不存在");
        }
        db.setTitle(notice.getTitle().trim());
        db.setContentText(notice.getContentText().trim());
        if (notice.getStatus() != null) {
            db.setStatus(notice.getStatus() == 0 ? 0 : 1);
            if (db.getStatus() == 1 && db.getPublishTime() == null) {
                db.setPublishTime(LocalDateTime.now());
            }
        }
        evaluationNoticeMapper.updateById(db);
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        EvaluationNotice db = evaluationNoticeMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("公告不存在");
        }
        db.setStatus(status);
        if (status == 1 && db.getPublishTime() == null) {
            db.setPublishTime(LocalDateTime.now());
        }
        evaluationNoticeMapper.updateById(db);
    }

    public void deleteById(Long id) {
        evaluationNoticeMapper.deleteById(id);
    }

    public Long countAll() {
        return evaluationNoticeMapper.selectCount(null);
    }

    public Long countActive() {
        return evaluationNoticeMapper.selectCount(new LambdaQueryWrapper<EvaluationNotice>().eq(EvaluationNotice::getStatus, 1));
    }
}
