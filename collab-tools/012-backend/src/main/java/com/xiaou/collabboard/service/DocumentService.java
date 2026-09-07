package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.Collaboration;
import com.xiaou.collabboard.entity.Document;
import com.xiaou.collabboard.entity.RecentVisit;
import com.xiaou.collabboard.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService extends ServiceImpl<DocumentMapper, Document> {

    @Autowired
    private UserService userService;

    @Autowired
    private RecentVisitService recentVisitService;

    @Autowired
    private CollaborationService collaborationService;

    public Document createDocument(Long userId, String title, String docType, Long folderId, Long teamId) {
        Document document = new Document();
        document.setUserId(userId);
        document.setTeamId(teamId != null ? teamId : 0L);
        document.setFolderId(folderId != null ? folderId : 0L);
        document.setTitle(title);
        document.setDocType(docType);
        document.setContent(buildInitialContent(docType));
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
        ensureOwnerCollaboration(document);
        refreshCollabCount(document.getId());

        return document;
    }

    public Document getDocumentById(Long documentId, Long userId) {
        Document document = getReadableDocument(documentId, userId);

        document.setViewCount(document.getViewCount() + 1);
        baseMapper.updateById(document);

        recentVisitService.addVisit(userId, documentId);

        return document;
    }

    public void updateDocument(Long documentId, Long userId, String title, String content, String description, String tags) {
        Document document = getEditableDocument(documentId, userId);

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
        collaborationService.updateLastEditTime(documentId, userId);
    }

    public void deleteDocument(Long documentId, Long userId) {
        Document document = getOwnedDocument(documentId, userId);

        document.setStatus(2);
        document.setDeleteTime(LocalDateTime.now());
        baseMapper.updateById(document);

        userService.decrementDocCount(userId);
    }

    public void restoreDocument(Long documentId, Long userId) {
        Document document = getOwnedDocument(documentId, userId);

        document.setStatus(1);
        document.setDeleteTime(null);
        baseMapper.updateById(document);

        userService.incrementDocCount(userId);
    }

    public void permanentDeleteDocument(Long documentId, Long userId) {
        Document document = getOwnedDocument(documentId, userId);

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

    public IPage<Document> getRecentDocuments(Long userId, Integer pageNum, Integer pageSize) {
        List<RecentVisit> visits = recentVisitService.lambdaQuery()
                .eq(RecentVisit::getUserId, userId)
                .orderByDesc(RecentVisit::getVisitTime)
                .list();

        Page<Document> page = new Page<>(pageNum, pageSize);
        if (visits.isEmpty()) {
            page.setRecords(new ArrayList<>());
            page.setTotal(0);
            return page;
        }

        List<Long> orderedIds = visits.stream()
                .map(RecentVisit::getDocumentId)
                .collect(Collectors.toList());
        Map<Long, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < orderedIds.size(); i++) {
            orderMap.putIfAbsent(orderedIds.get(i), i);
        }

        List<Document> documents = this.lambdaQuery()
                .in(Document::getId, orderedIds)
                .eq(Document::getStatus, 1)
                .orderByDesc(Document::getUpdateTime)
                .list();

        documents.sort(Comparator.comparingInt(document -> orderMap.getOrDefault(document.getId(), Integer.MAX_VALUE)));

        long total = documents.size();
        int fromIndex = Math.max((pageNum - 1) * pageSize, 0);
        if (fromIndex >= documents.size()) {
            page.setRecords(new ArrayList<>());
            page.setTotal(total);
            return page;
        }

        int toIndex = Math.min(fromIndex + pageSize, documents.size());
        page.setRecords(new ArrayList<>(documents.subList(fromIndex, toIndex)));
        page.setTotal(total);
        return page;
    }

    public IPage<Document> getStarredDocuments(Long userId, Integer pageNum, Integer pageSize) {
        Page<Document> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Document::getUserId, userId)
                .eq(Document::getTeamId, 0)
                .eq(Document::getStatus, 1)
                .eq(Document::getIsStarred, 1)
                .orderByDesc(Document::getUpdateTime);
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

    public Document getReadableDocument(Long documentId, Long userId) {
        Document document = baseMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }
        if (!hasReadPermission(document, userId)) {
            throw new RuntimeException("无权限访问此文档");
        }
        return document;
    }

    public Document getEditableDocument(Long documentId, Long userId) {
        Document document = baseMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }
        if (!hasEditPermission(document, userId)) {
            throw new RuntimeException("无权限编辑此文档");
        }
        return document;
    }

    public Document getOwnedDocument(Long documentId, Long userId) {
        Document document = baseMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }
        if (!document.getUserId().equals(userId)) {
            throw new RuntimeException("无权限操作此文档");
        }
        return document;
    }

    public String createShare(Long documentId, Long userId, String password, Integer expireHours) {
        getOwnedDocument(documentId, userId);
        LocalDateTime expireTime = expireHours != null && expireHours > 0
                ? LocalDateTime.now().plusHours(expireHours)
                : null;
        String normalizedPassword = StringUtils.hasText(password) ? password.trim() : null;
        return generateShareLink(documentId, normalizedPassword, expireTime);
    }

    public void cancelShare(Long documentId, Long userId) {
        Document document = getOwnedDocument(documentId, userId);
        document.setShareLink(null);
        document.setSharePassword(null);
        document.setShareExpireTime(null);
        baseMapper.updateById(document);
    }

    public Document getSharedDocumentInfo(String shareLink) {
        Document document = this.lambdaQuery()
                .eq(Document::getShareLink, shareLink)
                .eq(Document::getStatus, 1)
                .one();
        if (document == null) {
            throw new RuntimeException("分享链接不存在");
        }
        validateShareStatus(document);
        return document;
    }

    public Document accessSharedDocument(String shareLink, String password) {
        Document document = getSharedDocumentInfo(shareLink);
        if (StringUtils.hasText(document.getSharePassword())
                && !document.getSharePassword().equals(password)) {
            throw new RuntimeException("分享密码错误");
        }

        document.setViewCount(document.getViewCount() + 1);
        baseMapper.updateById(document);
        return document;
    }

    public void refreshCollabCount(Long documentId) {
        Document document = baseMapper.selectById(documentId);
        if (document == null) {
            return;
        }

        int collabCount = Math.toIntExact(collaborationService.lambdaQuery()
                .eq(Collaboration::getDocumentId, documentId)
                .count());
        document.setCollabCount(collabCount);
        document.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(document);
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

    private void ensureOwnerCollaboration(Document document) {
        if (document == null || document.getId() == null) {
            return;
        }
        boolean exists = collaborationService.lambdaQuery()
                .eq(Collaboration::getDocumentId, document.getId())
                .eq(Collaboration::getUserId, document.getUserId())
                .count() > 0;
        if (exists) {
            return;
        }

        Collaboration ownerCollaboration = new Collaboration();
        ownerCollaboration.setDocumentId(document.getId());
        ownerCollaboration.setUserId(document.getUserId());
        ownerCollaboration.setPermission("OWNER");
        ownerCollaboration.setInviteUserId(document.getUserId());
        ownerCollaboration.setIsActive(1);
        ownerCollaboration.setLastEditTime(LocalDateTime.now());
        ownerCollaboration.setCreateTime(LocalDateTime.now());
        ownerCollaboration.setUpdateTime(LocalDateTime.now());
        collaborationService.save(ownerCollaboration);
    }

    private boolean hasReadPermission(Document document, Long userId) {
        if (document.getStatus() != null && document.getStatus() == 2) {
            return userId != null && document.getUserId().equals(userId);
        }
        if (userId != null && document.getUserId().equals(userId)) {
            return true;
        }
        if (document.getIsPublic() != null && document.getIsPublic() == 1) {
            return true;
        }
        return userId != null && collaborationService.hasPermission(document.getId(), userId, "VIEW");
    }

    private boolean hasEditPermission(Document document, Long userId) {
        if (userId == null) {
            return false;
        }
        if (document.getUserId().equals(userId)) {
            return true;
        }
        if (document.getStatus() != null && document.getStatus() == 2) {
            return false;
        }
        return collaborationService.hasPermission(document.getId(), userId, "EDIT");
    }

    private void validateShareStatus(Document document) {
        if (!StringUtils.hasText(document.getShareLink())) {
            throw new RuntimeException("分享链接不存在");
        }
        if (document.getShareExpireTime() != null
                && document.getShareExpireTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("分享链接已过期");
        }
    }

    private String buildInitialContent(String docType) {
        if ("NOTE".equalsIgnoreCase(docType)) {
            return """
                    # 新建笔记

                    在这里开始记录你的灵感、会议纪要或任务清单。

                    ## 今日待办
                    - [ ] 补充关键信息
                    - [ ] 完成内容整理
                    """;
        }
        if ("BOARD".equalsIgnoreCase(docType)) {
            return "{\"version\":1,\"type\":\"BOARD\",\"background\":\"#ffffff\",\"strokes\":[]}";
        }
        if ("MINDMAP".equalsIgnoreCase(docType)) {
            return "{\"version\":1,\"type\":\"MINDMAP\",\"root\":{\"id\":\"root\",\"text\":\"中心主题\",\"children\":[]}}";
        }
        return "";
    }
}

