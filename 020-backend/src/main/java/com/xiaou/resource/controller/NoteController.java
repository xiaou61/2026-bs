package com.xiaou.resource.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.resource.common.Result;
import com.xiaou.resource.entity.Note;
import com.xiaou.resource.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/create")
    public Result<String> createNote(@RequestAttribute Long userId, @RequestBody Note note) {
        boolean success = noteService.createNote(note, userId);
        return success ? Result.success("创建成功") : Result.error("创建失败");
    }

    @PutMapping("/update")
    public Result<String> updateNote(@RequestAttribute Long userId, @RequestBody Note note) {
        boolean success = noteService.updateNote(note, userId);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }

    @GetMapping("/list")
    public Result<IPage<Note>> getNoteList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer isPublic) {
        IPage<Note> pageData = noteService.getNoteList(page, size, category, keyword, isPublic);
        return Result.success(pageData);
    }

    @GetMapping("/{id}")
    public Result<Note> getNoteDetail(@PathVariable Long id) {
        Note note = noteService.getNoteDetail(id);
        return Result.success(note);
    }

    @GetMapping("/my")
    public Result<IPage<Note>> getMyNotes(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        IPage<Note> pageData = noteService.getMyNotes(userId, page, size);
        return Result.success(pageData);
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteNote(@PathVariable Long id, @RequestAttribute Long userId) {
        boolean success = noteService.deleteNote(id, userId);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }

    @PostMapping("/like/{id}")
    public Result<String> likeNote(@PathVariable Long id) {
        boolean success = noteService.likeNote(id);
        return success ? Result.success("点赞成功") : Result.error("点赞失败");
    }
}

