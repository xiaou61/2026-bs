package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.Document;
import com.xiaou.collabboard.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DocumentService extends ServiceImpl<DocumentMapper, Document> {

    @Autowired
    private UserService userService;

    @Autowired
    private RecentVisitService recentVisitService;

    public Document createDocument(Long userId, String title, String docType, Long folderId, Long teamId) {
        Document document = new Document();
        document.setUserId(userId);
        document.setTeamId(teamId != null ? teamId : 0L);
        document.setFolderId(folderId != null ? folderId : 0L);
        document.setTitle(title);
        document.setDocType(docType);
        document.setContent("{}");
        document.setViewCount(0);
        document.setEditCount(0);
        document.setCollabCount(0);
        document.setIsPublic(0);
        document.setIsTemplate(0);
        document.setIsStarred(0);
        document.setStatus(1);
        document.setCreateTime(LocalDateTime.now());
        document.setUpdateTime(LocalDateTime.now());

        baseMapper.insert(document);
        userService.incrementDocCount(userId);

        return document;
    }

    public Document getDocumentById(Long documentId, Long userId) {
        Document document = baseMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }

        document.setViewCount(document.getViewCount() + 1);
        baseMapper.updateById(document);

        recentVisitService.addVisit(userId, documentId);

        return document;
    }

    public void updateDocument(Long documentId, String title, String content, String description, String tags) {
        Document document = baseMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }

        if (title != null) {
            document.setTitle(title);
        }
        if (content != null) {
            document.setContent(content);
            document.setEditCount(document.getEditCount() + 1);
        }
        if (description != null) {
            document.setDescription(description);
        }
        if (tags != null) {
            document.setTags(tags);
        }
        document.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(document);
    }

    public void deleteDocument(Long documentId, Long userId) {
        Document document = baseMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }

        if (!document.getUserId().equals(userId)) {
            throw new RuntimeException("无权限删除此文档");
        }

        document.setStatus(2);
        document.setDeleteTime(LocalDateTime.now());
        baseMapper.updateById(document);

        userService.decrementDocCount(userId);
    }

    public void restoreDocument(Long documentId, Long userId) {
        Document document = baseMapper.selectById(documentId);
        if (document == null || !document.getUserId().equals(userId)) {
            throw new RuntimeException("无权限恢复此文档");
        }

        document.setStatus(1);
        document.setDeleteTime(null);
        baseMapper.updateById(document);

        userService.incrementDocCount(userId);
    }

    public void permanentDeleteDocument(Long documentId, Long userId) {
        Document document = baseMapper.selectById(documentId);
        if (document == null || !document.getUserId().equals(userId)) {
            throw new RuntimeException("无权限删除此文档");
        }

        baseMapper.deleteById(documentId);
    }

    public IPage<Document> getDocumentList(Long userId, Long teamId, Long folderId, Integer status, Integer pageNum, Integer pageSize) {
        Page<Document> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();

        if (teamId != null && teamId > 0) {
            wrapper.eq(Document::getTeamId, teamId);
        } else {
            wrapper.eq(Document::getUserId, userId)
                    .eq(Document::getTeamId, 0);
        }

        if (folderId != null) {
            wrapper.eq(Document::getFolderId, folderId);
        }

        if (status != null) {
            wrapper.eq(Document::getStatus, status);
        }

        wrapper.orderByDesc(Document::getUpdateTime);
        return baseMapper.selectPage(page, wrapper);
    }

    public void starDocument(Long documentId) {
        Document document = baseMapper.selectById(documentId);
        if (document != null) {
            document.setIsStarred(document.getIsStarred() == 1 ? 0 : 1);
            baseMapper.updateById(document);
        }
    }

    public String generateShareLink(Long documentId, String password, LocalDateTime expireTime) {
        Document document = baseMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }

        String shareLink = UUID.randomUUID().toString().substring(0, 8);
        document.setShareLink(shareLink);
        document.setSharePassword(password);
        document.setShareExpireTime(expireTime);
        baseMapper.updateById(document);

        return shareLink;
    }

    public IPage<Document> searchDocuments(Long userId, String keyword, String docType, Integer pageNum, Integer pageSize) {
        Page<Document> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        
        wrapper.eq(Document::getUserId, userId)
                .eq(Document::getStatus, 1);

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(Document::getTitle, keyword)
                    .or().like(Document::getDescription, keyword)
                    .or().like(Document::getTags, keyword));
        }

        if (docType != null) {
            wrapper.eq(Document::getDocType, docType);
        }

        wrapper.orderByDesc(Document::getUpdateTime);
        return baseMapper.selectPage(page, wrapper);
    }
}

