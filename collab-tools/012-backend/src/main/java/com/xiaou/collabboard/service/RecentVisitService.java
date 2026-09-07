package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.RecentVisit;
import com.xiaou.collabboard.mapper.RecentVisitMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecentVisitService extends ServiceImpl<RecentVisitMapper, RecentVisit> {

    public void addVisit(Long userId, Long documentId) {
        LambdaQueryWrapper<RecentVisit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RecentVisit::getUserId, userId)
                .eq(RecentVisit::getDocumentId, documentId);
        
        RecentVisit visit = baseMapper.selectOne(wrapper);
        
        if (visit != null) {
            visit.setVisitTime(LocalDateTime.now());
            visit.setUpdateTime(LocalDateTime.now());
            baseMapper.updateById(visit);
        } else {
            visit = new RecentVisit();
            visit.setUserId(userId);
            visit.setDocumentId(documentId);
            visit.setVisitTime(LocalDateTime.now());
            visit.setCreateTime(LocalDateTime.now());
            visit.setUpdateTime(LocalDateTime.now());
            baseMapper.insert(visit);
        }
    }

    public List<RecentVisit> getRecentVisits(Long userId, Integer limit) {
        LambdaQueryWrapper<RecentVisit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RecentVisit::getUserId, userId)
                .orderByDesc(RecentVisit::getVisitTime)
                .last("LIMIT " + limit);
        
        return baseMapper.selectList(wrapper);
    }
}

