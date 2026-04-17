package com.xiaou.collabboard.controller;

import com.xiaou.collabboard.entity.Attachment;
import com.xiaou.collabboard.service.AttachmentService;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Value("${file.upload.attachment-path}")
    private String attachmentPath;

    @GetMapping("/api/document/{documentId}/attachments")
    public Result<List<Attachment>> listAttachments(@PathVariable Long documentId) {
        Long userId = UserHolder.get();
        return Result.success(attachmentService.listAttachments(documentId, userId));
    }

    @PostMapping("/api/document/{documentId}/attachments")
    public Result<Attachment> uploadAttachment(@PathVariable Long documentId,
                                               @RequestParam("file") MultipartFile file) throws Exception {
        Long userId = UserHolder.get();
        return Result.success(attachmentService.uploadAttachment(documentId, userId, file));
    }

    @DeleteMapping("/api/document/{documentId}/attachments/{attachmentId}")
    public Result<Void> deleteAttachment(@PathVariable Long documentId, @PathVariable Long attachmentId) {
        Long userId = UserHolder.get();
        attachmentService.deleteAttachment(attachmentId, userId);
        return Result.success();
    }

    @GetMapping("/api/attachment/{attachmentId}/download")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long attachmentId) throws Exception {
        Long userId = UserHolder.get();
        Attachment attachment = attachmentService.getAttachment(attachmentId, userId);
        Path filePath = Paths.get(attachmentPath, attachment.getFileUrl());
        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists()) {
            throw new RuntimeException("附件文件不存在");
        }

        String encodedFilename = URLEncoder.encode(attachment.getFileName(), StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename)
                .body(resource);
    }
}
