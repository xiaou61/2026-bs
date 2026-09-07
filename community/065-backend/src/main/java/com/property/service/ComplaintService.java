package com.property.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.property.common.BusinessException;
import com.property.common.PageResult;
import com.property.entity.Complaint;
import com.property.entity.User;
import com.property.mapper.ComplaintMapper;
import com.property.mapper.UserMapper;
import com.property.vo.ComplaintVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComplaintService {

    @Resource
    private ComplaintMapper complaintMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<ComplaintVO> page(Integer pageNum, Integer pageSize, Long ownerId, Integer status, String orderNo) {
        PageHelper.startPage(pageNum, pageSize);
        List<Complaint> list = complaintMapper.selectPageList(ownerId, status, orderNo);
        PageInfo<Complaint> pageInfo = new PageInfo<>(list);
        PageResult<ComplaintVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public PageResult<ComplaintVO> myPage(Long ownerId, Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        return page(pageNum, pageSize, ownerId, status, orderNo);
    }

    public void save(Complaint complaint, Long ownerId) {
        if (complaint == null || complaint.getTitle() == null || complaint.getTitle().trim().isEmpty()) {
            throw new BusinessException("投诉标题不能为空");
        }
        if (complaint.getContent() == null || complaint.getContent().trim().isEmpty()) {
            throw new BusinessException("投诉内容不能为空");
        }
        complaint.setOrderNo("COM" + cn.hutool.core.util.IdUtil.getSnowflakeNextIdStr());
        complaint.setOwnerId(ownerId);
        complaint.setTitle(complaint.getTitle().trim());
        complaint.setContent(complaint.getContent().trim());
        complaint.setStatus(0);
        complaint.setReply(null);
        complaintMapper.insert(complaint);
    }

    public void reply(Long id, String reply) {
        if (reply == null || reply.trim().isEmpty()) {
            throw new BusinessException("回复内容不能为空");
        }
        Complaint db = complaintMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("投诉不存在");
        }
        if (db.getStatus() != null && db.getStatus() == 1) {
            throw new BusinessException("该投诉已处理");
        }
        db.setReply(reply.trim());
        db.setStatus(1);
        complaintMapper.updateReply(db);
    }

    public void deleteById(Long id, Long userId, String role) {
        Complaint db = complaintMapper.selectById(id);
        if (db == null) {
            return;
        }
        if ("OWNER".equals(role)) {
            if (!db.getOwnerId().equals(userId)) {
                throw new BusinessException("无权限删除");
            }
            if (db.getStatus() != null && db.getStatus() != 0) {
                throw new BusinessException("已处理投诉不可删除");
            }
        }
        complaintMapper.deleteById(id);
    }

    private List<ComplaintVO> convertList(List<Complaint> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> users = userMapper.selectPageList(null, null, null);
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        List<ComplaintVO> result = new ArrayList<>();
        for (Complaint item : list) {
            ComplaintVO vo = new ComplaintVO();
            BeanUtils.copyProperties(item, vo);
            vo.setOwnerName(userMap.getOrDefault(item.getOwnerId(), "未知业主"));
            result.add(vo);
        }
        return result;
    }
}
