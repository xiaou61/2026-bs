package com.gongkao.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.entity.Notice;
import com.gongkao.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class NoticeService {

    private static final String NOTICE_PUBLIC_CACHE_KEY = "gongkao:notice:public:list";

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Page<Notice> getList(int pageNum, int pageSize, String title, String type, Integer status) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(title)) {
            wrapper.like("title", title);
        }
        if (StringUtils.hasText(type)) {
            wrapper.eq("type", type);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return noticeMapper.selectPage(page, wrapper);
    }

    public List<Notice> getPublicList() {
        String cacheValue = stringRedisTemplate.opsForValue().get(NOTICE_PUBLIC_CACHE_KEY);
        if (StringUtils.hasText(cacheValue)) {
            return JSON.parseArray(cacheValue, Notice.class);
        }
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).orderByDesc("publish_time").orderByDesc("create_time");
        List<Notice> list = noticeMapper.selectList(wrapper);
        stringRedisTemplate.opsForValue().set(NOTICE_PUBLIC_CACHE_KEY, JSON.toJSONString(list), 30, TimeUnit.MINUTES);
        return list;
    }

    public Notice getPublicDetail(Long id) {
        Notice notice = noticeMapper.selectById(id);
        if (notice != null && notice.getStatus() != null && notice.getStatus() == 1) {
            Notice update = new Notice();
            update.setId(id);
            update.setViewCount((notice.getViewCount() == null ? 0 : notice.getViewCount()) + 1);
            noticeMapper.updateById(update);
            notice.setViewCount(update.getViewCount());
        }
        return notice;
    }

    public void add(Notice notice) {
        if (notice.getStatus() == null) {
            notice.setStatus(0);
        }
        if (notice.getViewCount() == null) {
            notice.setViewCount(0);
        }
        if (notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        noticeMapper.insert(notice);
        clearCache();
    }

    public void update(Notice notice) {
        if (notice.getStatus() != null && notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        noticeMapper.updateById(notice);
        clearCache();
    }

    public void delete(Long id) {
        noticeMapper.deleteById(id);
        clearCache();
    }

    private void clearCache() {
        stringRedisTemplate.delete(NOTICE_PUBLIC_CACHE_KEY);
    }
}
