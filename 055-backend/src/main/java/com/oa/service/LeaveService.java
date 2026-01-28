package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.entity.LeaveRequest;
import com.oa.entity.User;
import com.oa.mapper.LeaveRequestMapper;
import com.oa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRequestMapper leaveRequestMapper;
    private final UserMapper userMapper;

    public Page<LeaveRequest> getList(Integer pageNum, Integer pageSize, Integer status) {
        Page<LeaveRequest> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<LeaveRequest> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(LeaveRequest::getStatus, status);
        }
        wrapper.orderByDesc(LeaveRequest::getCreateTime);
        Page<LeaveRequest> result = leaveRequestMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillUserInfo);
        return result;
    }

    public Page<LeaveRequest> getMyList(Long userId, Integer pageNum, Integer pageSize, Integer status) {
        Page<LeaveRequest> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<LeaveRequest> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveRequest::getUserId, userId);
        if (status != null) {
            wrapper.eq(LeaveRequest::getStatus, status);
        }
        wrapper.orderByDesc(LeaveRequest::getCreateTime);
        Page<LeaveRequest> result = leaveRequestMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillUserInfo);
        return result;
    }

    private void fillUserInfo(LeaveRequest leave) {
        User user = userMapper.selectById(leave.getUserId());
        if (user != null) leave.setRealName(user.getRealName());
        if (leave.getApproverId() != null) {
            User approver = userMapper.selectById(leave.getApproverId());
            if (approver != null) leave.setApproverName(approver.getRealName());
        }
    }

    public void add(Long userId, LeaveRequest leave) {
        leave.setUserId(userId);
        leave.setStatus(0);
        leaveRequestMapper.insert(leave);
    }

    public void approve(Long approverId, Long id, Integer status, String remark) {
        LeaveRequest leave = leaveRequestMapper.selectById(id);
        leave.setStatus(status);
        leave.setApproverId(approverId);
        leave.setApproveTime(LocalDateTime.now());
        leave.setApproveRemark(remark);
        leaveRequestMapper.updateById(leave);
    }

    public void delete(Long id) {
        leaveRequestMapper.deleteById(id);
    }

    public long getPendingCount() {
        return leaveRequestMapper.selectCount(new LambdaQueryWrapper<LeaveRequest>().eq(LeaveRequest::getStatus, 0));
    }
}
