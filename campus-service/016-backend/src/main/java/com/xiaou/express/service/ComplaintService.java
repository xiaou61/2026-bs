package com.xiaou.express.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.BusinessException;
import com.xiaou.express.dto.ComplaintRequest;
import com.xiaou.express.entity.Complaint;
import com.xiaou.express.entity.Orders;
import com.xiaou.express.entity.User;
import com.xiaou.express.mapper.ComplaintMapper;
import com.xiaou.express.mapper.OrdersMapper;
import com.xiaou.express.mapper.UserMapper;
import com.xiaou.express.vo.ComplaintVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintMapper complaintMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserMapper userMapper;

    public void createComplaint(Long userId, ComplaintRequest request) {
        Orders order = ordersMapper.selectById(request.getOrderId());
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        Long respondentId;
        if (order.getPublisherId().equals(userId)) {
            if (order.getTakerId() == null) {
                throw new BusinessException("当前订单暂无可投诉对象");
            }
            respondentId = order.getTakerId();
        } else if (order.getTakerId() != null && order.getTakerId().equals(userId)) {
            respondentId = order.getPublisherId();
        } else {
            throw new BusinessException("无权投诉该订单");
        }

        Complaint complaint = new Complaint();
        complaint.setOrderId(request.getOrderId());
        complaint.setComplainantId(userId);
        complaint.setRespondentId(respondentId);
        complaint.setType(request.getType());
        complaint.setReason(request.getReason());
        complaint.setEvidence(request.getEvidence());
        complaint.setStatus(0);
        complaintMapper.insert(complaint);
    }

    public Page<ComplaintVO> getMyComplaints(Long userId, int pageNum, int pageSize) {
        Page<Complaint> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Complaint> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Complaint::getComplainantId, userId)
                .orderByDesc(Complaint::getCreateTime);
        Page<Complaint> complaintPage = complaintMapper.selectPage(page, wrapper);

        Page<ComplaintVO> voPage = new Page<>();
        voPage.setCurrent(complaintPage.getCurrent());
        voPage.setSize(complaintPage.getSize());
        voPage.setTotal(complaintPage.getTotal());
        voPage.setRecords(complaintPage.getRecords().stream().map(this::convertToVO).toList());
        return voPage;
    }

    private ComplaintVO convertToVO(Complaint complaint) {
        ComplaintVO vo = new ComplaintVO();
        BeanUtils.copyProperties(complaint, vo);

        Orders order = ordersMapper.selectById(complaint.getOrderId());
        if (order != null) {
            vo.setOrderNo(order.getOrderNo());
        }

        User complainant = userMapper.selectById(complaint.getComplainantId());
        if (complainant != null) {
            vo.setComplainantName(complainant.getRealName());
        }

        User respondent = userMapper.selectById(complaint.getRespondentId());
        if (respondent != null) {
            vo.setRespondentName(respondent.getRealName());
        }

        return vo;
    }
}

