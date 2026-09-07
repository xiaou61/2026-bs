package com.xiaou.campusvideo.controller;

import com.xiaou.campusvideo.entity.VideoDraft;
import com.xiaou.campusvideo.service.VideoDraftService;
import com.xiaou.campusvideo.util.Result;
import com.xiaou.campusvideo.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/draft")
public class DraftController {
    
    @Autowired
    private VideoDraftService videoDraftService;
    
    @PostMapping("/save")
    public Result<?> saveDraft(@RequestBody VideoDraft draft) {
        draft.setUserId(UserHolder.getUserId());
        videoDraftService.saveDraft(draft);
        return Result.success("保存成功");
    }
    
    @GetMapping("/list")
    public Result<List<VideoDraft>> getDraftList() {
        Long userId = UserHolder.getUserId();
        List<VideoDraft> drafts = videoDraftService.getDraftList(userId);
        return Result.success(drafts);
    }
    
    @GetMapping("/{id}")
    public Result<VideoDraft> getDraftDetail(@PathVariable Long id) {
        VideoDraft draft = videoDraftService.getById(id);
        if (draft == null) {
            return Result.error("草稿不存在");
        }
        
        if (!draft.getUserId().equals(UserHolder.getUserId())) {
            return Result.error("无权限访问");
        }
        
        return Result.success(draft);
    }
    
    @DeleteMapping("/{id}")
    public Result<?> deleteDraft(@PathVariable Long id) {
        VideoDraft draft = videoDraftService.getById(id);
        if (!draft.getUserId().equals(UserHolder.getUserId())) {
            return Result.error("无权限删除");
        }
        
        videoDraftService.removeById(id);
        return Result.success("删除成功");
    }
}

