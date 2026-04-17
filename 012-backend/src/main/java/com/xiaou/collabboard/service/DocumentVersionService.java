package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.Document;
import com.xiaou.collabboard.entity.DocumentVersion;
import com.xiaou.collabboard.mapper.DocumentVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentVersionService extends ServiceImpl<DocumentVersionMapper, DocumentVersion> {

    @Autowired
    private DocumentService documentService;

    public DocumentVersion createVersion(Long documentId, Long userId, String versionName, String changeLog) {
        Document document = documentService.getEditableDocument(documentId, userId);
        DocumentVersion latest = this.lambdaQuery()
                .eq(DocumentVersion::getDocumentId, documentId)
                .orderByDesc(DocumentVersion::getVersionNumber)
                .last("limit 1")
                .one();

        int nextVersion = latest == null ? 1 : latest.getVersionNumber() + 1;

        DocumentVersion version = new DocumentVersion();
        version.setDocumentId(documentId);
        version.setVersionNumber(nextVersion);
        version.setContent(document.getContent());
        version.setVersionName(StringUtils.hasText(versionName) ? versionName.trim() : "版本 v" + nextVersion);
        version.setChangeLog(changeLog);
        version.setFileSize(document.getContent() == null ? 0L : (long) document.getContent().getBytes(StandardCharsets.UTF_8).length);
        version.setCreateUserId(userId);
        version.setCreateTime(LocalDateTime.now());
        this.save(version);
        return version;
    }

    public List<DocumentVersion> listVersions(Long documentId, Long userId) {
        documentService.getReadableDocument(documentId, userId);
        return this.lambdaQuery()
                .eq(DocumentVersion::getDocumentId, documentId)
                .orderByDesc(DocumentVersion::getVersionNumber)
                .list();
    }

    public Document restoreVersion(Long documentId, Long versionId, Long userId) {
        DocumentVersion version = this.getById(versionId);
        if (version == null || !version.getDocumentId().equals(documentId)) {
            throw new RuntimeException("版本不存在");
        }

        documentService.updateDocument(documentId, userId, null, version.getContent(), null, null);
        return documentService.getReadableDocument(documentId, userId);
    }
}
