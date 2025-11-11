package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.Note;
import com.xiaou.resource.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoteService extends ServiceImpl<NoteMapper, Note> {

    public boolean createNote(Note note, Long userId) {
        note.setUserId(userId);
        note.setViewCount(0);
        note.setLikeCount(0);
        note.setCreateTime(LocalDateTime.now());
        note.setUpdateTime(LocalDateTime.now());
        return this.save(note);
    }

    public boolean updateNote(Note note, Long userId) {
        Note existingNote = this.getById(note.getId());
        if (existingNote == null || !existingNote.getUserId().equals(userId)) {
            return false;
        }
        note.setUpdateTime(LocalDateTime.now());
        return this.updateById(note);
    }

    public IPage<Note> getNoteList(Integer page, Integer size, String category, String keyword, Integer isPublic) {
        Page<Note> pageParam = new Page<>(page, size);
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        if (isPublic != null) {
            wrapper.eq("is_public", isPublic);
        }
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like("title", keyword).or().like("content", keyword);
        }
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }

    public Note getNoteDetail(Long id) {
        Note note = this.getById(id);
        if (note != null) {
            note.setViewCount(note.getViewCount() + 1);
            this.updateById(note);
        }
        return note;
    }

    public IPage<Note> getMyNotes(Long userId, Integer page, Integer size) {
        Page<Note> pageParam = new Page<>(page, size);
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("update_time");
        return this.page(pageParam, wrapper);
    }

    public boolean deleteNote(Long id, Long userId) {
        Note note = this.getById(id);
        if (note == null || !note.getUserId().equals(userId)) {
            return false;
        }
        return this.removeById(id);
    }

    public boolean likeNote(Long id) {
        Note note = this.getById(id);
        if (note != null) {
            note.setLikeCount(note.getLikeCount() + 1);
            note.setUpdateTime(LocalDateTime.now());
            return this.updateById(note);
        }
        return false;
    }
}

