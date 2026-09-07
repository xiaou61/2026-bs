package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.Collaboration;
import com.xiaou.collabboard.mapper.CollaborationMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CollaborationService extends ServiceImpl<CollaborationMapper, Collaboration> {

    public Collaboration inviteCollaborator(Long documentId, Long userId, String permission, Long inviteUserId) {
        LambdaQueryWrapper<Collaboration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collaboration::getDocumentId, documentId)
                .eq(Collaboration::getUserId, userId);
        
        if (baseMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("该用户已在协作列表中");
        }

        Collaboration collaboration = new Collaboration();
        collaboration.setDocumentId(documentId);
        collaboration.setUserId(userId);
        collaboration.setPermission(permission);
        collaboration.setInviteUserId(inviteUserId);
        collaboration.setIsActive(1);
        collaboration.setCreateTime(LocalDateTime.now());
        collaboration.setUpdateTime(LocalDateTime.now());

        baseMapper.insert(collaboration);
        return collaboration;
    }

    public List<Collaboration> getCollaborators(Long documentId) {
        LambdaQueryWrapper<Collaboration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collaboration::getDocumentId, documentId)
                .orderByDesc(Collaboration::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    public void updatePermission(Long collaborationId, String permission) {
        Collaboration collaboration = baseMapper.selectById(collaborationId);
        if (collaboration == null) {
            throw new RuntimeException("协作记录不存在");
        }

        collaboration.setPermission(permission);
        collaboration.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(collaboration);
    }

    public void removeCollaborator(Long collaborationId, Long operatorId) {
        Collaboration collaboration = baseMapper.selectById(collaborationId);
        if (collaboration == null) {
            throw new RuntimeException("协作记录不存在");
        }

        baseMapper.deleteById(collaborationId);
    }

    public boolean hasPermission(Long documentId, Long userId, String requiredPermission) {
        LambdaQueryWrapper<Collaboration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collaboration::getDocumentId, documentId)
                .eq(Collaboration::getUserId, userId);
        
        Collaboration collaboration = baseMapper.selectOne(wrapper);
        if (collaboration == null) {
            return false;
        }

        if ("OWNER".equals(requiredPermission)) {
            return "OWNER".equals(collaboration.getPermission());
        } else if ("EDIT".equals(requiredPermission)) {
            return "OWNER".equals(collaboration.getPermission()) || "EDIT".equals(collaboration.getPermission());
        } else if ("VIEW".equals(requiredPermission)) {
            return true;
        }

        return false;
    }

    public void updateLastEditTime(Long documentId, Long userId) {
        LambdaQueryWrapper<Collaboration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collaboration::getDocumentId, documentId)
                .eq(Collaboration::getUserId, userId);
        
        Collaboration collaboration = baseMapper.selectOne(wrapper);
        if (collaboration != null) {
            collaboration.setLastEditTime(LocalDateTime.now());
            baseMapper.updateById(collaboration);
        }
    }
}

