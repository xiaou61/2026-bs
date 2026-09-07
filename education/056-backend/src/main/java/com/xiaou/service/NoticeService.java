package com.xiaou.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.entity.Competition;
import com.xiaou.entity.Notice;
import com.xiaou.mapper.CompetitionMapper;
import com.xiaou.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private CompetitionMapper competitionMapper;

    public Page<Notice> getList(Integer pageNum, Integer pageSize, Integer type, Integer status, String keyword) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(Notice::getType, type);
        }
        if (status != null) {
            wrapper.eq(Notice::getStatus, status);
        }
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(Notice::getTitle, keyword);
        }
        wrapper.orderByDesc(Notice::getTop).orderByDesc(Notice::getCreateTime);
        Page<Notice> result = noticeMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillNoticeInfo);
        return result;
    }

    public List<Notice> getPublicList(Integer type, Long competitionId) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getStatus, 1);
        if (type != null) {
            wrapper.eq(Notice::getType, type);
        }
        if (competitionId != null) {
            wrapper.eq(Notice::getCompetitionId, competitionId);
        }
        wrapper.orderByDesc(Notice::getTop).orderByDesc(Notice::getCreateTime);
        List<Notice> list = noticeMapper.selectList(wrapper);
        list.forEach(this::fillNoticeInfo);
        return list;
    }

    public Notice getById(Long id) {
        Notice notice = noticeMapper.selectById(id);
        if (notice != null) {
            fillNoticeInfo(notice);
        }
        return notice;
    }

    public void save(Notice notice, Long userId) {
        if (notice.getId() == null) {
            notice.setCreateUser(userId);
            noticeMapper.insert(notice);
        } else {
            noticeMapper.updateById(notice);
        }
    }

    public void delete(Long id) {
        noticeMapper.deleteById(id);
    }

    private void fillNoticeInfo(Notice notice) {
        if (notice.getCompetitionId() != null) {
            Competition competition = competitionMapper.selectById(notice.getCompetitionId());
            if (competition != null) {
                notice.setCompetitionTitle(competition.getTitle());
            }
        }
    }
}
