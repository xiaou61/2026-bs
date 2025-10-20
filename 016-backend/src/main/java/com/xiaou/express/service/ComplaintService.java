package com.xiaou.express.service;

import com.xiaou.express.common.BusinessException;
import com.xiaou.express.dto.ComplaintRequest;
import com.xiaou.express.entity.Complaint;
import com.xiaou.express.entity.Orders;
import com.xiaou.express.mapper.ComplaintMapper;
import com.xiaou.express.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintMapper complaintMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    public void createComplaint(Long userId, ComplaintRequest request) {
        Orders order = ordersMapper.selectById(request.getOrderId());
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        Long respondentId;
        if (order.getPublisherId().equals(userId)) {
            respondentId = order.getTakerId();
        } else if (order.getTakerId().equals(userId)) {
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
}

