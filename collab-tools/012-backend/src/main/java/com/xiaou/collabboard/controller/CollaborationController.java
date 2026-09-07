package com.xiaou.collabboard.controller;

import com.xiaou.collabboard.entity.Collaboration;
import com.xiaou.collabboard.entity.Document;
import com.xiaou.collabboard.entity.User;
import com.xiaou.collabboard.service.CollaborationService;
import com.xiaou.collabboard.service.DocumentService;
import com.xiaou.collabboard.service.UserService;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/document/{documentId}/collaborators")
public class CollaborationController {

    @Autowired
    private CollaborationService collaborationService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Result<List<Map<String, Object>>> listCollaborators(@PathVariable Long documentId) {
        Long userId = UserHolder.get();
        Document document = documentService.getReadableDocument(documentId, userId);

        Map<Long, User> userMap = new HashMap<>();
        List<Map<String, Object>> result = new ArrayList<>();

        User owner = userService.getById(document.getUserId());
        if (owner != null) {
            owner.setPassword(null);
            userMap.put(owner.getId(), owner);
            result.add(buildCollaboratorRow(null, owner, "OWNER", null, document.getCreateTime()));
        }

        List<Collaboration> collaborators = collaborationService.getCollaborators(documentId);
        for (Collaboration collaboration : collaborators) {
            if (Objects.equals(collaboration.getUserId(), document.getUserId())) {
                continue;
            }

            User collaborator = userMap.computeIfAbsent(collaboration.getUserId(), userService::getById);
            if (collaborator == null) {
                continue;
            }
            collaborator.setPassword(null);
            result.add(buildCollaboratorRow(
                    collaboration.getId(),
                    collaborator,
                    collaboration.getPermission(),
                    collaboration.getInviteUserId(),
                    collaboration.getLastEditTime()
            ));
        }

        return Result.success(result);
    }

    @PostMapping
    public Result<Collaboration> inviteCollaborator(@PathVariable Long documentId,
                                                    @RequestBody Map<String, Object> params) {
        Long operatorId = UserHolder.get();
        Document document = documentService.getOwnedDocument(documentId, operatorId);

        Long targetUserId = params.containsKey("userId") && params.get("userId") != null
                ? Long.parseLong(params.get("userId").toString())
                : null;
        if (targetUserId == null && StringUtils.hasText((String) params.get("username"))) {
            User targetUser = userService.lambdaQuery()
                    .eq(User::getUsername, params.get("username").toString().trim())
                    .one();
            if (targetUser != null) {
                targetUserId = targetUser.getId();
            }
        }
        if (targetUserId == null) {
            throw new RuntimeException("协作者不存在");
        }
        if (Objects.equals(targetUserId, document.getUserId())) {
            throw new RuntimeException("创建者已拥有全部权限，无需重复添加");
        }

        String permission = params.get("permission") == null ? "EDIT" : params.get("permission").toString();
        Collaboration collaboration = collaborationService.inviteCollaborator(documentId, targetUserId, permission, operatorId);
        documentService.refreshCollabCount(documentId);
        return Result.success(collaboration);
    }

    @PutMapping("/{collaborationId}")
    public Result<Void> updatePermission(@PathVariable Long documentId,
                                         @PathVariable Long collaborationId,
                                         @RequestBody Map<String, String> params) {
        Long operatorId = UserHolder.get();
        documentService.getOwnedDocument(documentId, operatorId);
        collaborationService.updatePermission(collaborationId, params.get("permission"));
        return Result.success();
    }

    @DeleteMapping("/{collaborationId}")
    public Result<Void> removeCollaborator(@PathVariable Long documentId, @PathVariable Long collaborationId) {
        Long operatorId = UserHolder.get();
        Document document = documentService.getOwnedDocument(documentId, operatorId);
        Collaboration collaboration = collaborationService.getById(collaborationId);
        if (collaboration == null || !Objects.equals(collaboration.getDocumentId(), documentId)) {
            throw new RuntimeException("协作记录不存在");
        }
        if (Objects.equals(collaboration.getUserId(), document.getUserId())) {
            throw new RuntimeException("不能移除文档创建者");
        }

        collaborationService.removeCollaborator(collaborationId, operatorId);
        documentService.refreshCollabCount(documentId);
        return Result.success();
    }

    private Map<String, Object> buildCollaboratorRow(Long collaborationId,
                                                     User user,
                                                     String permission,
                                                     Long inviteUserId,
                                                     Object lastEditTime) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("collaborationId", collaborationId);
        row.put("userId", user.getId());
        row.put("username", user.getUsername());
        row.put("nickname", user.getNickname());
        row.put("avatar", user.getAvatar());
        row.put("permission", permission);
        row.put("inviteUserId", inviteUserId);
        row.put("lastEditTime", lastEditTime);
        return row;
    }
}
