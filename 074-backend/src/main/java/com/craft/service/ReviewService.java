package com.craft.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.craft.common.BusinessException;
import com.craft.entity.Review;
import com.craft.entity.User;
import com.craft.entity.CraftOrder;
import com.craft.mapper.ReviewMapper;
import com.craft.mapper.UserMapper;
import com.craft.vo.ReviewVO;
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
    private CraftOrderService orderService;

    @Resource
    private UserMapper userMapper;

    public List<ReviewVO> listByItem(Long itemId) {
        if (itemId == null) {
            throw new BusinessException("商品ID不能为空");
        }
        List<Review> list = reviewMapper.selectList(new QueryWrapper<Review>()
                .eq("item_id", itemId)
                .eq("status", 1)
                .orderByDesc("id"));
        return convertReviewList(list);
    }

    public Page<ReviewVO> page(Integer pageNum, Integer pageSize, Long itemId, Long userId) {
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        if (itemId != null) {
            wrapper.eq("item_id", itemId);
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
            throw new BusinessException("订单ID不能为空");
        }
        if (review.getContent() == null || review.getContent().trim().isEmpty()) {
            throw new BusinessException("评价内容不能为空");
        }
        review.setContent(review.getContent().trim());
        if (review.getContent().length() > 1000) {
            throw new BusinessException("评价内容不能超过1000字符");
        }
        CraftOrder order = orderService.mustGetById(review.getOrderId());
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权限评价该订单");
        }
        if (order.getStatus() != 3) {
            throw new BusinessException("订单未完成，无法评价");
        }
        Long count = reviewMapper.selectCount(new QueryWrapper<Review>()
                .eq("order_id", review.getOrderId())
                .eq("user_id", userId));
        if (count > 0) {
            throw new BusinessException("该订单已评价");
        }
        review.setUserId(userId);
        review.setItemId(order.getItemId());
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

