package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.entity.Notice;
import com.oa.entity.NoticeRead;
import com.oa.entity.User;
import com.oa.mapper.NoticeMapper;
import com.oa.mapper.NoticeReadMapper;
import com.oa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper noticeMapper;
    private final NoticeReadMapper noticeReadMapper;
    private final UserMapper userMapper;

    public Page<Notice> getList(Integer pageNum, Integer pageSize, String keyword) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Notice::getTitle, keyword);
        }
        wrapper.orderByDesc(Notice::getIsTop).orderByDesc(Notice::getCreateTime);
        Page<Notice> result = noticeMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillPublisher);
        return result;
    }

    public Page<Notice> getPublished(Integer pageNum, Integer pageSize, String keyword) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getStatus, 1);
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Notice::getTitle, keyword);
        }
        wrapper.orderByDesc(Notice::getIsTop).orderByDesc(Notice::getPublishTime);
        Page<Notice> result = noticeMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillPublisher);
        return result;
    }

    private void fillPublisher(Notice notice) {
        User user = userMapper.selectById(notice.getPublisherId());
        if (user != null) notice.setPublisherName(user.getRealName());
    }

    public void add(Long userId, Notice notice) {
        notice.setPublisherId(userId);
        notice.setStatus(0);
        noticeMapper.insert(notice);
    }

    public void update(Notice notice) {
        noticeMapper.updateById(notice);
    }

    public void delete(Long id) {
        noticeMapper.deleteById(id);
        noticeReadMapper.delete(new LambdaQueryWrapper<NoticeRead>().eq(NoticeRead::getNoticeId, id));
    }

    public void publish(Long id) {
        Notice notice = noticeMapper.selectById(id);
        notice.setStatus(1);
        notice.setPublishTime(LocalDateTime.now());
        noticeMapper.updateById(notice);
    }

    public void read(Long userId, Long noticeId) {
        NoticeRead exist = noticeReadMapper.selectOne(new LambdaQueryWrapper<NoticeRead>()
                .eq(NoticeRead::getNoticeId, noticeId).eq(NoticeRead::getUserId, userId));
        if (exist == null) {
            NoticeRead read = new NoticeRead();
            read.setNoticeId(noticeId);
            read.setUserId(userId);
            read.setReadTime(LocalDateTime.now());
            noticeReadMapper.insert(read);
        }
    }
}
