package com.enterprise.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enterprise.common.BusinessException;
import com.enterprise.entity.Review;
import com.enterprise.entity.User;
import com.enterprise.entity.RepairAppointment;
import com.enterprise.mapper.ReviewMapper;
import com.enterprise.mapper.UserMapper;
import com.enterprise.vo.ReviewVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Resource
    private ReviewMapper reviewMapper;

    @Resource
    private RepairAppointmentService orderService;

    @Resource
    private UserMapper userMapper;

    public List<ReviewVO> listByItem(Long serviceId) {
        if (serviceId == null) {
            throw new BusinessException("企业信息ID不能为空");
        }
        List<Review> list = reviewMapper.selectList(new QueryWrapper<Review>()
                .eq("service_id", serviceId)
                .eq("status", 1)
                .orderByDesc("id"));
        return convertReviewList(list);
    }

    public Page<ReviewVO> page(Integer pageNum, Integer pageSize, Long serviceId, Long userId) {
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        if (serviceId != null) {
            wrapper.eq("service_id", serviceId);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        wrapper.orderByDesc("id");
        Page<Review> page = reviewMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<ReviewVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(convertReviewList(page.getRecords()));
        return voPage;
    }

    public void add(Review review, Long userId) {
        if (review == null || review.getOrderId() == null) {
            throw new BusinessException("处理单ID不能为空");
        }
        if (review.getContent() == null || review.getContent().trim().isEmpty()) {
            throw new BusinessException("评价内容不能为空");
        }
        review.setContent(review.getContent().trim());
        if (review.getContent().length() > 1000) {
            throw new BusinessException("评价内容不能超过1000字符");
        }
        RepairAppointment order = orderService.mustGetById(review.getOrderId());
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权限评价该处理单");
        }
        if (order.getStatus() != 3) {
            throw new BusinessException("处理单未完成，无法评价");
        }
        Long count = reviewMapper.selectCount(new QueryWrapper<Review>()
                .eq("order_id", review.getOrderId())
                .eq("user_id", userId));
        if (count > 0) {
            throw new BusinessException("该处理单已评价");
        }
        review.setUserId(userId);
        review.setServiceId(order.getServiceId());
        review.setStatus(1);
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            throw new BusinessException("评分范围为1-5");
        }
        reviewMapper.insert(review);
    }

    public void deleteById(Long id) {
        reviewMapper.deleteById(id);
    }

    private List<ReviewVO> convertReviewList(List<Review> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> userIds = new ArrayList<>();
        for (Review review : list) {
            if (review.getUserId() != null) {
                userIds.add(review.getUserId());
            }
        }
        Map<Long, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
        }
        List<ReviewVO> result = new ArrayList<>();
        for (Review review : list) {
            ReviewVO vo = new ReviewVO();
            BeanUtils.copyProperties(review, vo);
            User user = userMap.get(review.getUserId());
            vo.setUserName(user == null ? "未知用户" : (user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname()));
            result.add(vo);
        }
        return result;
    }
}







