package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.common.BusinessException;
import com.hrm.entity.LeaveRequest;
import com.hrm.mapper.LeaveRequestMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class LeaveRequestService {
    @Resource
    private LeaveRequestMapper leaveRequestMapper;

    public LeaveRequest getById(Long id) {
        return leaveRequestMapper.selectById(id);
    }

    public PageInfo<LeaveRequest> getList(Long employeeId, String employeeName, String type, String status,
                                           Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(leaveRequestMapper.selectList(employeeId, employeeName, type, status));
    }

    public void add(LeaveRequest leaveRequest) {
        leaveRequest.setStatus("pending");
        leaveRequestMapper.insert(leaveRequest);
    }

    public void update(LeaveRequest leaveRequest) {
        LeaveRequest existing = leaveRequestMapper.selectById(leaveRequest.getId());
        if (!"pending".equals(existing.getStatus())) {
            throw new BusinessException("只能修改待审批的请假申请");
        }
        leaveRequestMapper.update(leaveRequest);
    }

    public void delete(Long id) {
        LeaveRequest existing = leaveRequestMapper.selectById(id);
        if (!"pending".equals(existing.getStatus())) {
            throw new BusinessException("只能删除待审批的请假申请");
        }
        leaveRequestMapper.deleteById(id);
    }

    public void approve(Long id, Long approverId, String status) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(id);
        leaveRequest.setStatus(status);
        leaveRequest.setApproverId(approverId);
        leaveRequest.setApproveTime(new Date());
        leaveRequestMapper.update(leaveRequest);
    }

    public int getPendingCount() {
        return leaveRequestMapper.countByStatus("pending");
    }
}
