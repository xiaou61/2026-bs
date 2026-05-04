package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.TravelNote;
import com.harbin.tourism.service.NoteService;
import com.harbin.tourism.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    public Result<Page<TravelNote>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "time") String orderBy) {
        return Result.success(noteService.page(pageNum, pageSize, keyword, status, orderBy));
    }

    @GetMapping("/my")
    public Result<Page<TravelNote>> myNotes(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        return Result.success(noteService.userNotes(userId, pageNum, pageSize));
    }

    @GetMapping("/detail/{id}")
    public Result<TravelNote> detail(@PathVariable Long id) {
        return Result.success(noteService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody TravelNote note, HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        note.setUserId(userId);
        noteService.save(note);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody TravelNote note, HttpServletRequest request) {
        noteService.update(note, AuthUtils.currentUserId(request), AuthUtils.isAdmin(request));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        noteService.delete(id, AuthUtils.currentUserId(request), AuthUtils.isAdmin(request));
        return Result.success();
    }

    @PostMapping("/like/{id}")
    public Result<Void> like(@PathVariable Long id) {
        noteService.like(id);
        return Result.success();
    }

    @PutMapping("/audit/{id}")
    public Result<Void> audit(@PathVariable Long id, @RequestBody Map<String, String> params, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        noteService.audit(id, params.get("status"));
        return Result.success();
    }
}
