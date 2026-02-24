package com.harbin.tourism.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.entity.TravelNote;
import com.harbin.tourism.mapper.TravelNoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private TravelNoteMapper noteMapper;

    public Page<TravelNote> page(Integer pageNum, Integer pageSize, String keyword, String status, String orderBy) {
        LambdaQueryWrapper<TravelNote> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(TravelNote::getTitle, keyword);
        }
        if (StrUtil.isNotBlank(status)) {
            wrapper.eq(TravelNote::getStatus, status);
        } else {
            wrapper.eq(TravelNote::getStatus, "published");
        }
        if ("likes".equals(orderBy)) {
            wrapper.orderByDesc(TravelNote::getLikeCount);
        } else {
            wrapper.orderByDesc(TravelNote::getCreatedAt);
        }
        return noteMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Page<TravelNote> userNotes(Long userId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<TravelNote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TravelNote::getUserId, userId);
        wrapper.orderByDesc(TravelNote::getCreatedAt);
        return noteMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public TravelNote getById(Long id) {
        TravelNote note = noteMapper.selectById(id);
        if (note != null && "published".equals(note.getStatus())) {
            note.setViewCount(note.getViewCount() + 1);
            noteMapper.updateById(note);
        }
        return note;
    }

    public void save(TravelNote note) {
        note.setLikeCount(0);
        note.setViewCount(0);
        note.setStatus("pending");
        noteMapper.insert(note);
    }

    public void update(TravelNote note) {
        noteMapper.updateById(note);
    }

    public void delete(Long id) {
        noteMapper.deleteById(id);
    }

    public void like(Long id) {
        TravelNote note = noteMapper.selectById(id);
        note.setLikeCount(note.getLikeCount() + 1);
        noteMapper.updateById(note);
    }

    public void audit(Long id, String status) {
        TravelNote note = new TravelNote();
        note.setId(id);
        note.setStatus(status);
        noteMapper.updateById(note);
    }

    public long count() {
        return noteMapper.selectCount(new LambdaQueryWrapper<TravelNote>()
                .eq(TravelNote::getStatus, "published"));
    }

    public long pendingCount() {
        return noteMapper.selectCount(new LambdaQueryWrapper<TravelNote>()
                .eq(TravelNote::getStatus, "pending"));
    }
}
