package com.xiaou.collabboard.controller;

import com.xiaou.collabboard.entity.Document;
import com.xiaou.collabboard.entity.DocumentVersion;
import com.xiaou.collabboard.service.DocumentVersionService;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/document/{documentId}/versions")
public class DocumentVersionController {

    @Autowired
    private DocumentVersionService documentVersionService;

    @GetMapping
    public Result<List<DocumentVersion>> listVersions(@PathVariable Long documentId) {
        Long userId = UserHolder.get();
        return Result.success(documentVersionService.listVersions(documentId, userId));
    }

    @PostMapping
    public Result<DocumentVersion> createVersion(@PathVariable Long documentId,
                                                 @RequestBody(required = false) Map<String, String> params) {
        Long userId = UserHolder.get();
        String versionName = params == null ? null : params.get("versionName");
        String changeLog = params == null ? null : params.get("changeLog");
        return Result.success(documentVersionService.createVersion(documentId, userId, versionName, changeLog));
    }

    @PostMapping("/{versionId}/restore")
    public Result<Document> restoreVersion(@PathVariable Long documentId, @PathVariable Long versionId) {
        Long userId = UserHolder.get();
        return Result.success(documentVersionService.restoreVersion(documentId, versionId, userId));
    }
}
