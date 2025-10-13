package com.xiaou.collabboard.controller;

import com.xiaou.collabboard.entity.Folder;
import com.xiaou.collabboard.service.FolderService;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/folder")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @PostMapping("/create")
    public Result<Folder> createFolder(@RequestBody Map<String, Object> params) {
        Long userId = UserHolder.get();
        Long teamId = params.containsKey("teamId") ? Long.parseLong(params.get("teamId").toString()) : null;
        Long parentId = params.containsKey("parentId") ? Long.parseLong(params.get("parentId").toString()) : null;
        String folderName = (String) params.get("folderName");

        Folder folder = folderService.createFolder(userId, teamId, parentId, folderName);
        return Result.success(folder);
    }

    @GetMapping("/{id}")
    public Result<Folder> getFolder(@PathVariable Long id) {
        Folder folder = folderService.getById(id);
        return Result.success(folder);
    }

    @PutMapping("/{id}")
    public Result<Void> updateFolder(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String folderName = params.get("folderName");
        String description = params.get("description");

        folderService.updateFolder(id, folderName, description);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteFolder(@PathVariable Long id) {
        Long userId = UserHolder.get();
        folderService.deleteFolder(id, userId);
        return Result.success();
    }

    @GetMapping("/tree")
    public Result<List<Folder>> getFolderTree(@RequestParam(required = false) Long teamId) {
        Long userId = UserHolder.get();
        List<Folder> folders = folderService.getFolderTree(userId, teamId);
        return Result.success(folders);
    }

    @PostMapping("/{id}/move")
    public Result<Void> moveFolder(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Long parentId = Long.parseLong(params.get("parentId").toString());
        Folder folder = folderService.getById(id);
        folder.setParentId(parentId);
        folderService.updateById(folder);
        return Result.success();
    }
}

