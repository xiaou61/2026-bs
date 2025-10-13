package com.xiaou.collabboard.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.collabboard.entity.Document;
import com.xiaou.collabboard.service.DocumentService;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/create")
    public Result<Document> createDocument(@RequestBody Map<String, Object> params) {
        Long userId = UserHolder.get();
        String title = (String) params.get("title");
        String docType = (String) params.get("docType");
        Long folderId = params.containsKey("folderId") ? Long.parseLong(params.get("folderId").toString()) : null;
        Long teamId = params.containsKey("teamId") ? Long.parseLong(params.get("teamId").toString()) : null;

        Document document = documentService.createDocument(userId, title, docType, folderId, teamId);
        return Result.success(document);
    }

    @GetMapping("/{id}")
    public Result<Document> getDocument(@PathVariable Long id) {
        Long userId = UserHolder.get();
        Document document = documentService.getDocumentById(id, userId);
        return Result.success(document);
    }

    @PutMapping("/{id}")
    public Result<Void> updateDocument(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String title = params.get("title");
        String content = params.get("content");
        String description = params.get("description");
        String tags = params.get("tags");

        documentService.updateDocument(id, title, content, description, tags);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteDocument(@PathVariable Long id) {
        Long userId = UserHolder.get();
        documentService.deleteDocument(id, userId);
        return Result.success();
    }

    @PostMapping("/{id}/restore")
    public Result<Void> restoreDocument(@PathVariable Long id) {
        Long userId = UserHolder.get();
        documentService.restoreDocument(id, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}/permanent")
    public Result<Void> permanentDeleteDocument(@PathVariable Long id) {
        Long userId = UserHolder.get();
        documentService.permanentDeleteDocument(id, userId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<IPage<Document>> getDocumentList(
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) Long folderId,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {

        Long userId = UserHolder.get();
        IPage<Document> page = documentService.getDocumentList(userId, teamId, folderId, status, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/recent")
    public Result<IPage<Document>> getRecentDocuments(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {

        Long userId = UserHolder.get();
        IPage<Document> page = documentService.getDocumentList(userId, null, null, 1, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/starred")
    public Result<IPage<Document>> getStarredDocuments(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {

        Long userId = UserHolder.get();
        IPage<Document> page = documentService.getDocumentList(userId, null, null, 1, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/trash")
    public Result<IPage<Document>> getTrashDocuments(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {

        Long userId = UserHolder.get();
        IPage<Document> page = documentService.getDocumentList(userId, null, null, 2, pageNum, pageSize);
        return Result.success(page);
    }

    @PostMapping("/{id}/star")
    public Result<Void> starDocument(@PathVariable Long id) {
        documentService.starDocument(id);
        return Result.success();
    }

    @PostMapping("/{id}/copy")
    public Result<Document> copyDocument(@PathVariable Long id) {
        Long userId = UserHolder.get();
        Document original = documentService.getById(id);
        
        Document copy = documentService.createDocument(
            userId, 
            original.getTitle() + " (副本)", 
            original.getDocType(), 
            original.getFolderId(), 
            null
        );
        copy.setContent(original.getContent());
        documentService.updateById(copy);

        return Result.success(copy);
    }

    @PostMapping("/{id}/move")
    public Result<Void> moveDocument(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Long folderId = Long.parseLong(params.get("folderId").toString());
        Document document = documentService.getById(id);
        document.setFolderId(folderId);
        documentService.updateById(document);
        return Result.success();
    }

    @GetMapping("/search")
    public Result<IPage<Document>> searchDocuments(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String docType,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {

        Long userId = UserHolder.get();
        IPage<Document> page = documentService.searchDocuments(userId, keyword, docType, pageNum, pageSize);
        return Result.success(page);
    }
}

