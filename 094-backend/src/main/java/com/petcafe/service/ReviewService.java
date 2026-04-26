package com.petcafe.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcafe.common.BusinessException;
import com.petcafe.common.PageResult;
import com.petcafe.entity.CafeShop;
import com.petcafe.entity.ConsumeOrder;
import com.petcafe.entity.ReviewRecord;
import com.petcafe.entity.User;
import com.petcafe.mapper.CafeShopMapper;
import com.petcafe.mapper.ConsumeOrderMapper;
import com.petcafe.mapper.ReviewRecordMapper;
import com.petcafe.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Resource
    private ReviewRecordMapper reviewMapper;

    @Resource
    private ConsumeOrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CafeShopMapper shopMapper;

    public PageResult<ReviewRecord> page(Integer pageNum, Integer pageSize, String reviewStatus, Long userId, String role) {
        Page<ReviewRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ReviewRecord> wrapper = new LambdaQueryWrapper<ReviewRecord>()
                .eq(StringUtils.hasText(reviewStatus), ReviewRecord::getReviewStatus, reviewStatus == null ? null : reviewStatus.trim().toUpperCase())
                .eq("CUSTOMER".equalsIgnoreCase(role), ReviewRecord::getUserId, userId)
                .orderByDesc(ReviewRecord::getId);
        Page<ReviewRecord> resultPage = reviewMapper.selectPage(page, wrapper);
        fillDetail(resultPage.getRecords());
        PageResult<ReviewRecord> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void add(Long userId, ReviewRecord review) {
        if (review == null || review.getOrderId() == null || review.getRating() == null || review.getRating() < 1 || review.getRating() > 5 || !StringUtils.hasText(review.getContent())) {
            throw new BusinessException("评价信息不完整");
        }
        ConsumeOrder order = orderMapper.selectById(review.getOrderId());
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        review.setUserId(userId);
        review.setShopId(order.getShopId());
        review.setReviewStatus("WAIT_REPLY");
        review.setReviewTime(LocalDateTime.now());
        reviewMapper.insert(review);
    }

    public void reply(Long id, Long replyUserId, String replyContent) {
        if (!StringUtils.hasText(replyContent)) {
            throw new BusinessException("回复内容不能为空");
        }
        ReviewRecord review = reviewMapper.selectById(id);
        if (review == null) {
            throw new BusinessException("评价不存在");
        }
        review.setReplyUserId(replyUserId);
        review.setReplyContent(replyContent.trim());
        review.setReviewStatus("REPLIED");
        review.setReplyTime(LocalDateTime.now());
        reviewMapper.updateById(review);
    }

    public void deleteById(Long id) {
        reviewMapper.deleteById(id);
    }

    private void fillDetail(List<ReviewRecord> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> orderMap = orderMapper.selectList(new LambdaQueryWrapper<ConsumeOrder>()
                        .in(ConsumeOrder::getId, list.stream().map(ReviewRecord::getOrderId).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(ConsumeOrder::getId, ConsumeOrder::getOrderNo, (a, b) -> a, HashMap::new));
        Map<Long, String> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().flatMap(item -> java.util.stream.Stream.of(item.getUserId(), item.getReplyUserId())).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, User::getNickname, (a, b) -> a, HashMap::new));
        Map<Long, String> shopMap = shopMapper.selectList(new LambdaQueryWrapper<CafeShop>()
                        .in(CafeShop::getId, list.stream().map(ReviewRecord::getShopId).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(CafeShop::getId, CafeShop::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            item.setOrderNo(item.getOrderId() == null ? null : orderMap.get(item.getOrderId()));
            item.setUsername(userMap.get(item.getUserId()));
            item.setShopName(item.getShopId() == null ? null : shopMap.get(item.getShopId()));
            item.setReplyName(item.getReplyUserId() == null ? null : userMap.get(item.getReplyUserId()));
        });
    }
}
