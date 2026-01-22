package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Note;
import com.xiaou.mapper.NoteMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteMapper noteMapper;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int current,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(required = false) Long subjectId,
                         @RequestParam(defaultValue = "1") Integer isPublic) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Note::getIsPublic, isPublic);
        if (subjectId != null) wrapper.eq(Note::getSubjectId, subjectId);
        wrapper.orderByDesc(Note::getCreateTime);
        return Result.success(noteMapper.selectPage(new Page<>(current, size), wrapper));
    }

    @GetMapping("/my")
    public Result<?> myNotes(@RequestParam(defaultValue = "1") int current,
                            @RequestParam(defaultValue = "10") int size,
                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(noteMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<Note>().eq(Note::getUserId, userId).orderByDesc(Note::getCreateTime)));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        Note note = noteMapper.selectById(id);
        if (note != null) {
            note.setViewCount(note.getViewCount() + 1);
            noteMapper.updateById(note);
        }
        return Result.success(note);
    }

    @PostMapping
    public Result<?> create(@RequestBody Note note, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        note.setUserId(userId);
        note.setLikeCount(0);
        note.setViewCount(0);
        noteMapper.insert(note);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Note note) {
        note.setId(id);
        noteMapper.updateById(note);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        noteMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/{id}/like")
    public Result<?> like(@PathVariable Long id) {
        Note note = noteMapper.selectById(id);
        if (note != null) {
            note.setLikeCount(note.getLikeCount() + 1);
            noteMapper.updateById(note);
        }
        return Result.success();
    }
}
