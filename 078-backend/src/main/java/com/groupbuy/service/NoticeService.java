package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.groupbuy.entity.Notice;
import com.groupbuy.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public Page<Notice> page(Integer pageNum, Integer pageSize, Integer type, Integer status) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (type != null) {
            wrapper.eq("type", type);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return noticeMapper.selectPage(page, wrapper);
    }

    public Notice detail(Long id) {
        return noticeMapper.selectById(id);
    }

    public void add(Notice notice) {
        noticeMapper.insert(notice);
    }

    public void update(Notice notice) {
        noticeMapper.updateById(notice);
    }

    public void delete(Long id) {
        noticeMapper.deleteById(id);
    }

    public List<Notice> front() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).orderByDesc("create_time").last("LIMIT 10");
        return noticeMapper.selectList(wrapper);
    }
}
