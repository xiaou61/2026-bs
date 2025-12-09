package com.xiaou.hair.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.entity.Hairdresser;
import com.xiaou.hair.entity.Order;
import com.xiaou.hair.entity.Review;
import com.xiaou.hair.entity.Store;
import com.xiaou.hair.mapper.HairdresserMapper;
import com.xiaou.hair.mapper.OrderMapper;
import com.xiaou.hair.mapper.ReviewMapper;
import com.xiaou.hair.mapper.StoreMapper;
import com.xiaou.hair.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 评价服务类
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private HairdresserMapper hairdresserMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private UserService userService;

    /**
     * 创建评价
     */
    @Transactional
    public void createReview(Review review) {
        Long userId = UserContext.getUserId();

        // 验证订单
        Order order = orderMapper.selectById(review.getOrderId());
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("无权评价他人的订单");
        }

        if (order.getPayStatus() != 1) {
            throw new RuntimeException("订单未支付，无法评价");
        }

        // 检查是否已评价
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getOrderId, review.getOrderId());
        if (reviewMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("该订单已评价");
        }

        // 添加评价
        review.setUserId(userId);
        review.setHairdresserId(order.getHairdresserId());
        review.setStoreId(order.getStoreId());
        reviewMapper.insert(review);

        // 更新理发师评分
        updateHairdresserRating(order.getHairdresserId());

        // 更新门店评分
        updateStoreRating(order.getStoreId());

        // 添加积分
        userService.addPoints(userId, 10, "评价获得积分");

        // TODO: 发送评价通知
    }

    /**
     * 我的评价列表
     */
    public Page<Review> getMyReviews(Integer pageNum, Integer pageSize) {
        Long userId = UserContext.getUserId();
        Page<Review> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Review::getUserId, userId);
        wrapper.orderByDesc(Review::getCreatedAt);

        return reviewMapper.selectPage(page, wrapper);
    }

    /**
     * 理发师的评价列表
     */
    public Page<Review> getHairdresserReviews(Long hairdresserId, Integer pageNum, Integer pageSize) {
        Page<Review> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Review::getHairdresserId, hairdresserId);
        wrapper.orderByDesc(Review::getCreatedAt);

        return reviewMapper.selectPage(page, wrapper);
    }

    /**
     * 门店的评价列表
     */
    public Page<Review> getStoreReviews(Long storeId, Integer pageNum, Integer pageSize) {
        Page<Review> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Review::getStoreId, storeId);
        wrapper.orderByDesc(Review::getCreatedAt);

        return reviewMapper.selectPage(page, wrapper);
    }

    /**
     * 更新理发师评分
     */
    private void updateHairdresserRating(Long hairdresserId) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getHairdresserId, hairdresserId);
        List<Review> reviews = reviewMapper.selectList(wrapper);

        if (!reviews.isEmpty()) {
            double avgRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(5.0);

            Hairdresser hairdresser = hairdresserMapper.selectById(hairdresserId);
            if (hairdresser != null) {
                hairdresser.setRating(BigDecimal.valueOf(avgRating).setScale(2, BigDecimal.ROUND_HALF_UP));
                hairdresserMapper.updateById(hairdresser);
            }
        }
    }

    /**
     * 更新门店评分
     */
    private void updateStoreRating(Long storeId) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getStoreId, storeId);
        List<Review> reviews = reviewMapper.selectList(wrapper);

        if (!reviews.isEmpty()) {
            double avgRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(5.0);

            Store store = storeMapper.selectById(storeId);
            if (store != null) {
                store.setRating(BigDecimal.valueOf(avgRating).setScale(2, BigDecimal.ROUND_HALF_UP));
                storeMapper.updateById(store);
            }
        }
    }
}
