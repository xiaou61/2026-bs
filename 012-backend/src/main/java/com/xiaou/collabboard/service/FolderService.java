package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.Folder;
import com.xiaou.collabboard.mapper.FolderMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FolderService extends ServiceImpl<FolderMapper, Folder> {

    public Folder createFolder(Long userId, Long teamId, Long parentId, String folderName) {
        Folder folder = new Folder();
        folder.setUserId(userId);
        folder.setTeamId(teamId != null ? teamId : 0L);
        folder.setParentId(parentId != null ? parentId : 0L);
        folder.setFolderName(folderName);
        folder.setDocCount(0);
        folder.setSortOrder(0);
        folder.setCreateTime(LocalDateTime.now());
        folder.setUpdateTime(LocalDateTime.now());

        baseMapper.insert(folder);
        return folder;
    }

    public List<Folder> getFolderTree(Long userId, Long teamId) {
        LambdaQueryWrapper<Folder> wrapper = new LambdaQueryWrapper<>();
        
        if (teamId != null && teamId > 0) {
            wrapper.eq(Folder::getTeamId, teamId);
        } else {
            wrapper.eq(Folder::getUserId, userId)
                    .eq(Folder::getTeamId, 0);
        }

        wrapper.orderByAsc(Folder::getSortOrder)
                .orderByDesc(Folder::getCreateTime);

        return baseMapper.selectList(wrapper);
    }

    public void updateFolder(Long folderId, String folderName, String description) {
        Folder folder = baseMapper.selectById(folderId);
        if (folder == null) {
            throw new RuntimeException("文件夹不存在");
        }

        if (folderName != null) {
            folder.setFolderName(folderName);
        }
        if (description != null) {
            folder.setDescription(description);
        }
        folder.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(folder);
    }

    public void deleteFolder(Long folderId, Long userId) {
        Folder folder = baseMapper.selectById(folderId);
        if (folder == null || !folder.getUserId().equals(userId)) {
            throw new RuntimeException("无权限删除此文件夹");
        }

        baseMapper.deleteById(folderId);
    }

    public void incrementDocCount(Long folderId) {
        Folder folder = baseMapper.selectById(folderId);
        if (folder != null) {
            folder.setDocCount(folder.getDocCount() + 1);
            baseMapper.updateById(folder);
        }
    }

    public void decrementDocCount(Long folderId) {
        Folder folder = baseMapper.selectById(folderId);
        if (folder != null && folder.getDocCount() > 0) {
            folder.setDocCount(folder.getDocCount() - 1);
            baseMapper.updateById(folder);
        }
    }
}

