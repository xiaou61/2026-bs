package com.milk.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.entity.Complaint;
import com.milk.mapper.ComplaintMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {

    @Resource
    private ComplaintMapper complaintMapper;

    public List<Complaint> listByUserId(Long userId) {
        return complaintMapper.selectList(new QueryWrapper<Complaint>().eq("user_id", userId).orderByDesc("create_time"));
    }

    public Page<Complaint> page(Integer pageNum, Integer pageSize, String type, Integer status) {
        QueryWrapper<Complaint> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(type)) {
            wrapper.eq("type", type);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return complaintMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(Complaint complaint) {
        complaint.setStatus(0);
        complaintMapper.insert(complaint);
    }

    public void reply(Long id, String reply) {
        Complaint complaint = complaintMapper.selectById(id);
        if (complaint != null) {
            complaint.setReply(reply);
            complaint.setStatus(1);
            complaint.setReplyTime(LocalDateTime.now());
            complaintMapper.updateById(complaint);
        }
    }
}
