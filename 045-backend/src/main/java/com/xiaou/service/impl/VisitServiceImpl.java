package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Visit;
import com.xiaou.mapper.VisitMapper;
import com.xiaou.service.VisitService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitService {

    @Override
    public void apply(Visit visit, Long userId) {
        visit.setApplyUserId(userId);
        visit.setStatus(0);
        save(visit);
    }

    @Override
    public void approve(Long id, Integer status, String remark, Long approveUserId) {
        Visit visit = getById(id);
        if (visit == null) {
            throw new BusinessException("探访记录不存在");
        }
        if (visit.getStatus() != 0) {
            throw new BusinessException("只能审批待审核的申请");
        }
        visit.setStatus(status);
        visit.setApproveRemark(remark);
        visit.setApproveUserId(approveUserId);
        visit.setApproveTime(LocalDateTime.now());
        updateById(visit);
    }

    @Override
    public IPage<Visit> pageList(Long elderId, Integer current, Integer size, Integer status) {
        LambdaQueryWrapper<Visit> wrapper = new LambdaQueryWrapper<>();
        if (elderId != null) {
            wrapper.eq(Visit::getElderId, elderId);
        }
        if (status != null) {
            wrapper.eq(Visit::getStatus, status);
        }
        wrapper.orderByDesc(Visit::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public IPage<Visit> pageByUser(Long userId, Integer current, Integer size) {
        LambdaQueryWrapper<Visit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Visit::getApplyUserId, userId);
        wrapper.orderByDesc(Visit::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }
}
