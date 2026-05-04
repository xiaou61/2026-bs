package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.entity.TeamSpace;
import com.promptops.mapper.TeamSpaceMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class TeamSpaceService extends ServiceImpl<TeamSpaceMapper, TeamSpace> {

    public Page<TeamSpace> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        LambdaQueryWrapper<TeamSpace> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), w -> w.like(TeamSpace::getName, keyword).or().like(TeamSpace::getOwnerName, keyword));
        wrapper.eq(status != null, TeamSpace::getStatus, status);
        wrapper.orderByDesc(TeamSpace::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(TeamSpace entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setMemberCount(entity.getMemberCount() == null ? 0 : entity.getMemberCount());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }
}
