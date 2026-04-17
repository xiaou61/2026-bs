package com.xiaou.collabboard.controller;

import com.xiaou.collabboard.entity.Document;
import com.xiaou.collabboard.service.DocumentService;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ShareController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/api/document/{documentId}/share")
    public Result<Map<String, Object>> createShare(@PathVariable Long documentId,
                                                   @RequestBody(required = false) Map<String, Object> params) {
        Long userId = UserHolder.get();
        String password = params == null ? null : (String) params.get("password");
        Integer expireHours = null;
        if (params != null && params.get("expireHours") != null) {
            expireHours = Integer.parseInt(params.get("expireHours").toString());
        }

        String shareLink = documentService.createShare(documentId, userId, password, expireHours);
        Document document = documentService.getOwnedDocument(documentId, userId);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("shareLink", shareLink);
        data.put("shareUrl", "/share/" + shareLink);
        data.put("expireTime", document.getShareExpireTime());
        data.put("hasPassword", document.getSharePassword() != null && !document.getSharePassword().isBlank());
        return Result.success(data);
    }

    @DeleteMapping("/api/document/{documentId}/share")
    public Result<Void> cancelShare(@PathVariable Long documentId) {
        Long userId = UserHolder.get();
        documentService.cancelShare(documentId, userId);
        return Result.success();
    }

    @GetMapping("/api/share/{shareLink}/info")
    public Result<Map<String, Object>> getShareInfo(@PathVariable String shareLink) {
        Document document = documentService.getSharedDocumentInfo(shareLink);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", document.getId());
        data.put("title", document.getTitle());
        data.put("docType", document.getDocType());
        data.put("description", document.getDescription());
        data.put("hasPassword", document.getSharePassword() != null && !document.getSharePassword().isBlank());
        data.put("expireTime", document.getShareExpireTime());
        return Result.success(data);
    }

    @PostMapping("/api/share/{shareLink}/access")
    public Result<Document> accessSharedDocument(@PathVariable String shareLink,
                                                 @RequestBody(required = false) Map<String, String> params) {
        String password = params == null ? null : params.get("password");
        Document document = documentService.accessSharedDocument(shareLink, password);
        document.setSharePassword(null);
        return Result.success(document);
    }
}
