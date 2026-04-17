package com.xiaou.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.OrderCreateDTO;
import com.xiaou.dto.OrderRateDTO;
import com.xiaou.entity.OrderInfo;
import com.xiaou.entity.Product;
import com.xiaou.entity.SystemConfig;
import com.xiaou.entity.User;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.OrderInfoMapper;
import com.xiaou.mapper.ProductMapper;
import com.xiaou.mapper.SystemConfigMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.OrderService;
import com.xiaou.vo.OrderDetailVO;
import com.xiaou.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final int DEFAULT_GOOD_REVIEW_SCORE = 2;
    private static final int DEFAULT_BAD_REVIEW_SCORE = -5;

    private final OrderInfoMapper orderInfoMapper;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final SystemConfigMapper systemConfigMapper;

    @Override
    @Transactional
    public Long createOrder(Long userId, OrderCreateDTO createDTO) {
        Product product = getAvailableProduct(createDTO.getProductId());
        if (product.getSellerId().equals(userId)) {
            throw new BusinessException("不能购买自己发布的商品");
        }

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(IdUtil.getSnowflakeNextIdStr());
        orderInfo.setProductId(product.getId());
        orderInfo.setSellerId(product.getSellerId());
        orderInfo.setBuyerId(userId);
        orderInfo.setPrice(product.getPrice());
        orderInfo.setOriginalPrice(product.getOriginalPrice() == null ? product.getPrice() : product.getOriginalPrice());
        orderInfo.setIsBargain(0);
        orderInfo.setStatus("pending");
        orderInfoMapper.insert(orderInfo);

        product.setStatus("sold");
        productMapper.updateById(product);
        return orderInfo.getId();
    }

    @Override
    public Page<OrderVO> getMySellOrders(Long userId, Integer current, Integer size) {
        return buildOrderPage(userId, current, size, OrderInfo::getSellerId);
    }

    @Override
    public Page<OrderVO> getMyBuyOrders(Long userId, Integer current, Integer size) {
        return buildOrderPage(userId, current, size, OrderInfo::getBuyerId);
    }

    @Override
    public OrderDetailVO getOrderDetail(Long userId, Long orderId) {
        OrderInfo orderInfo = getOrderById(orderId);
        ensureOrderParticipant(userId, orderInfo);

        Product product = productMapper.selectById(orderInfo.getProductId());
        User seller = userMapper.selectById(orderInfo.getSellerId());
        User buyer = userMapper.selectById(orderInfo.getBuyerId());

        OrderDetailVO detailVO = new OrderDetailVO();
        detailVO.setId(orderInfo.getId());
        detailVO.setOrderNo(orderInfo.getOrderNo());
        detailVO.setProductId(orderInfo.getProductId());
        detailVO.setProductTitle(product != null ? product.getTitle() : "商品已删除");
        detailVO.setProductImages(product != null ? product.getImages() : "");
        detailVO.setSellerId(orderInfo.getSellerId());
        detailVO.setSellerName(seller != null ? seller.getRealName() : "未知卖家");
        detailVO.setSellerAvatar(seller != null ? seller.getAvatar() : null);
        detailVO.setSellerCreditScore(seller != null ? seller.getCreditScore() : 0);
        detailVO.setBuyerId(orderInfo.getBuyerId());
        detailVO.setBuyerName(buyer != null ? buyer.getRealName() : "未知买家");
        detailVO.setBuyerAvatar(buyer != null ? buyer.getAvatar() : null);
        detailVO.setBuyerCreditScore(buyer != null ? buyer.getCreditScore() : 0);
        detailVO.setPrice(orderInfo.getPrice());
        detailVO.setOriginalPrice(orderInfo.getOriginalPrice());
        detailVO.setIsBargain(orderInfo.getIsBargain());
        detailVO.setStatus(orderInfo.getStatus());
        detailVO.setBuyerRating(orderInfo.getBuyerRating());
        detailVO.setBuyerComment(orderInfo.getBuyerComment());
        detailVO.setSellerRating(orderInfo.getSellerRating());
        detailVO.setSellerComment(orderInfo.getSellerComment());
        detailVO.setCreateTime(orderInfo.getCreateTime());
        detailVO.setCompleteTime(orderInfo.getCompleteTime());
        detailVO.setCancelTime(orderInfo.getCancelTime());
        return detailVO;
    }

    @Override
    @Transactional
    public void completeOrder(Long userId, Long orderId) {
        OrderInfo orderInfo = getOrderById(orderId);
        if (!orderInfo.getBuyerId().equals(userId)) {
            throw new BusinessException("只有买家可以确认收货");
        }
        if (!"pending".equals(orderInfo.getStatus())) {
            throw new BusinessException("当前订单状态不能确认收货");
        }

        orderInfo.setStatus("completed");
        orderInfo.setCompleteTime(LocalDateTime.now());
        orderInfoMapper.updateById(orderInfo);
    }

    @Override
    @Transactional
    public void cancelOrder(Long userId, Long orderId) {
        OrderInfo orderInfo = getOrderById(orderId);
        ensureOrderParticipant(userId, orderInfo);
        if (!"pending".equals(orderInfo.getStatus())) {
            throw new BusinessException("当前订单状态不能取消");
        }

        orderInfo.setStatus("cancelled");
        orderInfo.setCancelTime(LocalDateTime.now());
        orderInfoMapper.updateById(orderInfo);

        Product product = productMapper.selectById(orderInfo.getProductId());
        if (product != null) {
            product.setStatus("on_sale");
            productMapper.updateById(product);
        }
    }

    @Override
    @Transactional
    public void rateOrder(Long userId, Long orderId, OrderRateDTO rateDTO) {
        OrderInfo orderInfo = getOrderById(orderId);
        if (!"completed".equals(orderInfo.getStatus())) {
            throw new BusinessException("只有已完成订单才能评价");
        }

        if (orderInfo.getBuyerId().equals(userId)) {
            if (orderInfo.getBuyerRating() != null) {
                throw new BusinessException("买家已评价过该订单");
            }
            orderInfo.setBuyerRating(rateDTO.getRating());
            orderInfo.setBuyerComment(rateDTO.getComment());
            applyCreditScore(orderInfo.getSellerId(), rateDTO.getRating());
        } else if (orderInfo.getSellerId().equals(userId)) {
            if (orderInfo.getSellerRating() != null) {
                throw new BusinessException("卖家已评价过该订单");
            }
            orderInfo.setSellerRating(rateDTO.getRating());
            orderInfo.setSellerComment(rateDTO.getComment());
            applyCreditScore(orderInfo.getBuyerId(), rateDTO.getRating());
        } else {
            throw new BusinessException("无权限评价该订单");
        }

        orderInfoMapper.updateById(orderInfo);
    }

    private Page<OrderVO> buildOrderPage(Long userId, Integer current, Integer size, SFunction<OrderInfo, ?> selector) {
        Page<OrderInfo> page = new Page<>(current, size);
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(selector, userId).orderByDesc(OrderInfo::getCreateTime);
        Page<OrderInfo> orderPage = orderInfoMapper.selectPage(page, wrapper);

        Page<OrderVO> result = new Page<>(current, size, orderPage.getTotal());
        result.setRecords(orderPage.getRecords().stream().map(this::toOrderVO).toList());
        return result;
    }

    private OrderVO toOrderVO(OrderInfo orderInfo) {
        Product product = productMapper.selectById(orderInfo.getProductId());
        User seller = userMapper.selectById(orderInfo.getSellerId());
        User buyer = userMapper.selectById(orderInfo.getBuyerId());

        OrderVO orderVO = new OrderVO();
        orderVO.setId(orderInfo.getId());
        orderVO.setOrderNo(orderInfo.getOrderNo());
        orderVO.setProductId(orderInfo.getProductId());
        orderVO.setProductTitle(product != null ? product.getTitle() : "商品已删除");
        orderVO.setProductImages(product != null ? product.getImages() : "");
        orderVO.setSellerId(orderInfo.getSellerId());
        orderVO.setSellerName(seller != null ? seller.getRealName() : "未知卖家");
        orderVO.setBuyerId(orderInfo.getBuyerId());
        orderVO.setBuyerName(buyer != null ? buyer.getRealName() : "未知买家");
        orderVO.setPrice(orderInfo.getPrice());
        orderVO.setOriginalPrice(orderInfo.getOriginalPrice());
        orderVO.setIsBargain(orderInfo.getIsBargain());
        orderVO.setStatus(orderInfo.getStatus());
        orderVO.setCreateTime(orderInfo.getCreateTime());
        orderVO.setCompleteTime(orderInfo.getCompleteTime());
        orderVO.setCancelTime(orderInfo.getCancelTime());
        return orderVO;
    }

    private Product getAvailableProduct(Long productId) {
        Product product = productMapper.selectById(productId);
        if (product == null || product.getDeleted() == 1) {
            throw new BusinessException("商品不存在");
        }
        if (!"on_sale".equals(product.getStatus())) {
            throw new BusinessException("商品当前不可购买");
        }

        LambdaQueryWrapper<OrderInfo> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(OrderInfo::getProductId, productId)
                .in(OrderInfo::getStatus, List.of("pending", "completed"));
        if (orderInfoMapper.selectCount(orderWrapper) > 0) {
            throw new BusinessException("商品已被下单");
        }
        return product;
    }

    private OrderInfo getOrderById(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if (orderInfo == null) {
            throw new BusinessException("订单不存在");
        }
        return orderInfo;
    }

    private void ensureOrderParticipant(Long userId, OrderInfo orderInfo) {
        if (!orderInfo.getBuyerId().equals(userId) && !orderInfo.getSellerId().equals(userId)) {
            throw new BusinessException("无权限访问该订单");
        }
    }

    private void applyCreditScore(Long targetUserId, Integer rating) {
        User user = userMapper.selectById(targetUserId);
        if (user == null) {
            return;
        }

        Map<String, Integer> configMap = systemConfigMapper.selectList(null).stream()
                .collect(Collectors.toMap(SystemConfig::getConfigKey, item -> Integer.parseInt(item.getConfigValue()), (a, b) -> a));

        int delta = 0;
        if (rating >= 4) {
            delta = configMap.getOrDefault("good_review_score", DEFAULT_GOOD_REVIEW_SCORE);
        } else if (rating <= 2) {
            delta = configMap.getOrDefault("bad_review_score", DEFAULT_BAD_REVIEW_SCORE);
        }

        if (delta != 0) {
            user.setCreditScore(Math.max(0, user.getCreditScore() + delta));
            userMapper.updateById(user);
        }
    }
}
