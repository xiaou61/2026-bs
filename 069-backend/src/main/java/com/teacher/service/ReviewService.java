package com.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.common.BusinessException;
import com.teacher.common.PageResult;
import com.teacher.entity.ScenicSpot;
import com.teacher.entity.TravelOrder;
import com.teacher.entity.TravelReview;
import com.teacher.entity.User;
import com.teacher.mapper.ScenicSpotMapper;
import com.teacher.mapper.TravelOrderMapper;
import com.teacher.mapper.TravelReviewMapper;
import com.teacher.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private static final Integer STATUS_PENDING = 0;
    private static final Integer STATUS_APPROVED = 1;
    private static final Integer STATUS_REJECTED = 2;

    private static final Set<String> SENSITIVE_WORDS = new HashSet<>(Arrays.asList("垃圾", "诈骗", "违法", "暴力", "色情", "仇恨"));

    @Resource
    private TravelReviewMapper travelReviewMapper;

    @Resource
    private TravelOrderMapper travelOrderMapper;

    @Resource
    private ScenicSpotMapper scenicSpotMapper;

    @Resource
    private UserMapper userMapper;

    public TravelReview save(Long userId, TravelReview review) {
        if (review == null || review.getOrderId() == null || review.getScore() == null || !StringUtils.hasText(review.getContent())) {
            throw new BusinessException("评价参数不完整");
        }
        if (review.getScore() < 1 || review.getScore() > 5) {
            throw new BusinessException("评分范围为1-5");
        }
        TravelOrder order = travelOrderMapper.selectById(review.getOrderId());
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (!OrderService.STATUS_FINISHED.equals(order.getStatus())) {
            throw new BusinessException("仅已完成订单可评价");
        }
        TravelReview exist = travelReviewMapper.selectOne(new LambdaQueryWrapper<TravelReview>()
                .eq(TravelReview::getOrderId, order.getId())
                .last("limit 1"));
        if (exist != null) {
            throw new BusinessException("该订单已评价");
        }
        review.setUserId(userId);
        review.setSpotId(order.getSpotId());
        String content = review.getContent().trim();
        review.setStatus(containsSensitiveWord(content) ? STATUS_PENDING : STATUS_APPROVED);
        review.setContent(content);
        review.setReplyContent("");
        travelReviewMapper.insert(review);
        return review;
    }

    public void reply(Long id, String replyContent) {
        if (!StringUtils.hasText(replyContent)) {
            throw new BusinessException("回复内容不能为空");
        }
        TravelReview db = travelReviewMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("评价不存在");
        }
        if (db.getStatus() == null || !STATUS_APPROVED.equals(db.getStatus())) {
            throw new BusinessException("仅已通过评价可回复");
        }
        db.setReplyContent(replyContent.trim());
        travelReviewMapper.updateById(db);
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != STATUS_APPROVED && status != STATUS_REJECTED)) {
            throw new BusinessException("审核状态不合法");
        }
        TravelReview db = travelReviewMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("评价不存在");
        }
        db.setStatus(status);
        if (STATUS_REJECTED.equals(status)) {
            db.setReplyContent("");
        }
        travelReviewMapper.updateById(db);
    }

    public PageResult<Map<String, Object>> myPage(Long userId, Integer pageNum, Integer pageSize, Integer score, Integer status) {
        Page<TravelReview> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TravelReview> wrapper = new LambdaQueryWrapper<TravelReview>()
                .eq(TravelReview::getUserId, userId)
                .eq(score != null, TravelReview::getScore, score)
                .eq(status != null, TravelReview::getStatus, status)
                .orderByDesc(TravelReview::getId);
        return buildPageResult(travelReviewMapper.selectPage(page, wrapper));
    }

    public PageResult<Map<String, Object>> page(Integer pageNum, Integer pageSize, Integer score, String spotName, Integer status) {
        Page<TravelReview> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TravelReview> wrapper = new LambdaQueryWrapper<TravelReview>()
                .eq(score != null, TravelReview::getScore, score)
                .eq(status != null, TravelReview::getStatus, status)
                .orderByDesc(TravelReview::getId);
        if (StringUtils.hasText(spotName)) {
            List<ScenicSpot> spots = scenicSpotMapper.selectList(new LambdaQueryWrapper<ScenicSpot>()
                    .like(ScenicSpot::getName, spotName.trim()));
            if (spots.isEmpty()) {
                PageResult<Map<String, Object>> result = new PageResult<>();
                result.setTotal(0L);
                result.setRecords(Collections.emptyList());
                return result;
            }
            wrapper.in(TravelReview::getSpotId, spots.stream().map(ScenicSpot::getId).collect(Collectors.toSet()));
        }
        return buildPageResult(travelReviewMapper.selectPage(page, wrapper));
    }

    public Long countAll() {
        return travelReviewMapper.selectCount(null);
    }

    private PageResult<Map<String, Object>> buildPageResult(Page<TravelReview> resultPage) {
        List<TravelReview> reviews = resultPage.getRecords();
        List<Map<String, Object>> records = new ArrayList<>();
        if (!reviews.isEmpty()) {
            Set<Long> orderIds = reviews.stream().map(TravelReview::getOrderId).collect(Collectors.toSet());
            Set<Long> spotIds = reviews.stream().map(TravelReview::getSpotId).collect(Collectors.toSet());
            Set<Long> userIds = reviews.stream().map(TravelReview::getUserId).collect(Collectors.toSet());
            Map<Long, TravelOrder> orderMap = travelOrderMapper.selectBatchIds(orderIds).stream()
                    .collect(Collectors.toMap(TravelOrder::getId, e -> e));
            Map<Long, ScenicSpot> spotMap = scenicSpotMapper.selectBatchIds(spotIds).stream()
                    .collect(Collectors.toMap(ScenicSpot::getId, e -> e));
            Map<Long, User> userMap = userMapper.selectBatchIds(userIds).stream()
                    .collect(Collectors.toMap(User::getId, e -> e));
            for (TravelReview review : reviews) {
                TravelOrder order = orderMap.get(review.getOrderId());
                ScenicSpot spot = spotMap.get(review.getSpotId());
                User user = userMap.get(review.getUserId());
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("id", review.getId());
                item.put("orderId", review.getOrderId());
                item.put("orderNo", order == null ? "" : order.getOrderNo());
                item.put("userId", review.getUserId());
                item.put("userName", user == null ? "" : (StringUtils.hasText(user.getNickname()) ? user.getNickname() : user.getUsername()));
                item.put("spotId", review.getSpotId());
                item.put("spotName", spot == null ? "" : spot.getName());
                item.put("score", review.getScore());
                item.put("content", review.getContent());
                item.put("status", review.getStatus());
                item.put("replyContent", review.getReplyContent());
                item.put("createTime", review.getCreateTime());
                records.add(item);
            }
        }
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(records);
        return result;
    }

    private boolean containsSensitiveWord(String content) {
        for (String word : SENSITIVE_WORDS) {
            if (content.contains(word)) {
                return true;
            }
        }
        return false;
    }
}


